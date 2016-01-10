package com.tapatron.service

import java.util.UUID.randomUUID
import java.util.{Date, UUID}
import javax.inject.{Inject, Singleton}

import com.tapatron.common.TwitterConverters.scalaToTwitterFuture
import com.tapatron.domain.Permission.CREATE_POSTS
import com.tapatron.domain.User
import com.tapatron.error.{UnauthorizedError, Error, NotFoundError, ServerError}
import com.tapatron.persistence.{Post, PostsDao}
import com.twitter.util.{Future, Promise}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

@Singleton
class PostService @Inject()(postDao: PostsDao) {

  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll(limit)

  def create(title: String, user: User): Future[Either[Error, Post]] = {
    val outcome = new Promise[Either[Error, Post]]

    if (user.hasPermissionTo(CREATE_POSTS)) {
      val post = Post(randomUUID(), title, new Date().getTime, UUID.fromString("ad2d5e58-12d9-4857-a952-c4e4be44caf3"))
      postDao.save(post).onComplete {
        case Success(_) => outcome.setValue(Right(post))
        case Failure(t) => outcome.setException(t)
      }
    } else {
      outcome.setValue(Left(UnauthorizedError("User is not permitted to access this resource")))
    }

    outcome
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

  def updateById(id: UUID, title: String): Future[Either[Error, Unit]] = {
    val outcome = new Promise[Either[Error, Unit]]

    postDao.delete(id) onComplete {
      case Success(n) if n == 0 => outcome.setValue(Left(NotFoundError(s"No post with ID $id")))
      case Success(n) if n >= 1 => outcome.setValue(Right())
      case Failure(f) => outcome.setValue(Left(ServerError(s"Failed to update post with ID $id")))
    }

    outcome
  }
}
