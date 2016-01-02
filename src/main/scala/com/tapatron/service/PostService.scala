package com.tapatron.service

import java.util.UUID.randomUUID
import java.util.{Date, UUID}
import javax.inject.{Inject, Singleton}

import com.tapatron.common.TwitterConverters.scalaToTwitterFuture
import com.tapatron.persistence.{Post, PostsDao}
import com.twitter.util.{Future, Promise}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

@Singleton
class PostService @Inject()(postDao: PostsDao) {

  def posts(limit: Int): Future[Seq[Post]] = postDao.findAll(limit)

  def create(title: String): Future[Post] = {
    val promise = new Promise[Post]
    val post = Post(randomUUID(), title, new Date().getTime)

    postDao.save(post).onComplete {
      case Success(_) => promise.setValue(post)
      case Failure(t) => promise.setException(t)
    }

    promise
  }

  def deleteById(id: UUID): Future[Int] = postDao.delete(id)

  def updateById(id: UUID, title: String): Future[Post] = ???
}
