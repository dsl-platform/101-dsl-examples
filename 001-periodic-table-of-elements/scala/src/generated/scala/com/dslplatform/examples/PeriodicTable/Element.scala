package com.dslplatform.examples.PeriodicTable

import com.dslplatform.api.patterns._
import com.dslplatform.api.client._
import scala.concurrent.{Await, Future, ExecutionContext}
import scala.concurrent.duration.Duration

class Element @com.fasterxml.jackson.annotation.JsonIgnore() private(
    private var _URI: String,
    @transient private var __locator: Option[ServiceLocator],
    private var _number: Int,
    private var _name: String
  ) extends Serializable with AggregateRoot {

  @com.fasterxml.jackson.annotation.JsonProperty("URI")
  def URI = {
    _URI
  }

  private [examples] def URI_= (value: String) {
    _URI = value

  }

  override def hashCode = URI.hashCode
  override def equals(o: Any) = o match {
    case c: Element => c.URI == URI
    case _ => false
  }

  override def toString = "Element("+ URI +")"

   def copy(number: Int = this._number, name: String = this._name): Element = {

  require(name ne null, "Null value was provided for property \"name\"")
    new Element(_URI = this.URI, __locator = this.__locator, _number = number, _name = name)
  }

  @com.fasterxml.jackson.annotation.JsonIgnore
  def isNewAggregate() = __locator == None || _URI == null

  private def updateWithAnother(result: Element): this.type = {
    this._URI = result._URI
    this._number = result._number
    this._name = result._name
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
    Await.result(__locator.get.resolve(classOf[CrudProxy]).delete[Element](URI), duration)
  }

  @com.fasterxml.jackson.annotation.JsonProperty("number")
  def number = {
    _number
  }

  def number_= (value: Int) {
    _number = value

  }

  @com.fasterxml.jackson.annotation.JsonProperty("name")
  def name = {
    _name
  }

  def name_= (value: String) {
    _name = value

  }

  @com.fasterxml.jackson.annotation.JsonCreator private def this(
    @com.fasterxml.jackson.annotation.JacksonInject("__locator") __locator__ : ServiceLocator
  , @com.fasterxml.jackson.annotation.JsonProperty("URI") URI: String
  , @com.fasterxml.jackson.annotation.JsonProperty("number") number: Int
  , @com.fasterxml.jackson.annotation.JsonProperty("name") name: String
  ) =
    this(_URI = URI, __locator = Some(__locator__), _number = number, _name = if (name == null) "" else name)

}

object Element extends AggregateRootCompanion[Element]{

  def apply(
    number: Int = 0
  , name: String = ""
  ) = {
    require(name ne null, "Null value was provided for property \"name\"")
    new Element(
      __locator = None
    , _URI = java.util.UUID.randomUUID.toString
    , _number = number
    , _name = name)
  }

}
