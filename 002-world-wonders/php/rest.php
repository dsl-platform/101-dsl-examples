<?php
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
    $response = crudFindAll();
    $app->response->setBody($response);
}

function restDeleteAll() {
    global $app;
    $response = crudDeleteAll();
    $app->response->setBody($response);
}

function restFindWonder($uri) {
    global $app;
    $response = crudFindWonder($uri);
    $app->response->setBody($response);
}

function restUpdateWonder($uri) {
    global $app;
    $request = $app->request->getBody();
    $response = crudUpdateWonder($uri, $request);
    $app->response->setBody($response);
}

function restCreateWonder() {
    global $app;
    $request = $app->request->getBody();
    $response = crudCreateWonder($request);
    $app->response->setBody($response);
}

function restDeleteWonder($uri) {
    global $app;
    $response = crudDeleteWonder($uri);
    $app->response->setBody($response);
}
?>