package com.tapatron.security

trait Crypto {
  def encrypt(data: String): String

  def decrypt(data: String): Option[String]
}

trait PasswordCrypto {
  def encryptPassword(password: String): String

  def checkPassword(password: String, underTest: String): Boolean
}
