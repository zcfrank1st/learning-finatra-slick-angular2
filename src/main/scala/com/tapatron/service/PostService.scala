package com.tapatron.service

import java.time.{LocalDateTime, ZoneOffset}
import java.util.UUID
import javax.inject.{Inject, Singleton}

import com.tapatron.domain.Post
import com.tapatron.persistence.PostDao
import com.twitter.util.Future

@Singleton
class PostService @Inject()(postDao: PostDao) {
  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll().map(_.take(limit))

  def create(title: String): Future[Post] = {
    val post = Post(UUID.randomUUID(), title, LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(0)))
    postDao.save(post)
  }

  def deleteById(id: UUID): Future[Unit] = {
    postDao.delete(id)
  }

  def updateById(id: UUID, title: String): Future[Post] = {
    postDao.update(id, title)
  }

}
