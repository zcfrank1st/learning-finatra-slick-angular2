package com.tapatron.persistence

import java.time.{ZoneOffset, LocalDateTime}
import java.util.UUID

import com.tapatron.domain.Post
import com.twitter.util.Future

class PostDao {
  var fakeDb: Map[UUID, Post] = Map()

  def save(post: Post): Future[Post] = {
    fakeDb += (post.id -> post)
    Future.value(post)
  }

  def delete(id: UUID): Future[Unit] = Future.value(fakeDb -= id)

  def findAll(): Future[Seq[Post]] = Future.value(fakeDb.values.toList)

  def update(id: UUID, title: String): Future[Post] = {
    val updated = Post(id, title, LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0)))
    fakeDb += (id -> updated)
    Future.value(updated)
  }
}
