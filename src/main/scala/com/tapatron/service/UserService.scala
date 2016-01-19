package com.tapatron.service

import java.util.UUID
import javax.inject.{Singleton, Inject}

import com.tapatron.common.TwitterConverters._
import com.tapatron.domain.{UsersDao, User}
import com.twitter.util.Future
import scala.concurrent.ExecutionContext.Implicits.global

import scala.Int.MaxValue

@Singleton
class UserService @Inject()(usersDao: UsersDao) {

  def users(): Future[Seq[User]] = usersDao.findAll(MaxValue)

  def findByUsername(username: String): Future[Option[User]] =
    usersDao.findByUsername(username).map(users => users.headOption)

  def findByID(id: UUID): Future[Option[User]] = usersDao.findById(id)
}
