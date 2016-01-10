package com.tapatron.security

import java.util.concurrent.TimeUnit

import com.twitter.finagle.http.Cookie
import com.twitter.util.Duration

object Cookies {
  val SessionTokenName = "session_token"

  val ExpiredCookie = new Cookie(SessionTokenName, "")
  ExpiredCookie.maxAge = Duration(-10, TimeUnit.DAYS)
}
