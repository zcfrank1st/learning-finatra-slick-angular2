package com.tapatron.feature

import com.tapatron.controller.Credentials
import com.tapatron.domain.User
import com.tapatron.security.SecurityUtils
import com.twitter.finagle.http.Status._
import org.junit.Test
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AuthFeatureTest extends FeatureSpec {

  "Api security" should {

    "authenticate a user" in {

      Given("An existing user")
      val user = User(username = "stein", password = "password")
      persistUsers(user)

      When("The api receives a login request from user")
      val authHeader = SecurityUtils.authHeaderFromCredentials(Credentials(user.username, user.password))
      val response = server.httpPost(path = "/login",
        headers = Map("Authorization" -> authHeader),
        postBody = "",
        andExpect = Ok)

      Then("A session cookie is returned")
      val authCookie = response.headerMap.get("Set-Cookie")
      authCookie.get should startWith regex "sid=+"

      Then("The session cookie can be used to authenticate future requests")
      server.httpPost(path = "/post",
        headers = Map("Cookie" -> authCookie.get),
        postBody =
          """
             {"title": "some post"}
          """,
        andExpect = Created)
    }

    "logout an already authenticated user" in {

      Given("An existing user")
      val user = User(username = "stein", password = "password")
      persistUsers(user)

      Given("The user is already logged in")
      val authHeader = SecurityUtils.authHeaderFromCredentials(Credentials(user.username, user.password))
      val response = server.httpPost(path = "/login",
        headers = Map("Authorization" -> authHeader),
        postBody = "",
        andExpect = Ok)
      val authCookie = response.headerMap.get("Set-Cookie")

      When("The api receives a request to log the user out")
      server.httpPost(path = "/logout",
        postBody = "",
        headers = Map("Cookie" -> authCookie.get),
        andExpect = Ok)

      Then("The user is no longer authenticated")
      Then("Future requests will fail with 401")
      server.httpPost(path = "/post",
        headers = Map("Cookie" -> authCookie.get),
        postBody =
          """
             {"title": "should fail"}
          """,
        andExpect = Unauthorized)
    }

  }

}
