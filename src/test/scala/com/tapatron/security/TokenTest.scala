package com.tapatron.security

import org.scalatest.{FunSuite, Matchers}

class TokenTest extends FunSuite with Matchers {

  test("generates a random token") {
    val tokens: Seq[Token] = (0 to 1000).map(_ => Token.generate())
    tokens.distinct.size shouldEqual tokens.size
  }

}
