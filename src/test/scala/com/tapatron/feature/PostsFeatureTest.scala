package com.tapatron.feature

import com.tapatron.common.TestUtils.objectMapper
import com.tapatron.domain.Post
import com.tapatron.fixtures.PostFixtures._
import com.tapatron.fixtures.UserFixtures.adminUser
import com.twitter.finagle.http.Status._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PostsFeatureTest extends FeatureSpec {

  "Post Controller" should {

    "return the posts" in {

      Given("three posts exist in the database")
      persistPosts(Seq(sportsPost, politicsPost, environmentPost), byUser = adminUser)

      When("the api receives a request for the most recent two posts")
      val response = server.httpGet(path = s"/post?limit=2")

      Then("the 2 most recent posts are returned")
      val content = response.getContentString
      val posts = objectMapper.readValue[Seq[Post]](content)

      response.getStatusCode() shouldEqual 200
      posts.length shouldEqual 2
      posts.head.added should be > posts(1).added
    }

    "return bad request if limit is invalid" in {

      Given("the the maximum limit for posts is 100")

      When("a request is made for 200 posts")
      val response = server.httpGet(path = "/post?limit=200", andExpect = BadRequest)

      Then("A bad request response is returned with a limit error")
      response.getStatusCode() shouldBe 400
      response.getContentString() should include ("limit")
    }

    "delete a post" in {

      Given("a post is stored in the database")
      And("and a user has rights to delete the post ")
      persistPosts(Seq(sportsPost), byUser = adminUser)

      Given("the user has authenticated with the api")
      val token = loginUserAndGetSessionToken(adminUser)

      When("The api receives a POST request with a valid post")
      val response = server.httpDelete(path = s"/post/${sportsPost.id}",
        headers = Map("Cookie" -> token),
        andExpect = Ok)

      Then("the post is deleted")
      response.getStatusCode() shouldBe 200
    }

    "create a new post" in {

      Given("a user exists with rights to create a post")
      persistUsers(Seq(adminUser))

      Given("the user has authenticated with the api")
      val token = loginUserAndGetSessionToken(adminUser)

      When("The api receives a POST request with a valid post")
      val response = server.httpPost(path = "/post",
        headers = Map("Cookie" -> token),
        postBody =
          """
             {"title": "some post title"}
          """,
        andExpect = Created)

      Then("the post is created and returned")
      response.getStatusCode() shouldBe 201
      val content = response.getContentString()
      val post = objectMapper.readValue[Post](content)
      post.title shouldBe "some post title"
    }

    "update an existing post" in {
      Given("a post is stored in the database")
      And("and a user has rights to modify the post ")
      persistPosts(Seq(sportsPost), byUser = adminUser)

      Given("the user has authenticated with the api")
      val token = loginUserAndGetSessionToken(adminUser)

      When("The api receives a POST request with a valid post")
      val response = server.httpPut(path = s"/post/${sportsPost.id}",
        putBody =
          """
             {"title": "updated title"}
          """,
        headers = Map("Cookie" -> token))

      Then("the post is updated")
      response.getStatusCode() shouldBe 200
      val content = response.getContentString()
      val post = objectMapper.readValue[Post](content)
      post.title shouldBe "updated title"
    }
  }
}
