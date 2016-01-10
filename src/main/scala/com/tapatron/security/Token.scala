package com.tapatron.security

import java.util.UUID

case class Token(val underlying: String) extends AnyVal

object Token {
  def generate(): Token = Token(UUID.randomUUID().toString)
}
