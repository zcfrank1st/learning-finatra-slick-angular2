package com.tapatron.persistence

import java.time.LocalDateTime
import java.util.UUID

import com.tapatron.DB
import com.tapatron.domain.Post

import slick.driver.PostgresDriver.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PostDao extends DB {
  val posts = TableQuery[Posts]

  var fakeDb: Map[UUID, Post] = Map()

  def save(post: Post): Future[Post] = {
    fakeDb += (post.id -> post)
    Future(post)
  }

  def delete(id: UUID): Future[Unit] = Future(fakeDb -= id)

  def findAll(): Future[Seq[Post]] = {
    db.run(Posts.query.result)
  }

  def update(id: UUID, title: String): Future[Post] = {
    val updated = Post(id, title, LocalDateTime.now())
    fakeDb += (id -> updated)
    Future(updated)
  }
}
