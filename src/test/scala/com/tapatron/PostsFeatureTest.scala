package com.tapatron

import com.google.inject.testing.fieldbinder.Bind
import com.tapatron.fixtures.PostFixtures._
import com.tapatron.persistence.PostDao
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status.{BadRequest, Created, Ok}
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class PostsFeatureTest extends FeatureTest with Mockito with HttpTest {
  @Bind val postDao = smartMock[PostDao]

  override val server = new EmbeddedHttpServer(new Server)

  "Post Controller" should {

    "return the posts" in {
      val posts = Seq(sportsPost, politicsPost, environmentPost)
      postDao.findAll() returns Future(posts)

      server.httpGet(
        path = "/user?limit=2",
        andExpect = Ok,
        withJsonBody = mapper.writeValueAsString(posts.take(2)))
    }

    "return bad request if limit is invalid" in {
      server.httpGet(
        path = "/user?limit=200",
        andExpect = BadRequest)
    }

    "create a new post" in {
      val post = sportsPost
      postDao.save(any) returns Future(post)

      val res:Response = server.httpPost(
        path = "/user",
        postBody =
          """
             {"title": "some post title"}
          """,
        andExpect = Created
      )
      res.getContentString() should include (post.title)
    }
  }
}
