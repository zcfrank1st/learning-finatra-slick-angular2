package com.tapatron.security

import com.twitter.finagle.http.{Cookie, CookieMap}
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers}

class AuthFilterTest extends FunSuite with Matchers with MockitoSugar {

  test("returns the session token from the cookie if present") {
    val cookies = mock[CookieMap]
    when(cookies.get(Cookies.SessionTokenName)).thenReturn(Some(new Cookie("session_token", "5151")))

    val token = AuthFilter.sessionTokenFrom(cookies)

    token.get shouldBe "5151"
  }

  test("returns None if the cookies do not contain the token") {
    val cookies = mock[CookieMap]
    when(cookies.get(Cookies.SessionTokenName)).thenReturn(None)

    val token = AuthFilter.sessionTokenFrom(cookies)

    token shouldBe None
  }
}
