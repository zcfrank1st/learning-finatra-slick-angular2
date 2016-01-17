package com.tapatron.controller

import java.util.UUID
import javax.inject.Inject

import com.google.inject.Provider
import com.tapatron.error.UnauthorizedError
import com.tapatron.security.SecurityUtils.credentialsFromAuthHeader
import com.tapatron.security.{EncryptedSessionCookie, SessionID, SessionService, Sessions}
import com.tapatron.service.UserService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.Header
import com.twitter.inject.Logging
import org.jboss.netty.handler.codec.http.DefaultCookie

class UserController @Inject()(userService: UserService,
                               sessionService: SessionService,
                               subject: Provider[Option[UUID]]) extends Controller with Logging {
  get("/user") { request: Request =>
    subject.get().map { user =>
      userService.users()
    } getOrElse {
      response.unauthorized
    }.toFuture
  }

  post("/login") { request: LoginRequest =>
    credentialsFromAuthHeader(request.Authorization).map { credentials => {
      sessionService.login(credentials).map { sessionID =>
        val cookie = new DefaultCookie(Sessions.SessionIdName, sessionID.value)
        response.ok().cookie(cookie)
      } rescue {
        case UnauthorizedError(message) =>
          debug(message)
          response.unauthorized.toFutureException
        case _ => response.internalServerError.toFutureException
      }
    }
    }.getOrElse(response.unauthorized.toFuture)
  }

  post("/logout") { request: Request =>
    request.cookies.get(Sessions.SessionIdName).foreach {
      cookie => sessionService.logout(EncryptedSessionCookie(cookie.value))
    }
    response.ok().cookie(Sessions.ExpiredSessionCookie).toFuture
  }
}

case class Credentials(username: String, password: String)

case class LoginRequest(@Header Authorization: String)
