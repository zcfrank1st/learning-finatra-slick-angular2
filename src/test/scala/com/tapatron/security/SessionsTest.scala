package com.tapatron.security

import com.redis.RedisClient
import com.tapatron.fixtures.UserFixtures.adminUser
import org.mockito.Mockito._
import org.scalatest.OptionValues._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{BeforeAndAfterEach, FunSuite, Matchers}

class SessionsTest extends FunSuite with Matchers with MockitoSugar with BeforeAndAfterEach {

  var underTest: RedisSessionStore = _
  var redisCli: RedisClient = _

  override def beforeEach() = {
    redisCli = mock[RedisClient]
    underTest = new RedisSessionStore(redisCli)
  }

  test("adds a token to the session") {
    val token = SessionID("123")
    val a: Option[String] = redisCli.get(token.value)
    when(redisCli.get(token.value)).thenReturn(Some(adminUser.id.toString))

    underTest.put(token, adminUser.id)

    underTest.resolveUser(token).value shouldBe adminUser.id
  }

  test("removes token") {
    val token = SessionID("123")
    when(redisCli.get(token.value)).thenReturn(None)
    underTest.put(token, adminUser.id)

    underTest.remove(token)

    underTest.resolveUser(token) shouldBe None
  }

}
