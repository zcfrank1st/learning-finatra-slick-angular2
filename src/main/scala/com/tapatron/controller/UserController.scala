package com.tapatron.controller

import java.util.UUID
import javax.inject.Inject

import com.google.inject.Provider
import com.tapatron.domain.User
import com.tapatron.security.Cookies.SessionTokenName
import com.tapatron.security.SecurityUtils.credentialsFromAuthHeader
import com.tapatron.security.{Cookies, SessionStore}
import com.tapatron.service.UserService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.Header
import com.twitter.util.Future

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
          case Some(user) =>
            val token = UUID.randomUUID().toString
            sessionStore.addToken(user, token)
            response.ok.cookie(SessionTokenName, token)
          case None => response.unauthorized
        }
      }
    } getOrElse {
      response.unauthorized.toFuture
    }
  }

  post("/logout") { request: Request =>
    request.cookies.get(SessionTokenName).map {
      cookie => sessionStore.removeToken(cookie.value)
    }
    response.ok.cookie(Cookies.ExpiredCookie).toFuture
  }
}

case class Credentials(username: String, password: String)

case class LoginRequest(@Header Authorization: String)
