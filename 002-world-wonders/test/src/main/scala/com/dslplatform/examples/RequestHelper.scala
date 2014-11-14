package com.dslplatform.examples

import scala.reflect.ClassTag

class RequestHelper[T: ClassTag](val host: String, val port: Int) {
  val rest = new RestHelper(host, port)

  def get(path: List[String]): T = {
    execAndDeserialize("get") {
      rest.get(path)
    }
  }

  def getList(path: List[String]): IndexedSeq[T] = {
    execAndDeserializeList("getList") {
      rest.get(path)
    }
  }

  def put(path: List[String], item: T): T = {
    val body = jsonSerialization.serialize(item).getBytes
    execAndDeserialize("put") {
      rest.put(path, body)
    }
  }

  def post(path: List[String], item: T): T = {
    val body = jsonSerialization.serialize(item).getBytes
    execAndDeserialize("post") {
      rest.post(path, body)
    }
  }

  def delete(path: List[String]): T = {
    execAndDeserialize("delete") {
      rest.delete(path)
    }
  }

  def deleteList(path: List[String]): IndexedSeq[T] = {
    execAndDeserializeList("deleteList") {
      rest.delete(path)
    }
  }

  def options(path: List[String]): String = {
    val response = exec("options") {
      rest.options(path)
    }
    new String(response.body)
  }

  private def execAndDeserialize(name: String)(f: => Response): T = {
    val response = exec(name)(f)
    try {
      jsonSerialization.deserialize[T](response.body)
    } catch {
      case e: Exception =>
        val message = new String(response.body)
        throw new Exception(s"Error in deserialization during $name request with body:\n$message")
    }
  }

  private def execAndDeserializeList(name: String)(f: => Response): IndexedSeq[T] = {
    val response = exec(name)(f)
    jsonSerialization.deserializeList[T](response.body)
  }

  private def exec(name: String)(f: => Response): Response = {
    val response = f
    if (response.status != 200) {
      val status = response.status
      val message = new String(response.body)
      throw new Exception(s"Received an error response (HTTP $status) during $name request with message:\n$message")
    } else {
      response
    }
  }
}
