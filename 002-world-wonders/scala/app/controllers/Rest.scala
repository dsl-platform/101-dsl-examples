package controllers

import com.dslplatform.examples.Crud
import play.api.mvc.{ Action, Controller }
import com.dslplatform.examples.WorldWonders.Wonder

object Rest extends Controller
    with CustomParsers
    with CustomWriteables {
  private val wonderParser     = platformJson[Wonder]
  private val wonderListParser = platformJson[Array[Wonder]]

  def findAll = Action.async {
    Crud.readAll map { wonderList =>
      Ok(wonderList)
    }
  }

  def updateList = Action.async(wonderListParser) { request =>
    Crud.updateList(request.body) map { wonderList =>
      Ok(wonderList)
    }
  }

  def createList = Action.async(wonderListParser) { request =>
    Crud.createList(request.body) map { wonderList =>
      Ok(wonderList)
    }
  }

  def deleteList = Action.async(wonderListParser) { request =>
    Crud.deleteList(request.body) map { _ =>
      Ok
    }
  }
}
