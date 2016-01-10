package com.tapatron.security

import com.tapatron.fixtures.UserFixtures.adminUser
import org.scalatest.{Matchers, FunSuite}
import org.scalatest.OptionValues._

class SessionStoreTest extends FunSuite with Matchers {

  test("adds a token to the session") {
    val token = Token("123")
    val user = adminUser

    val underTest = new SessionStore
    underTest.addToken(token, adminUser)

    underTest.resolveUserFrom(token).value shouldBe user
  }

  test("removes token") {
    val token = Token("123")
    val user = adminUser

    val underTest = new SessionStore
    underTest.addToken(token, adminUser)

    underTest.resolveUserFrom(token).value shouldBe user

    underTest.removeToken(token)
    underTest.resolveUserFrom(token) shouldBe None
  }

}
