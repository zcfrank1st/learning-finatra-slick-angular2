package com.tapatron.service

import java.util.UUID.randomUUID
import java.util.{Date, UUID}
import javax.inject.{Inject, Singleton}

import com.tapatron.common.TwitterConverters.scalaToTwitterFuture
import com.tapatron.error.{ServerError, NotFoundError, Error}
import com.tapatron.persistence.{Post, PostsDao}
import com.twitter.util.{Future, Promise}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

@Singleton
class PostService @Inject()(postDao: PostsDao) {

  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll(limit)

  def create(title: String): Future[Either[Error, Post]] = {
    val promise = new Promise[Either[Error, Post]]
    val post = Post(randomUUID(), title, new Date().getTime)

    postDao.save(post).onComplete {
      case Success(_) => promise.setValue(Right(post))
      case Failure(t) => promise.setException(t)
    }

    promise
  }

  def deleteById(id: UUID): Future[Either[Error, Unit]] = {
    val promise = new Promise[Either[Error, Unit]]
    postDao.delete(id).onComplete {
      case Success(n) if n == 0 => promise.setValue(Left(NotFoundError(s"No post with ID $id")))
      case Success(n) if n >= 1 => promise.setValue(Right())
      case Failure(f) => promise.setValue(Left(ServerError(s"Failed to delete post with ID $id")))
    }
    promise
  }

  def updateById(id: UUID, title: String): Future[Post] = ???
}
