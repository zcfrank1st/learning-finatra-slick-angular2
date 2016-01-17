package com.tapatron.security

import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSuite, Matchers}

class AuthFilterTest extends FunSuite with Matchers with MockitoSugar {

  test("returns the session token from the cookie if present") {
  }

  test("returns None if the cookies do not contain the token") {
  }
}
