<?php
use NGS\Client\CrudProxy;
use NGS\Client\DomainProxy;
use NGS\Client\StandardProxy;
use WorldWonders\Wonder;
use WorldWonders\WonderJsonConverter;

// PLATFORM INIT
define('NGS_DSL_PATH',  __DIR__.'/../dsl');
require_once __DIR__.'/platform/Bootstrap.php';

$crudProxy = CrudProxy::instance();
$domainProxy = DomainProxy::instance();
$standardProxt = StandardProxy::instance();

// CRUD
function crudCreateWonder($json) {
    global $crudProxy;
    $wonder = WonderJsonConverter::fromJson($json);
    return $crudProxy->create($wonder);
}

function crudFindAll() {
    global $domainProxy;
    $wonders = $domainProxy->search('WorldWonders\\Wonder');
    return WonderJsonConverter::toJson($wonders);
}

function crudFindWonder($uri) {
    global $crudProxy;
    $wonder = $crudProxy->read('WorldWonders\\Wonder', $uri);
    return WonderJsonConverter::toJson($wonder);
}

function crudUpdateWonder($uri, $json) {
    global $crudProxy;
    $wonder = WonderJsonConverter::fromJson($json);
    //$wonder->setURI($uri);  WHAT DO TO WITH URI HERE?
    $updatedWonder = $crudProxy->update($wonder);
    return WonderJsonConverter::toJson($updatedWonder);
}

function crudDeleteWonder($uri) {
    global $crudProxy;
    return $domainProxy->delete('WorldWonders\\Wonder', $uri);
}

function crudDeleteAll() {
    global $domainProxy;
    global $standardProxy;
    $wonders = $domainProxy->search('WorldWonders\\Wonder');
    return $standardProxy->delete($wonders);
}