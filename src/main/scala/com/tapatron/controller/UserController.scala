package com.tapatron.controller

import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

import com.google.inject.Provider
import com.tapatron.error.BadCredentialsError
import com.tapatron.persistence.User
import com.tapatron.security.Cookies.SessionTokenName
import com.tapatron.security.SecurityUtils.credentialsFromAuthHeader
import com.tapatron.security.{SecurityUtils, SessionStore}
import com.tapatron.service.UserService
import com.twitter.finagle.http.{Cookie, Request}
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.Header
import com.twitter.util.{Duration, Future}

class UserController @Inject()(sessionStore: SessionStore, userService: UserService, subject: Provider[Option[User]]) extends Controller {

  get("/user") { request: Request =>
    subject.get().map { user =>
      userService.users()
    } getOrElse {
      response.unauthorized
    }.toFuture
  }

  // TODO move bulk of this into service, http stuff should stay in this layer
  // TODO tests

  post("/login") { request: LoginRequest =>
    val creds: Option[Credentials] = credentialsFromAuthHeader(request.Authorization)
    creds.map { headerCreds => {
        val userLookup: Future[Option[User]] = userService.findOne(headerCreds.username, headerCreds.password)
        userLookup.map {
          case Some(_) =>
            val token = UUID.randomUUID().toString
            sessionStore.addToken(User(UUID.randomUUID(), "stein", "password"), token)
            response.ok.cookie(SessionTokenName, token)
          case None => response.unauthorized
        }
      }
    } getOrElse {
      response.unauthorized.toFuture
    }
  }

  post("/logout") { request: Request =>
    val authToken = request.cookies.get(SessionTokenName).map {
      cookie => sessionStore.removeToken(cookie.value)
    }
    val expiredCookie = new Cookie(SessionTokenName, "")
    expiredCookie.maxAge = Duration(-10, TimeUnit.DAYS)
    response.ok.cookie(expiredCookie).toFuture
  }
}

case class Credentials(username: String, password: String)

case class LoginRequest(@Header Authorization: String)
