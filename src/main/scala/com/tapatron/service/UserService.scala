package com.tapatron.service

import javax.inject.Singleton

import com.tapatron.domain.User
import com.twitter.util.Future

@Singleton
class UserService {
  def users(): Future[Seq[User]] = {
    val users = Seq(User("stein", 35), User("Sam", 45))
    Future.value(users)
  }
}
