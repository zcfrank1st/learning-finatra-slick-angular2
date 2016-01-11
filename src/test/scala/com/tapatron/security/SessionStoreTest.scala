package com.tapatron.security

import com.tapatron.fixtures.UserFixtures.adminUser
import org.scalatest.OptionValues._
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}

class SessionStoreTest extends FunSuite with Matchers with BeforeAndAfterEach {

  var underTest: SessionStore = _

  override def beforeEach() = {
    underTest = new SessionStore
  }

  test("adds a token to the session") {
    val token = Token("123")

    underTest.addToken(token, adminUser)

    underTest.resolveUserFrom(token).value shouldBe adminUser
  }

  test("removes token") {
    val token = Token("123")
    underTest.addToken(token, adminUser)

    underTest.removeToken(token)

    underTest.resolveUserFrom(token) shouldBe None
  }

}
