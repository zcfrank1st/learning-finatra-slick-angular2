package com.tapatron.domain

import java.util.UUID
import java.util.UUID.randomUUID
import javax.inject.{Inject, Singleton}

import com.fasterxml.jackson.annotation.JsonProperty
import slick.driver.PostgresDriver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class Post(id: UUID = randomUUID(),
                title: String,
                added: Long,
                @JsonProperty("user_id") userId: UUID) extends Entity

final class Posts(tag: Tag) extends Table[Post](tag, "posts") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def title = column[String]("title")

  def added = column[Long]("added")

  def userId = column[UUID]("user_id")

  def * = (id, title, added, userId) <>(Post.tupled, Post.unapply)

  val user = foreignKey("user_fk", userId, TableQuery[Users])(_.id)
}

@Singleton
class PostsDao @Inject()(db: Database) extends GenericDao[Post, Posts] (TableQuery[Posts], db) {
  def updateById(id: UUID, title: String): Future[Post] = {
    val query = table.filter(row => row.id === id).map(_.title)
    db.run(query.update(title)).flatMap(numUpdated => findById(id).map(_.get))
  }
}
