package com.tapatron.feature

import com.fasterxml.jackson.databind.module.SimpleModule
import com.google.inject.testing.fieldbinder.Bind
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.tapatron.Server
import com.tapatron.common.DbSetupOperations
import com.tapatron.common.json.{LocalDateSerializer, LocalDateTimeSerializer}
import com.tapatron.fixtures.PostFixtures._
import com.tapatron.persistence.PostsDao
import com.twitter.finagle.http.Response
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.BeforeAndAfterEach
import com.ninja_squad.dbsetup.Operations._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PostsFeatureTest extends FeatureTest with Mockito with HttpTest with BeforeAndAfterEach {
  @Bind val postDao = smartMock[PostsDao]

  mapper.registerModule(new SimpleModule {
    addSerializer(LocalDateTimeSerializer)
    addSerializer(LocalDateSerializer)
  })

  val config:Config = ConfigFactory.load("dbSetup.conf")
  override def beforeEach() = {
    val operation = sequenceOf(DbSetupOperations.DeleteAll, DbSetupOperations.postFixtures)
    val dbSetup = new DbSetup(new DriverManagerDestination(
      config.getString("db.url"),
      config.getString("db.username"),
      config.getString("db.password")), operation)
    dbSetup.launch()
  }

  override val server = new EmbeddedHttpServer(new Server)

  "Post Controller" should {

    "return the posts" in {
      val limit = 2
      val posts = Seq(sportsPost, politicsPost)
      postDao.findAll(limit) returns Future(posts)

      server.httpGet(
        path = s"/user?limit=$limit",
        andExpect = Ok,
        withJsonBody = mapper.writeValueAsString(posts))
    }

    "return bad request if limit is invalid" in {
      server.httpGet(
        path = "/user?limit=200",
        andExpect = BadRequest)
    }

    "create a new post" in {
      postDao.save(any) returns Future(1)

      val res: Response = server.httpPost(
        path = "/user",
        postBody =
          """
             {"title": "some post title"}
          """,
        andExpect = Ok
      )
      res.getContentString() should include("some post title")
    }
  }
}
