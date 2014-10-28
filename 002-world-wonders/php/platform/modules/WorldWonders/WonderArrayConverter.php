<?php
namespace WorldWonders;

require_once __DIR__.'/Wonder.php';

/**
 * Generated from NGS DSL
 *
 * Converts an object of class WorldWonders\Wonder into a simple array and backwards.
 *
 * @package WorldWonders
 * @version 1.0.0.16886
 */
abstract class WonderArrayConverter
{/**
     * @param array|\WorldWonders\Wonder An object or an array of objects of type "WorldWonders\Wonder"
     *
     * @return array A simple array representation
     */
    public static function toArray($item, $allowNullValues=false)
    {
        if ($item instanceof \WorldWonders\Wonder)
            return self::toArrayObject($item);
        if (is_array($item))
            return self::toArrayList($item, $allowNullValues);

        throw new \InvalidArgumentException('Argument was not an instance of class "WorldWonders\Wonder" nor an array of said instances!');
    }

    private static function toArrayObject($item)
    {
        $ret = array();
        $ret['URI'] = $item->URI;
        $ret['englishName'] = $item->englishName;
        $ret['nativeNames'] = $item->nativeNames;
        $ret['isAncient'] = $item->isAncient;
        return $ret;
    }

    private static function toArrayList(array $items, $allowNullValues=false)
    {
        $ret = array();

        foreach($items as $key => $val) {
            if ($allowNullValues && $val===null) {
                $ret[] = null;
            }
            else {
                if (!$val instanceof \WorldWonders\Wonder)
                    throw new \InvalidArgumentException('Element with index "'.$key.'" was not an object of class "WorldWonders\Wonder"! Type was: '.\NGS\Utils::getType($val));

                $ret[] = $val->toArray();
            }
        }

        return $ret;
    }

    public static function fromArray($item)
    {
        if ($item instanceof \WorldWonders\Wonder)
            return $item;
        if (is_array($item))
            return new \WorldWonders\Wonder($item, 'build_internal');

        throw new \InvalidArgumentException('Argument was not an instance of class "WorldWonders\Wonder" nor an array of said instances!');
    }

    public static function fromArrayList(array $items, $allowNullValues=false)
    {
        try {
            foreach($items as $key => &$val) {
                if($allowNullValues && $val===null)
                    continue;
                if($val === null)
                    throw new \InvalidArgumentException('Null value found in provided array');
                if(!$val instanceof \WorldWonders\Wonder)
                    $val = new \WorldWonders\Wonder($val, 'build_internal');
            }
        }
        catch (\Exception $e) {
            throw new \InvalidArgumentException('Element at index '.$key.' could not be converted to object "WorldWonders\Wonder"!', 42, $e);
        }

        return $items;
    }
}