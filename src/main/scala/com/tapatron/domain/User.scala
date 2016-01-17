package com.tapatron.domain

import java.util.UUID
import java.util.UUID._
import javax.inject.{Inject, Singleton}

import slick.driver.PostgresDriver.api._
import SlickExtension._

import scala.concurrent.Future

case class User(id: UUID = randomUUID(),
                username: String,
                password: String,
                permissions: Permissions = new Permissions(Seq())) extends Entity {

  def hasPermissionTo(permission: Permission): Boolean = permissions.contains(permission)
}

final class Users(tag: Tag) extends Table[User](tag, "users") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def username = column[String]("username")

  def password = column[String]("password")

  def permissions = column[Permissions]("permissions")

  def userId = column[UUID]("user_id")

  def * = (id, username, password, permissions) <>(User.tupled, User.unapply)

  def user = foreignKey("posts_users_fk", userId, TableQuery[Users])(_.id)
}

@Singleton
class UsersDao @Inject()(db: Database) extends GenericDao[User, Users](TableQuery[Users], db) {
  def findByUsernameAndPassword(username: String, password: String): Future[Seq[User]] = {
    val query = table.filter(row => row.username === username && row.password === password)
    db.run(query.result)
  }
}
