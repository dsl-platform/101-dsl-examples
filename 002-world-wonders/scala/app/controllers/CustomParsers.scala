package controllers

import play.api.libs.iteratee.Iteratee
import play.api.mvc.{ BodyParser, BodyParsers, RequestHeader, Results }
import scala.concurrent.Future
import scala.reflect.ClassTag

trait CustomParsers extends BodyParsers {
  def platformJson[T : ClassTag]: BodyParser[T] = parse.when(
    predicate = isMime("application/json"),
    parser    = tolerantPlatformJson,
    badResult = createBadRequest("Expected application/json body")
  )

  private def tolerantPlatformJson[T : ClassTag]: BodyParser[T] = BodyParser("tolerantPlatformJson") { _ =>
    Iteratee.fold[Array[Byte], Array[Byte]](Array.empty[Byte])(_ ++ _).map { body =>
      val t = jsonSerialization.deserialize[T](body)
      Right(t)
    }
  }

  private def isMime(mime: String)(request: RequestHeader) =
    request.contentType.exists(m => m.equalsIgnoreCase(mime))

  private def createBadRequest(msg: String)(request: RequestHeader) =
    Future(Results.BadRequest(msg))
}
