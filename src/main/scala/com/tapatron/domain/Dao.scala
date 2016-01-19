package com.tapatron.domain

import java.util.UUID

import com.tapatron.DB
import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

trait Entity {
  val id: UUID
}

trait EntityKey {
  def id: slick.lifted.Rep[UUID]
}

class GenericDao[T <: Entity, U <: Table[T] with EntityKey](val table: TableQuery[U]) {

  val db = DB.connection
  type Record = U#TableElementType

  def save(record: Record): Future[Int] = {
    val query = table += record
    db.run(query.transactionally)
  }

  def findAll(limit: Int): Future[Seq[Record]] = {
    db.run(table.take(limit).result)
  }

  def findById(id: UUID): Future[Option[Record]] = {
    val query = table.filter(_.id === id)
    db.run(query.result.headOption)
  }

  def delete(id: UUID): Future[Int] = {
    val query = table.filter(row => row.id === id).delete
    db.run(query.transactionally)
  }
}
