package com.dslplatform.examples

import WorldWonders.Wonder

import com.dslplatform.api.client.Bootstrap
import com.dslplatform.api.patterns.PersistableRepository
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Crud {
  private val locator = Bootstrap.init(getClass.getResourceAsStream("/dsl-project.props"))
  private val wonderRepository = locator.resolveUnsafe[PersistableRepository[Wonder]]

  // #####=====-----> CREATE <-----=====#####
  def create(wonder: Wonder): Future[Wonder] =
    wonderRepository.insert(wonder) map { _ =>
      wonder
    }

  def createList(wonderList: List[Wonder]): Future[List[Wonder]] =
    wonderRepository.insert(wonderList) map { _ =>
      wonderList
    }

  // #####=====-----> READ <-----=====#####
  def read(uri: String): Future[Wonder] =
    wonderRepository.find(uri)

  def readList(uriList: List[String]): Future[List[Wonder]] =
    wonderRepository.find(uriList).map(_.toList)

  def readAll(): Future[List[Wonder]] =
    wonderRepository.search(None).map(_.toList)

  // #####=====-----> UPDATE <-----=====#####
  def update(updatedWonder: Wonder): Future[Wonder] =
    wonderRepository.update(updatedWonder) map { _ =>
      updatedWonder
    }

  def updateList(updatedWonderList: List[Wonder]): Future[List[Wonder]] =
    wonderRepository.update(updatedWonderList) map { _ =>
      updatedWonderList
    }

  // #####=====-----> DELETE <-----=====#####
  def delete(wonder: Wonder): Future[Unit] =
    wonderRepository.delete(wonder) map { _ =>
      ()
    }

  def deleteList(wonderList: List[Wonder]): Future[Unit] =
    wonderRepository.delete(wonderList) map { _ =>
      ()
    }
}
