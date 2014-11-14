<?php
use WorldWonders\WonderJsonConverter;

// SLIM INIT
require_once __DIR__.'/Slim/Slim.php';
\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// CORS
$app->response->headers->set('Access-Control-Allow-Origin', '*');
$app->response->headers->set('Access-Control-Allow-Methods', 'GET, PUT, POST, DELETE, OPTIONS');
$app->response->headers->set('Access-Control-Allow-Headers', 'Content-Type');
$app->response->headers->set('Access-Control-Max-Age', '86400');

// ROUTES
$app->get('/randomimg', 'restRandomImg');
$app->get('/reset', 'restReset');
$app->get('/wonders', 'restFindAll');
$app->delete('/wonders', 'restDeleteAll');

$app->get('/wonders/:uri', 'restFindWonder');
$app->put('/wonders/:uri', 'restUpdateWonder');
$app->post('/wonders', 'restCreateWonder');
$app->delete('/wonders/:uri', 'restDeleteWonder');

$app->options('/.*?', 'restOptions');

// RUNIT
$app->run();

// REST
function restReset() {
    global $app;
    $defaultJson    = file_get_contents('static/wonders.json');
    $defaultWonders = WonderJsonConverter::fromJson($defaultJson);
    crudReset($defaultWonders);
    $resWonders     = crudFindAll();
    $resJson        = WonderJsonConverter::toJson($resWonders);
    $app->response->setBody($resJson);
}

function restRandomImg() {
    global $app;
    $image = getRandomImage();
    while ($image == null) {
      $image = getRandomImage();
    }
    $app->response->setBody($image);
}

function restFindAll() {
    global $app;
    $resWonders = crudFindAll();
    $resJson    = WonderJsonConverter::toJson($resWonders);
    $app->response->setBody($resJson);
}

function restDeleteAll() {
    global $app;
    $response = crudDeleteAll();
    $app->response->setBody($response);
}

function restFindWonder($uri) {
    global $app;
    $resWonder = crudFindWonder($uri);
    $resJson   = WonderJsonConverter::toJson($resWonder);
    $app->response->setBody($resJson);
}

function restUpdateWonder($uri) {
    global $app;
    $reqJson   = $app->request->getBody();
    $reqWonder = WonderJsonConverter::fromJson($reqJson);
    $resWonder = crudUpdateWonder($uri, $reqWonder);
    $resJson   = WonderJsonConverter::toJson($resWonder);
    $app->response->setBody($resJson);
}

function restCreateWonder() {
    global $app;
    $reqJson   = $app->request->getBody();
    $reqWonder = WonderJsonConverter::fromJson($reqJson);
    $resWonder = crudCreateWonder($reqWonder);
    $resJson   = WonderJsonConverter::toJson($resWonder);
    $app->response->setBody($resJson);
}

function restDeleteWonder($uri) {
    global $app;
    $resWonder = crudDeleteWonder($uri);
    $resJson   = WonderJsonConverter::toJson($resWonder);
    $app->response->setBody($resJson);
}

function restOptions() {
}

// UTIL
function getRandomImage() {
    $reqURL = 'http://commons.wikimedia.org/w/api.php?continue=&format=json&action=query&generator=random&prop=imageinfo&iiprop=url&iiurlwidth=200';
    $resJson    = file_get_contents($reqURL);
    //$resJson    = '{"batchcomplete":"","query":{"pages":{"22459639":{"pageid":22459639,"ns":6,"title":"File:Port Townsend, WA - First Presbyterian Church 04.jpg","imagerepository":"local","imageinfo":[{"thumburl":"http://upload.wikimedia.org/wikipedia/commons/thumb/c/cc/Port_Townsend%2C_WA_-_First_Presbyterian_Church_04.jpg/200px-Port_Townsend%2C_WA_-_First_Presbyterian_Church_04.jpg","thumbwidth":200,"thumbheight":301,"url":"http://upload.wikimedia.org/wikipedia/commons/c/cc/Port_Townsend%2C_WA_-_First_Presbyterian_Church_04.jpg","descriptionurl":"http://commons.wikimedia.org/wiki/File:Port_Townsend,_WA_-_First_Presbyterian_Church_04.jpg"}]}}}}';
    $imageInfo  = json_decode($resJson, true);
    $pages      = $imageInfo['query']['pages'];
    $pageKey    = key($pages);
    $page       = $pages[$pageKey];
    $imageTitle = preg_replace('/File:(.+).(jpg|gif|png)/i', '\\1', $page['title']);
    if (array_key_exists('imageinfo', $page)) {
        $imageURL = $page['imageinfo'][0]['thumburl'];
        $resArr = array(
            'title' => $imageTitle.' [REPLACE ME]',
            'url'   => $imageURL
        );
        $resJSON = json_encode($resArr);
        return $resJSON;
    } else {
        return null;
    }
}
?>