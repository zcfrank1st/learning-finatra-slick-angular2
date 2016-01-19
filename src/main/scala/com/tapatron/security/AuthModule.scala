package com.tapatron.security

import java.util.UUID

import com.github.t3hnar.bcrypt._
import com.google.inject.{Provides, Singleton}
import com.redis.RedisClient
import com.twitter.inject.TwitterModule
import com.twitter.inject.requestscope.RequestScopeBinding
import com.twitter.util.Try
import com.typesafe.config.Config
import org.jasypt.util.text.BasicTextEncryptor

object AuthModule extends TwitterModule with RequestScopeBinding {
  val CookieCryptoSecret = "r2fqwmg3g4343g" // TODO externalize

  override def configure(): Unit = {
    bindRequestScope[Option[UUID]]
  }

  @Singleton
  @Provides
  def crypto(): Crypto = {
    val encryptor = new BasicTextEncryptor
    encryptor.setPassword(CookieCryptoSecret)

    new Crypto {
      override def decrypt(data: String): Option[String] = Try(encryptor.decrypt(data)).toOption

      override def encrypt(data: String): String = encryptor.encrypt(data)
    }
  }

  @Singleton
  @Provides
  def passwordCrypto(): PasswordCrypto = {
    new PasswordCrypto {
      override def encryptPassword(password: String): String = password.bcrypt(generateSalt)

      override def checkPassword(password: String, underTest: String): Boolean = underTest.isBcrypted(password)
    }
  }

  @Singleton
  @Provides
  def sessionStore(config: Config): SessionStore =
    new RedisSessionStore(new RedisClient(config.getString("redis.host"), config.getInt("redis.port")))
}
