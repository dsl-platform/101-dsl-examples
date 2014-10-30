<?php
namespace WorldWonders;

require_once __DIR__.'/WonderJsonConverter.php';
require_once __DIR__.'/WonderArrayConverter.php';

/**
 * Generated from NGS DSL
 *
 * @property string $URI a unique object identifier (read-only)
 * @property string $englishName a string
 * @property array $nativeNames an array of strings
 * @property bool $isAncient a boolean value
 *
 * @package WorldWonders
 * @version 1.0.0.16886
 */
class Wonder extends \NGS\Patterns\AggregateRoot implements \IteratorAggregate
{
    protected $URI;
    protected $englishName;
    protected $nativeNames;
    protected $isAncient;

    /**
     * Constructs object using a key-property array or instance of class "WorldWonders\Wonder"
     *
     * @param array|void $data key-property array or instance of class "WorldWonders\Wonder" or pass void to provide all fields with defaults
     */
    public function __construct($data = array(), $construction_type = '')
    {
        if(is_array($data) && $construction_type !== 'build_internal') {
            foreach($data as $key => $val) {
                if(in_array($key, self::$_read_only_properties, true))
                    throw new \LogicException($key.' is a read only property and can\'t be set through the constructor.');
            }
        }
        if (is_array($data)) {
            $this->fromArray($data);
        } else {
            throw new \InvalidArgumentException('Constructor parameter must be an array! Type was: '.\NGS\Utils::getType($data));
        }
    }

    /**
     * Supply default values for uninitialized properties
     *
     * @param array $data key-property array which will be filled in-place
     */
    private static function provideDefaults(array &$data)
    {
        if(!array_key_exists('URI', $data))
            $data['URI'] = null; //a string representing a unique object identifier
        if(!array_key_exists('englishName', $data))
            $data['englishName'] = ''; // a string
        if(!array_key_exists('nativeNames', $data))
            $data['nativeNames'] = array(); // an array of strings
        if(!array_key_exists('isAncient', $data))
            $data['isAncient'] = false; // a boolean value
    }

    /**
     * Constructs object from a key-property array
     *
     * @param array $data key-property array
     */
    private function fromArray(array $data)
    {
        $this->provideDefaults($data);

        if(isset($data['URI']))
            $this->URI = \NGS\Converter\PrimitiveConverter::toString($data['URI']);
        unset($data['URI']);
        if (array_key_exists('englishName', $data))
            $this->setEnglishName($data['englishName']);
        unset($data['englishName']);
        if (array_key_exists('nativeNames', $data))
            $this->setNativeNames($data['nativeNames']);
        unset($data['nativeNames']);
        if (array_key_exists('isAncient', $data))
            $this->setIsAncient($data['isAncient']);
        unset($data['isAncient']);

        if (count($data) !== 0 && \NGS\Utils::WarningsAsErrors())
            throw new \InvalidArgumentException('Superflous array keys found in "WorldWonders\Wonder" constructor: '.implode(', ', array_keys($data)));
    }

// ============================================================================

    /**
     * Helper getter function, body for magic method $this->__get('URI')
     * URI is a string representation of the primary key.
     *
     * @return string unique resource identifier representing this object
     */
    public function getURI()
    {
        return $this->URI;
    }

    /**
     * @return a string
     */
    public function getEnglishName()
    {
        return $this->englishName;
    }

    /**
     * @return an array of strings
     */
    public function getNativeNames()
    {
        return $this->nativeNames;
    }

    /**
     * @return a boolean value
     */
    public function getIsAncient()
    {
        return $this->isAncient;
    }

    /**
     * Property getter which throws Exceptions on invalid access
     *
     * @param string $name Property name
     *
     * @return mixed
     */
    public function __get($name)
    {
        if ($name === 'URI')
            return $this->getURI(); // a string representing a unique object identifier
        if ($name === 'englishName')
            return $this->getEnglishName(); // a string
        if ($name === 'nativeNames')
            return $this->getNativeNames(); // an array of strings
        if ($name === 'isAncient')
            return $this->getIsAncient(); // a boolean value

        throw new \InvalidArgumentException('Property "'.$name.'" in class "WorldWonders\Wonder" does not exist and could not be retrieved!');
    }

// ============================================================================

