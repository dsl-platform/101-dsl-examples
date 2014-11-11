package com.dslplatform

import com.dslplatform.api.client.{ Bootstrap, JsonSerialization }
import dispatch.Req

package object examples {
  implicit val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  val jsonSerialization = locator.resolve[JsonSerialization]

  implicit val executionContext =
    scala.concurrent.ExecutionContext.fromExecutor(
      java.util.concurrent.Executors.newCachedThreadPool
    )

  implicit def implicitStringToList(base: String): List[String] = List(base)

  implicit class RichString(base: String) {
    def /(s: String) = List(base, s)
  }

  implicit class RichStringList(base: List[String]) {
    def /(s: String) = base + s
  }

  implicit class RichReq(base: Req) {
    private def fillSvc(path: List[String])(implicit svc: Req): Req =
      path match {
        case segment :: tail => fillSvc(tail)(svc / segment)
        case Nil => svc
     }

     def /#(path: List[String]): Req = fillSvc(path)(base)
  }

  implicit class RichAny[T](base: => T) {
    def repeated(count: Int): IndexedSeq[T] =
      (1 to count).map { _ =>
        base
      }
  }
}
