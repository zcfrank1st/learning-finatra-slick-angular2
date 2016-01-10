package com.tapatron.persistence

import java.util.UUID
import java.util.UUID.randomUUID
import javax.inject.{Singleton, Inject}

import com.fasterxml.jackson.annotation.JsonProperty
import slick.driver.PostgresDriver.api._

case class Post(id: UUID = randomUUID(), title: String, added: Long, @JsonProperty("user_id") userId: UUID) extends Entity

final class Posts(tag: Tag) extends Table[Post](tag, "posts") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def title = column[String]("title")

  def added = column[Long]("added")

  def userId = column[UUID]("user_id")

  def * = (id, title, added, userId) <>(Post.tupled, Post.unapply)

  val user = foreignKey("user_fk", userId, TableQuery[Users])(_.id)
}

@Singleton
class PostsDao @Inject()(db: Database) extends GenericDao[Post, Posts] (TableQuery[Posts], db)
