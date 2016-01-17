package com.tapatron.security

import javax.annotation.concurrent.ThreadSafe

import org.jasypt.util.text.BasicTextEncryptor

import scala.util.Try

trait Crypto {
  def encrypt(data: String): String
  def decrypt(data: String): Option[String]
}

@ThreadSafe
class DefaultCrypto(crypto: BasicTextEncryptor) extends Crypto {

  def encrypt(data: String): String = crypto.encrypt(data)

  def decrypt(data: String): Option[String] = Try(crypto.decrypt(data)).toOption
}
