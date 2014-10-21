package controllers

import com.dslplatform.api.client.{ Bootstrap, JsonSerialization }
import com.dslplatform.examples.Crud
import play.api.mvc.{ Action, Controller }
import scala.concurrent.ExecutionContext.Implicits.global

object Rest extends Controller
    with CustomParsers
    with CustomWriteables {
  protected val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  protected val jsonSerialization = locator.resolve[JsonSerialization]

  def findAll = Action.async {
    Crud.readAll() map { r =>
      Ok(r)
    }
  }

  def find(id: String) = Action.async {
    Crud.read(id) map { wonder =>
      Ok(wonder)
    }
  }

  def updateAll() = Action {
    Ok(s"UPDATE ALL")
  }

  def update(id: String) = Action {
    Ok(s"UPDATE: $id")
  }

  def create = Action {
    Ok(s"CREATE")
  }

  def deleteAll = Action {
    Ok(s"DELETE ALL")
  }

  def delete(id: String) = Action {
    Ok(s"DELETE: $id")
  }
}
