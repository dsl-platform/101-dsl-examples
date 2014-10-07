<?php
abstract class Bootstrap
{
    const API_HOST = 'api.dsl-platform.com';

    public static function init()
    {
        self::performChecks();

        $configPath = __DIR__.'/bootstrap/Config.php';
        if (!file_exists($configPath)) {
            self::ensureBootstrapLoaded();
        }

        require_once $configPath;
    }

    private static $httpOK;
    private static $curlZLib;
    private static $extStatus = array();
    private static $requiredExt = array('curl', 'mbstring', 'bcmath');

    public static function performChecks()
    {
        self::$httpOK = in_array('http', stream_get_wrappers()) && ini_get('allow_url_fopen') === '1';

        foreach (self::$requiredExt as $ext)
            self::$extStatus[$ext] = in_array($ext, get_loaded_extensions());

        foreach (self::$requiredExt as $ext) {
            if (!self::$extStatus[$ext]) {
                self::remotePredefinedError('bootstrap', $ext.'-missing');
                exit(255);
            }
        }

        $curlVersion = curl_version();
        $curlFeatures = $curlVersion['features'];
        $curlSsl = ($curlFeatures & CURL_VERSION_SSL) !== 0;
        self::$curlZLib = ($curlFeatures & CURL_VERSION_LIBZ) !== 0;

        if (!$curlSsl) {
            self::remotePredefinedError('bootstrap', 'curl-no-ssl');
            exit(255);
        }
    }

    public static function remotePredefinedError ($section, $code, $encode = true) {
        $errorUrl = 'http://'.self::API_HOST.'/alpha/error/'.rawurlencode($section).'/';

        if ($encode)
            $errorUrl .= rawurlencode($code);
        else
            $errorUrl .= $code;

        header(':', true, 500);

        if (self::$httpOK) {
            $message = @file_get_contents($errorUrl);
            if ($message !== false) {
                header('Content-Type: text/html; charset=UTF-8');
                echo $message;
                return;
            }
        }

        if (self::$extStatus['curl']) {
            $curl = curl_init($errorUrl);
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);

            $message = curl_exec($curl);
            if (curl_getinfo($curl, CURLINFO_HTTP_CODE) === 200) {
                header('Content-Type: text/html; charset=UTF-8');
                echo $message;
                return;
            }
        }

        header('Content-Type: text/plain; charset=UTF-8');
        echo 'DSL Platform could not be initialized.
Error was: '.$code.PHP_EOL;

        if (!self::$httpOK)
            echo PHP_EOL.'Please visit '.$errorUrl.' for more information!';

        exit(255);
    }

    public static function ensureBootstrapLoaded()
    {
        $curl = curl_init('https://'.self::API_HOST.'/alpha/bootstrap');
        curl_setopt_array($curl, array(
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_SSL_VERIFYPEER => false,
            CURLOPT_HTTPHEADER => array(
                'Accept: application/json; charset=UTF-8'
            )
        ));

        if (self::$curlZLib) {
            curl_setopt($curl, CURLOPT_ENCODING, 'deflate');
        }

        $files = curl_exec($curl);
        curl_close($curl);

        if ($files === false) {
            self::remotePredefinedError('bootstrap', 'download-error');
            exit(255);
        }

        $files = json_decode($files, true);
        if ($files === null) {
            self::remotePredefinedError('bootstrap', 'download-error');
            exit(255);
        }

        foreach($files as $path => $body) {
            if (strtolower(substr($path, -4)) === '.exe') {
                $files[$path] = base64_decode($body);
            }
        }

        $platformPath = __DIR__;
        $bootstrapPath = __DIR__.'/bootstrap';

        if (!is_dir($bootstrapPath)) {
            mkdir($bootstrapPath, 0777, true);
        }

        foreach($files as $filename => $body) {
            $filename = __DIR__.'/../'.$filename;
            if (file_exists($filename)) {
                $oldBody = file_get_contents($filename);

                if ($oldBody === $body) {
                    continue;
                }

                if (strpos($oldBody, '<?php // DO NOT MANAGE') === 0) {
                    continue;
                }
            }

            file_put_contents($filename, $body);
        }
    }

    public static function canCompress()
    {
        return self::$curlZLib;
    }
}

Bootstrap::init();
