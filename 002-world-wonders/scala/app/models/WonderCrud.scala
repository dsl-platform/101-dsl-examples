package models

import com.dslplatform.api.patterns.PersistableRepository
import models.WorldWonders.Wonder

import scala.concurrent.Future

object WonderCrud {
  private lazy val wonderRepository = locator.resolve[PersistableRepository[Wonder]]

  /** Create a new wonder */
  def create(wonder: Wonder): Future[String] =
    wonderRepository.insert(wonder)

  /** Create new wonders */
  def create(wonders: Seq[Wonder]): Future[IndexedSeq[String]] =
    wonderRepository.insert(wonders)

  /** Find existing wonder by name */
  def read(name: String): Future[Wonder] =
    wonderRepository.find(name)

  /** Find all existing wonders */
  def readAll: Future[IndexedSeq[Wonder]] =
    wonderRepository.search

  /** Update an existing wonder */
  def update(wonder: Wonder): Future[Unit] =
    wonderRepository.update(wonder)

  /** Update a list of existing wonders */
  def update(wonders: Seq[Wonder]): Future[Unit] =
    wonderRepository.update(wonders)

  /** Delete a wonder */
  def delete(wonder: Wonder): Future[Unit] =
    wonderRepository.delete(wonder)

  /** Delete a list of wonders */
  def delete(wonders: Seq[Wonder]): Future[Unit] =
    wonderRepository.delete(wonders)
}
