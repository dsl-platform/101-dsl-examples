package controllers

import com.dslplatform.examples.WorldWonders.Wonder
import play.api.http.Writeable
import scala.reflect.ClassTag

trait CustomWriteables {
  implicit val wonderWriteable = Writeable[Wonder](
      transform   = serialize[Wonder] _,
      contentType = Some("application/json")
  )

  implicit val wonderListWriteable = Writeable[Array[Wonder]](
      transform   = serialize[Array[Wonder]] _,
      contentType = Some("application/json")
  )

  private def serialize[T : ClassTag](t: T): Array[Byte] =
    jsonSerialization.serialize[T](t).getBytes("UTF-8")
}
