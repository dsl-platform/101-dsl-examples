package com.dslplatform.examples

import WorldWonders.Wonder

import com.dslplatform.api.patterns.PersistableRepository
import scala.concurrent.Future

object Crud {
  private val wonderRepository = locator.resolve[PersistableRepository[Wonder]]

  // #####=====-----> CREATE <-----=====#####
  def create(wonder: Wonder): Future[Wonder] =
    wonderRepository.insert(wonder) map { _ =>
      wonder
    }

  def createList(wonderList: Array[Wonder]): Future[Array[Wonder]] =
    wonderRepository.insert(wonderList) map { _ =>
      wonderList
    }

  // #####=====-----> READ <-----=====#####
  def read(uri: String): Future[Wonder] =
    wonderRepository.find(uri)

  def readList(uriList: Array[String]): Future[Array[Wonder]] =
    wonderRepository.find(uriList).map(_.toArray)

  def readAll(): Future[Array[Wonder]] =
    wonderRepository.search(None).map(_.toArray)

  // #####=====-----> UPDATE <-----=====#####
  def update(updatedWonder: Wonder): Future[Wonder] =
    wonderRepository.update(updatedWonder) map { _ =>
      updatedWonder
    }

  def updateList(updatedWonderList: Array[Wonder]): Future[Array[Wonder]] =
    wonderRepository.update(updatedWonderList) map { _ =>
      updatedWonderList
    }

  // #####=====-----> DELETE <-----=====#####
  def delete(wonder: Wonder): Future[Unit] =
    wonderRepository.delete(wonder) map { _ =>
      ()
    }

  def deleteList(wonderList: Array[Wonder]): Future[Unit] =
    wonderRepository.delete(wonderList) map { _ =>
      ()
    }
}
