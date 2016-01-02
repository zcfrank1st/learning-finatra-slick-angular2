package com.tapatron.persistence

import java.util.UUID

import slick.driver.PostgresDriver.api._

import scala.concurrent.Future

trait Entity {
  val id: UUID
}

trait EntityKey {
  def id: slick.lifted.Rep[UUID]
}

class GenericDao[T <: Entity, U <: Table[T] with EntityKey](val table: TableQuery[U], val db: Database) {

  type Record = U#TableElementType

  def save(record: Record): Future[Int] = {
    val query = table += record
    db.run(query.transactionally)
  }

  def findAll(limit: Int): Future[Seq[Record]] = {
    db.run(table.take(limit).result)
  }

  def delete(id: UUID): Future[Int] = {
    val query = table.filter(row => row.id === id).delete
    db.run(query.transactionally)
  }
}
