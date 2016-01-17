package com.tapatron.feature

import com.google.inject.testing.fieldbinder.Bind
import com.tapatron.controller.Credentials
import com.tapatron.domain.User
import com.tapatron.security.{SecurityUtils, SessionStore}
import com.twitter.finagle.http.Status._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class AuthFeatureTest extends FeatureSpec {

  @Bind val sessionStore = mock[SessionStore]

  "Api security" should {

    "Authenticate a user" in {

      Given("An existing user")
      val adam = User(username = "adam", password = "adam8")
      persistUsers(Seq(adam))

      When("The api receives a login request from user")
      val authHeader = SecurityUtils.authHeaderFromCredentials(Credentials(adam.username, adam.password))
      val response = server.httpPost(path = "/login",
        headers = Map("Authorization" -> authHeader),
        postBody = "",
        andExpect = Ok)

      Then("A session cookie is returned")
      val authCookie = response.headerMap.get("Set-Cookie")
      authCookie.get should startWith regex "sid=+"

      Then("The session cookie can be used to authenticate future requests")

    }

  }

}
