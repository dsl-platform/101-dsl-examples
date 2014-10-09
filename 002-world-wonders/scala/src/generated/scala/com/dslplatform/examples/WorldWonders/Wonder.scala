package com.dslplatform.examples.WorldWonders

import com.dslplatform.api.patterns._
import com.dslplatform.api.client._
import scala.concurrent.{ Await, Future, ExecutionContext }
import scala.concurrent.duration.Duration

class Wonder @com.fasterxml.jackson.annotation.JsonIgnore() private (
    private var _URI: String,
    @transient private var __locator: Option[ServiceLocator],
    private var _englishName: String,
    private var _nativeNames: List[String],
    private var _isAncient: Boolean
) extends Serializable with AggregateRoot {

  @com.fasterxml.jackson.annotation.JsonProperty("URI")
  def URI = {
    _URI
  }

  private[examples] def URI_=(value: String) {
    _URI = value
  }

  override def hashCode = URI.hashCode
  override def equals(o: Any) = o match {
    case c: Wonder => c.URI == URI
    case _ => false
  }

  override def toString = "Wonder(" + URI + ")"

  def copy(englishName: String = this._englishName, nativeNames: List[String] = this._nativeNames, isAncient: Boolean = this._isAncient): Wonder = {

    require(englishName ne null, "Null value was provided for property \"englishName\"")
    require(nativeNames ne null, "Null value was provided for property \"nativeNames\"")
    com.dslplatform.api.Guards.checkCollectionNulls(nativeNames)
    new Wonder(_URI = this.URI, __locator = this.__locator, _englishName = englishName, _nativeNames = nativeNames, _isAncient = isAncient)
  }

  @com.fasterxml.jackson.annotation.JsonIgnore
  def isNewAggregate() = __locator == None || _URI == null

  private def updateWithAnother(result: Wonder): this.type = {
    this._URI = result._URI
    this._englishName = result._englishName
    this._nativeNames = result._nativeNames
    this._isAncient = result._isAncient
    this
  }

  private def create()(implicit locator: ServiceLocator, ec: ExecutionContext, duration: Duration): this.type = {
    __locator = Some(if (locator ne null) locator else Bootstrap.getLocator)
    val toUpdateWith = Await.result(__locator.get.resolve(classOf[CrudProxy]).create(this), duration)
    updateWithAnother(toUpdateWith)
  }

  private def update()(implicit ec: ExecutionContext, duration: Duration): this.type = {
    val toUpdateWith = Await.result(__locator.get.resolve(classOf[CrudProxy]).update(this), duration)
    updateWithAnother(toUpdateWith)
  }

  private def delete()(implicit ec: ExecutionContext, duration: Duration) = {
    if (__locator.isEmpty) throw new IllegalArgumentException("Can't delete an aggregate before it's been saved")
    Await.result(__locator.get.resolve(classOf[CrudProxy]).delete[Wonder](URI), duration)
  }

  @com.fasterxml.jackson.annotation.JsonProperty("englishName")
  def englishName = {
    _englishName
  }

  def englishName_=(value: String) {
    _englishName = value
  }

  @com.fasterxml.jackson.annotation.JsonProperty("nativeNames")
  @com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY)
  def nativeNames = {
    _nativeNames
  }

  def nativeNames_=(value: List[String]) {
    com.dslplatform.api.Guards.checkCollectionNulls(value)
    _nativeNames = value
  }

  @com.fasterxml.jackson.annotation.JsonProperty("isAncient")
  def isAncient = {
    _isAncient
  }

  def isAncient_=(value: Boolean) {
    _isAncient = value
  }

  @com.fasterxml.jackson.annotation.JsonCreator private def this(
    @com.fasterxml.jackson.annotation.JacksonInject("__locator") __locator__ : ServiceLocator,
    @com.fasterxml.jackson.annotation.JsonProperty("URI") URI: String,
    @com.fasterxml.jackson.annotation.JsonProperty("englishName") englishName: String,
    @com.fasterxml.jackson.annotation.JsonProperty("nativeNames") nativeNames: List[String],
    @com.fasterxml.jackson.annotation.JsonProperty("isAncient") isAncient: Boolean
  ) =
    this(_URI = URI, __locator = Some(__locator__), _englishName = if (englishName == null) "" else englishName, _nativeNames = if (nativeNames == null) List.empty else nativeNames, _isAncient = isAncient)
}

object Wonder extends AggregateRootCompanion[Wonder] {

  def apply(
    englishName: String = "",
    nativeNames: List[String] = List.empty,
    isAncient: Boolean = false
  ) = {
    require(englishName ne null, "Null value was provided for property \"englishName\"")
    com.dslplatform.api.Guards.checkCollectionNulls(nativeNames)
    require(nativeNames ne null, "Null value was provided for property \"nativeNames\"")
    new Wonder(
      __locator = None,
      _URI = java.util.UUID.randomUUID.toString,
      _englishName = englishName,
      _nativeNames = nativeNames,
      _isAncient = isAncient
    )
  }
}
