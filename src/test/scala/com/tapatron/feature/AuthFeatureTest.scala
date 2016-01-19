package com.tapatron.feature

import com.tapatron.controller.Credentials
import com.tapatron.domain.User
import com.tapatron.security.SecurityUtils
import com.twitter.finagle.http.Status._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AuthFeatureTest extends FeatureSpec {

  "Api security" should {

    "authenticate a user" in {

      Given("an existing user")

      val user = User(username = "stein", password = "password")
      persistUsers(user)

      When("the api receives a login request from user")

      val authHeader = SecurityUtils.authHeaderFromCredentials(Credentials(user.username, user.password))
      val response = server.httpPost(path = "/login",
        headers = Map("Authorization" -> authHeader),
        postBody = "",
        andExpect = Ok)

      Then("a session cookie is returned")
      val authCookie = response.headerMap.get("Set-Cookie")
      authCookie.get should startWith regex "sid=+"

      Then("the session cookie can be used to authenticate future requests")

      server.httpPost(path = "/post",
        headers = Map("Cookie" -> authCookie.get),
        postBody =
          """
             {"title": "some post"}
          """,
        andExpect = Created)
    }

    "logout an already authenticated user" in {

      Given("an existing user")
      val user = User(username = "stein", password = "password")
      persistUsers(user)

      Given("the user is already logged in")
      val authHeader = SecurityUtils.authHeaderFromCredentials(Credentials(user.username, user.password))
      val response = server.httpPost(path = "/login",
        headers = Map("Authorization" -> authHeader),
        postBody = "",
        andExpect = Ok)
      val authCookie = response.headerMap.get("Set-Cookie")

      When("the api receives a request to log the user out")
      server.httpPost(path = "/logout",
        postBody = "",
        headers = Map("Cookie" -> authCookie.get),
        andExpect = Ok)

      Then("the user is no longer authenticated")
      Then("future requests will fail with 401")
      server.httpPost(path = "/post",
        headers = Map("Cookie" -> authCookie.get),
        postBody =
          """
             {"title": "should fail"}
          """,
        andExpect = Unauthorized)
    }

    "prevent an unathorised user accessing a protected resource" in {

      Given("user amy is registered with non-admin permissions")

      When("the api receives a request from amy for an admin resource")

      Then("an unauthorized response is returned to the user")

    }

  }
}
