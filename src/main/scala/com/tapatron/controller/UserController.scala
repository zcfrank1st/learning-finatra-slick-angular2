package com.tapatron.controller

import javax.inject.Inject

import com.tapatron.service.UserService
import com.twitter.finagle.http.Request
import com.twitter.finatra.http.Controller

class UserController @Inject()(userService: UserService) extends Controller {

  post("/user") { request =>

  }

  get("/user/:name") { request: Request =>
    val name: String = request.params.getOrElse("name", "unknown")
    userService.users()
  }
}