    /**
     * Property existence checker
     *
     * @param string $name Property name to check for existence
     *
     * @return bool will return true if and only if the property exist and is not null
     */
    public function __isset($name)
    {
        if ($name === 'URI')
            return $this->URI !== null;
        if ($name === 'englishName')
            return true; // a string (always set)
        if ($name === 'nativeNames')
            return true; // an array of strings (always set)
        if ($name === 'isAncient')
            return true; // a boolean value (always set)

        return false;
    }

    private static $_read_only_properties = array('URI');

    /**
     * @param string $value a string
     *
     * @return string
     */
    public function setEnglishName($value)
    {
        if ($value === null)
            throw new \InvalidArgumentException('Property "englishName" cannot be set to null because it is non-nullable!');
        $value = \NGS\Converter\PrimitiveConverter::toString($value);
        $this->englishName = $value;
        return $value;
    }

    /**
     * @param array $value an array of strings
     *
     * @return array
     */
    public function setNativeNames($value)
    {
        if ($value === null)
            throw new \InvalidArgumentException('Property "nativeNames" cannot be set to null because it is non-nullable!');
        $value = \NGS\Converter\PrimitiveConverter::toStringArray($value, false);
        $this->nativeNames = $value;
        return $value;
    }

    /**
     * @param bool $value a boolean value
     *
     * @return bool
     */
    public function setIsAncient($value)
    {
        if ($value === null)
            throw new \InvalidArgumentException('Property "isAncient" cannot be set to null because it is non-nullable!');
        $value = \NGS\Converter\PrimitiveConverter::toBoolean($value);
        $this->isAncient = $value;
        return $value;
    }

    /**
     * Property setter which checks for invalid access to entity properties and enforces proper type checks
     *
     * @param string $name Property name
     * @param mixed $value Property value
     */
    public function __set($name, $value)
    {
        if(in_array($name, self::$_read_only_properties, true))
            throw new \LogicException('Property "'.$name.'" in "WorldWonders\Wonder" cannot be set, because it is read-only!');
        if ($name === 'englishName')
            return $this->setEnglishName($value); // a string
        if ($name === 'nativeNames')
            return $this->setNativeNames($value); // an array of strings
        if ($name === 'isAncient')
            return $this->setIsAncient($value); // a boolean value
        throw new \InvalidArgumentException('Property "'.$name.'" in class "WorldWonders\Wonder" does not exist and could not be set!');
    }

    /**
     * Will unset a property if it exists, but throw an exception if it is not nullable
     *
     * @param string $name Property name to unset (set to null)
     */
    public function __unset($name)
    {
        if(in_array($name, self::$_read_only_properties, true))
            throw new \LogicException('Property "'.$name.'" cannot be unset, because it is read-only!');
        if ($name === 'englishName')
            throw new \LogicException('The property "englishName" cannot be unset because it is non-nullable!'); // a string (cannot be unset)
        if ($name === 'nativeNames')
            throw new \LogicException('The property "nativeNames" cannot be unset because it is non-nullable!'); // an array of strings (cannot be unset)
        if ($name === 'isAncient')
            throw new \LogicException('The property "isAncient" cannot be unset because it is non-nullable!'); // a boolean value (cannot be unset)
    }

    /**
     * Create or update WorldWonders\Wonder instance (server call)
     *
     * @return modified instance object
     */
    public function persist()
    {

        $newObject = parent::persist();
        $this->updateWithAnother($newObject);

        return $this;
    }

    private function updateWithAnother(\WorldWonders\Wonder $result)
    {
        $this->URI = $result->URI;

        $this->englishName = $result->englishName;
        $this->nativeNames = $result->nativeNames;
        $this->isAncient = $result->isAncient;
    }

    public function toJson()
    {
        return \WorldWonders\WonderJsonConverter::toJson($this);
    }

    public static function fromJson($item)
    {
        return \WorldWonders\WonderJsonConverter::fromJson($item);
    }

    public function __toString()
    {
        return 'WorldWonders\Wonder'.$this->toJson();
    }

    public function __clone()
    {
        return \WorldWonders\WonderArrayConverter::fromArray(\WorldWonders\WonderArrayConverter::toArray($this));
    }

    public function toArray()
    {
        return \WorldWonders\WonderArrayConverter::toArray($this);
    }

    /**
     * Implementation of the IteratorAggregate interface via \ArrayIterator
     *
     * @return Traversable a new iterator specially created for this iteratation
     */
    public function getIterator()
    {
        return new \ArrayIterator(\WorldWonders\WonderArrayConverter::toArray($this));
    }
}