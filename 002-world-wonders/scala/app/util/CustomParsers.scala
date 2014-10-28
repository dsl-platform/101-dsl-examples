package models

import play.api.libs.iteratee.Iteratee
import play.api.mvc.{BodyParser, BodyParsers, RequestHeader, Results}

import scala.concurrent.Future
import scala.reflect.ClassTag

trait CustomParsers extends BodyParsers {
  def parseObject[T : ClassTag]: BodyParser[T] = parse.when(
    predicate = isMime("application/json"),
    parser    = BodyParser("tolerantPlatformJson") { _ =>
      Iteratee.fold[Array[Byte], Array[Byte]](Array.empty[Byte])(_ ++ _).map { body =>
        val t = jsonSerialization.deserialize[T](body)
        Right(t)
      }
    },
    badResult = createBadRequest("Expected application/json object")
  )

  def parseIndexedSeq[T : ClassTag]: BodyParser[IndexedSeq[T]] = parse.when(
    predicate = isMime("application/json"),
    parser    = BodyParser("tolerantPlatformJson") { _ =>
      Iteratee.fold[Array[Byte], Array[Byte]](Array.empty[Byte])(_ ++ _).map { body =>
        val t = jsonSerialization.deserializeList[T](body)
        Right(t)
      }
    },
    badResult = createBadRequest("Expected application/json array")
  )

  private def isMime(mime: String)(request: RequestHeader) =
    request.contentType.exists(_.equalsIgnoreCase(mime))

  private def createBadRequest(msg: String)(request: RequestHeader) =
    Future(Results.BadRequest(msg))
}
