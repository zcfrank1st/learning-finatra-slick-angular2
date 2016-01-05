package com.tapatron.persistence

import java.util.UUID
import java.util.UUID.randomUUID
import javax.inject.Inject

import slick.driver.PostgresDriver.api._

case class User(id: UUID = randomUUID(), username: String, password: String) extends Entity

final class Users(tag: Tag) extends Table[User](tag, "users") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def username = column[String]("username")

  def password = column[String]("password")

  def * = (id, username, password) <>(User.tupled, User.unapply)
}

class UsersDao @Inject()(db: Database) extends GenericDao[User, Users] (TableQuery[Users], db)
