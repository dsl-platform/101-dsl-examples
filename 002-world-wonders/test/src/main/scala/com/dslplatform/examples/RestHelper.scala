package com.dslplatform.examples

import com.ning.http.client.{ Response => NingResponse }
import dispatch.{ :/, Http, Req }
import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

case class Response(status: Int, body: Array[Byte])

class RestHelper(val host: String, val port: Int) {
  implicit val baseSvc = :/(host, port)

  def get(path: List[String]): Response = {
    val svc = baseSvc /# path
    send(svc)
  }

  def put(path: List[String], body: Array[Byte]): Response = {
    val svc = (baseSvc /# path).PUT.setBody(body)
    send(svc)
  }

  def post(path: List[String], body: Array[Byte]): Response = {
    val svc = (baseSvc /# path).POST.setBody(body)
    send(svc)
  }

  def delete(path: List[String]): Response = {
    val svc = (baseSvc /# path).DELETE
    send(svc)
  }

  private def send(svc: Req): Response = {
    val r = Http(svc > respParser)
    Await.result(r, 10 seconds)
  }

  val respParser = (r: NingResponse) =>
    Response(r.getStatusCode(), r.getResponseBodyAsBytes())
}
