package com.tapatron.service

import java.util.UUID.randomUUID
import java.util.{Date, UUID}
import javax.inject.{Inject, Singleton}

import com.tapatron.common.TwitterConverters.scalaToTwitterFuture
import com.tapatron.domain.{Post, PostsDao}
import com.tapatron.error.{ServerError, NotFoundError}
import com.twitter.util.{Promise, Future}

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class PostService @Inject()(postDao: PostsDao) {

  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll(limit)

  def create(title: String, user: UUID): Future[Post] = {
      val post = Post(randomUUID(), title, new Date().getTime, user)
      postDao.save(post).map(saved => post)
  }

  def deleteById(id: UUID): Future[Unit] = {
    val promise = Promise[Unit]

    val deleteOperation:Future[Int] = postDao.delete(id)

    deleteOperation onSuccess { num =>
      if (num == 1) promise.setDone() else promise.setException(NotFoundError(s"No post with ID $id"))
    } onFailure { ex =>
      promise.setException(ServerError("Failed to perform datastore operation", ex))
    }

    promise
  }

  def updateById(id: UUID, title: String): Future[Post] = postDao.updateById(id, title)
}
