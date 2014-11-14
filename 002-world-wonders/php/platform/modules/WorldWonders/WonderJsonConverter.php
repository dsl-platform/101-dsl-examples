<?php
namespace WorldWonders;

require_once __DIR__.'/WonderArrayConverter.php';

/**
 * Generated from NGS DSL
 *
 * Converts an object of class WorldWonders\Wonder into a JSON string and backwards via an array converter.
 *
 * @package WorldWonders
 * @version 1.0.0.42921
 */
abstract class WonderJsonConverter
{/**
     * @param string Json representation of the object(s)
     *
     * @return array|\WorldWonders\Wonder An object or an array of objects of type "WorldWonders\Wonder"
     */
    public static function fromJson($item, $allowNullValues=false)
    {
        $obj = json_decode($item, true);

        return \NGS\Utils::isJsonArray($item)
            ? \WorldWonders\WonderArrayConverter::fromArrayList($obj, $allowNullValues)
            : \WorldWonders\WonderArrayConverter::fromArray($obj);
    }

    /**
     * @param array|\WorldWonders\Wonder An object or an array of objects of type "WorldWonders\Wonder"
     *
     * @return string Json representation of the object(s)
     */
    public static function toJson($item)
    {
        $arr = \WorldWonders\WonderArrayConverter::toArray($item);
        if(is_array($item))
            return json_encode($arr);
        if(empty($arr))
            return '{}';
        return json_encode($arr);
    }
}