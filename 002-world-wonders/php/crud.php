<?php
use NGS\Client\CrudProxy;
use NGS\Client\DomainProxy;
use NGS\Client\StandardProxy;
use WorldWonders\Wonder;
use WorldWonders\WonderJsonConverter;

// PLATFORM INIT
define('NGS_DSL_PATH',  __DIR__.'/../dsl');
define('NGS_UPDATE', false);
require_once __DIR__.'/platform/Bootstrap.php';

$crudProxy = CrudProxy::instance();
$domainProxy = DomainProxy::instance();
$standardProxy = StandardProxy::instance();

// CRUD
function crudCreateWonder($wonder) {
    global $crudProxy;
    return $crudProxy->create($wonder);
}

function crudFindAll() {
    global $domainProxy;
    return $domainProxy->search('WorldWonders\\Wonder');
}

function crudFindWonder($uri) {
    global $crudProxy;
    return $crudProxy->read('WorldWonders\\Wonder', $uri);
}

function crudUpdateWonder($uri, $wonder) {
    global $crudProxy;
    //$wonder->setURI($uri);  WHAT DO TO WITH URI HERE?
    return $crudProxy->update($wonder);
}

function crudDeleteWonder($uri) {
    global $crudProxy;
    return $crudProxy->delete('WorldWonders\\Wonder', $uri);
}

function crudDeleteAll() {
    global $domainProxy;
    global $standardProxy;
    $wonders = $domainProxy->search('WorldWonders\\Wonder');
    return $standardProxy->delete($wonders);
}