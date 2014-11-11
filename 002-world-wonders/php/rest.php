<?php
use WorldWonders\WonderJsonConverter;

// SLIM INIT
require_once __DIR__.'/Slim/Slim.php';
\Slim\Slim::registerAutoloader();

$app = new \Slim\Slim();

// ROUTES
$app->get('/wonders', 'restFindAll');
$app->delete('/wonders', 'restDeleteAll');

$app->get('/wonders/:uri', 'restFindWonder');
$app->put('/wonders/:uri', 'restUpdateWonder');
$app->post('/wonders', 'restCreateWonder');
$app->delete('/wonders/:uri', 'restDeleteWonder');

$app->run();

// REST
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
?>