package com.tapatron.security

import java.util.UUID

import com.google.inject.{Provides, Singleton}
import com.redis.RedisClient
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding
import com.typesafe.config.{ConfigFactory, Config}

object AuthModule extends TwitterModule with RequestScopeBinding {

  override def configure(): Unit = {
    val config = ConfigFactory.load()

    bindRequestScope[Option[UUID]]
    bind[Crypto].toInstance(new BasicCrypto(config.getString("webApi.cookie-secret")))
    bind[PasswordCrypto].toInstance(new BCryptPasswordCrypto)
  }

  @Singleton
  @Provides
  def sessionStore(config: Config): SessionStore =
    new RedisSessionStore(new RedisClient(config.getString("redis.host"), config.getInt("redis.port")))
}
