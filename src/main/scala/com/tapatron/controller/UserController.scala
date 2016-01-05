package com.tapatron.controller

import java.util.UUID
import javax.inject.Inject

import com.tapatron.persistence.User
import com.tapatron.security.{SecurityUtils, SessionStore}
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.Header

class UserController @Inject()(sessionStore: SessionStore) extends Controller {
  post("/login") { request: LoginRequest =>
    SecurityUtils.credentialsFromAuthHeader(request.Authorization).fold(
      err => response.unauthorized,
      credentials => {
        if (credentials.username == "stein" && credentials.password == "password") { // TODO lookup in db
          val token = UUID.randomUUID().toString
          sessionStore.addToken(User(UUID.randomUUID(), "stein", "password"), token)
          response.ok.cookie("session_token", token)
        } else response.unauthorized
      }
    )
  }

  post("logout") { request: Request =>
    val authToken = request.cookies.get("auth_token")
      .map(cookie => sessionStore.removeToken(cookie.value))
    response.ok
  }
}

case class Credentials(username: String, password: String)

case class LoginRequest(@Header Authorization: String)
