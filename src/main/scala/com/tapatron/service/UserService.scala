package com.tapatron.service

import javax.inject.{Singleton, Inject}

import com.tapatron.common.TwitterConverters._
import com.tapatron.domain.User
import com.tapatron.persistence.UsersDao
import com.twitter.util.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.Int.MaxValue

@Singleton
class UserService @Inject()(usersDao: UsersDao) {

  def users(): Future[Seq[User]] = usersDao.findAll(MaxValue)

  def findOne(username: String, password: String): Future[Option[User]] = {
    usersDao.findByUsernameAndPassword(username, password).map { users =>
      users.headOption
    }
  }
}
