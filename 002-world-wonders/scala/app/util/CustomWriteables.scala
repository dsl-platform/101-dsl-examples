package models

import play.api.http.Writeable
import scala.reflect.ClassTag

trait CustomWriteables {
  implicit def toWriteable[T: ClassTag] = Writeable[T](
    transform = jsonSerialization.serialize(_: T).getBytes("UTF-8"),
    contentType = Some("application/json")
  )
}
