package com.tapatron.security

import com.tapatron.domain.User
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding

object AuthModule extends TwitterModule with RequestScopeBinding {

  override def configure(): Unit = {
    bindRequestScope[Option[User]]
  }
}
