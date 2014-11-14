package models.WorldWonders

import com.dslplatform.api.patterns._
import com.dslplatform.api.client._
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration.Duration

class Wonder @com.fasterxml.jackson.annotation.JsonIgnore() private(
    private var _URI: String,
    @transient private var __locator: Option[ServiceLocator],
    private var _isAncient: Boolean,
    private var _englishName: String,
    private var _nativeNames: List[String],
    private var _imageURL: String
  ) extends Serializable with AggregateRoot {

  @com.fasterxml.jackson.annotation.JsonProperty("URI")
  def URI = {
    _URI
  }

  private [models] def URI_= (value: String) {
    _URI = value

  }

  override def hashCode = URI.hashCode
  override def equals(o: Any) = o match {
    case c: Wonder => c.URI == URI
    case _ => false
  }

  override def toString = "Wonder("+ URI +")"

   def copy(isAncient: Boolean = this._isAncient, englishName: String = this._englishName, nativeNames: List[String] = this._nativeNames, imageURL: String = this._imageURL): Wonder = {

  require(englishName ne null, "Null value was provided for property \"englishName\"")
  require(nativeNames ne null, "Null value was provided for property \"nativeNames\"")
  com.dslplatform.api.Guards.checkCollectionNulls(nativeNames)
  require(imageURL ne null, "Null value was provided for property \"imageURL\"")
    new Wonder(_URI = this.URI, __locator = this.__locator, _isAncient = isAncient, _englishName = englishName, _nativeNames = nativeNames, _imageURL = imageURL)
  }

  private def updateWithAnother(result: Wonder): this.type = {
    this._URI = result._URI
    this._isAncient = result._isAncient
    this._englishName = result._englishName
    this._nativeNames = result._nativeNames
    this._imageURL = result._imageURL
    this
  }

  def create()(implicit locator: ServiceLocator, ec: ExecutionContext, duration: Duration): this.type = {
    __locator = Some(if (locator ne null) locator else Bootstrap.getLocator)
    val toUpdateWith = Await.result(__locator.get.resolve(classOf[CrudProxy]).create(this), duration)
    updateWithAnother(toUpdateWith)

  }

  def update()(implicit ec: ExecutionContext, duration: Duration): this.type = {
    val toUpdateWith = Await.result(__locator.get.resolve(classOf[CrudProxy]).update(this), duration)
    updateWithAnother(toUpdateWith)

  }

  def delete()(implicit ec: ExecutionContext, duration: Duration) = {
    if (__locator.isEmpty) throw new IllegalArgumentException("Can't delete an aggregate before it's been saved")
    Await.result(__locator.get.resolve(classOf[CrudProxy]).delete[Wonder](URI), duration)
  }

  @com.fasterxml.jackson.annotation.JsonProperty("isAncient")
  def isAncient = {
    _isAncient
  }

  def isAncient_= (value: Boolean) {
    _isAncient = value

  }

  @com.fasterxml.jackson.annotation.JsonProperty("englishName")
  def englishName = {
    _englishName
  }

  def englishName_= (value: String) {
    _englishName = value

  }

  @com.fasterxml.jackson.annotation.JsonProperty("nativeNames")
  @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
  def nativeNames = {
    _nativeNames
  }

  def nativeNames_= (value: List[String]) {
    com.dslplatform.api.Guards.checkCollectionNulls(value)
    _nativeNames = value

  }

  @com.fasterxml.jackson.annotation.JsonProperty("imageURL")
  def imageURL = {
    _imageURL
  }

  def imageURL_= (value: String) {
    _imageURL = value

  }

  @com.fasterxml.jackson.annotation.JsonCreator private def this(
    @com.fasterxml.jackson.annotation.JacksonInject("__locator") __locator__ : ServiceLocator
  , @com.fasterxml.jackson.annotation.JsonProperty("URI") URI: String
  , @com.fasterxml.jackson.annotation.JsonProperty("isAncient") isAncient: Boolean
  , @com.fasterxml.jackson.annotation.JsonProperty("englishName") englishName: String
  , @com.fasterxml.jackson.annotation.JsonProperty("nativeNames") nativeNames: List[String]
  , @com.fasterxml.jackson.annotation.JsonProperty("imageURL") imageURL: String
  ) =
    this(_URI = URI, __locator = Some(__locator__), _isAncient = isAncient, _englishName = if (englishName == null) "" else englishName, _nativeNames = if (nativeNames == null) List.empty else nativeNames, _imageURL = if (imageURL == null) "" else imageURL)

}

object Wonder extends AggregateRootCompanion[Wonder]{

  def apply(
    isAncient: Boolean = false
  , englishName: String = ""
  , nativeNames: List[String] = List.empty
  , imageURL: String = ""
  ) = {
    require(englishName ne null, "Null value was provided for property \"englishName\"")
    com.dslplatform.api.Guards.checkCollectionNulls(nativeNames)
    require(nativeNames ne null, "Null value was provided for property \"nativeNames\"")
    require(imageURL ne null, "Null value was provided for property \"imageURL\"")
    new Wonder(
      __locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _isAncient = isAncient
    , _englishName = englishName
    , _nativeNames = nativeNames
    , _imageURL = imageURL)
  }

}
