package com.tapatron.security

import java.util.UUID

import com.google.inject.{Provides, Singleton}
import com.redis.RedisClient
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding
import org.jasypt.util.text.BasicTextEncryptor

object AuthModule extends TwitterModule with RequestScopeBinding {
  val CookieCryptoSecret = "r2fqwmg3g4343g" // TODO externalize

  override def configure(): Unit =
    bindRequestScope[Option[UUID]]

  @Singleton
  @Provides
  def crypto():Crypto = {
    val encryptor = new BasicTextEncryptor
    encryptor.setPassword(CookieCryptoSecret)
    new DefaultCrypto(encryptor)
  }

  @Singleton
  @Provides
  def sessionStore(): SessionStore =
    new RedisSessionStore(new RedisClient("localhost", 6379))

}
