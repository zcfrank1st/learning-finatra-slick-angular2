package com.tapatron.security

import com.github.t3hnar.bcrypt._
import org.scalatest.{FlatSpec, Matchers}

class PasswordEncryptionTest extends FlatSpec with Matchers {
  val encryptor = new PasswordCrypto {
    override def encryptPassword(password: String): String = password.bcrypt(generateSalt)
    override def checkPassword(encryptedPassword: String, underTest: String): Boolean = underTest.isBcrypted(encryptedPassword)
  }

  it should "encrypt the password with salt" in {
    val plain = "s3cr3t"

    val encrypted = encryptor.encryptPassword(plain)
    encrypted should not equal plain
    println(encrypted)

    encryptor.checkPassword(encrypted, "s3cr3t") shouldBe true
  }

  it should "fail if the passwords are different" in {
    val plain = "s3cr3t"

    val encrypted = encryptor.encryptPassword(plain)
    encrypted should not equal plain
    println(encrypted)

    encryptor.checkPassword(encrypted, "t3cr3t") shouldBe false
  }

}
