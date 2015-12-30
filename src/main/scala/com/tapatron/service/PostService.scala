package com.tapatron.service

import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID.randomUUID
import javax.inject.{Inject, Singleton}

import com.tapatron.domain.Post
import com.tapatron.persistence.PostDao
import com.twitter.util.Future
import com.tapatron.common.TwitterConverters.scalaToTwitterFuture
import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class PostService @Inject()(postDao: PostDao) {

  def posts(limit: Int): Future[Seq[Post]] = scalaToTwitterFuture(postDao.findAll())
//  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll().map(_.take(limit))

  def create(title: String): Future[Post] = {
    val post = Post(randomUUID(), title, LocalDateTime.now())
    scalaToTwitterFuture(postDao.save(post))
  }

  def deleteById(id: UUID): Future[Unit] = scalaToTwitterFuture(postDao.delete(id))

  def updateById(id: UUID, title: String): Future[Post] = scalaToTwitterFuture(postDao.update(id, title))

}
