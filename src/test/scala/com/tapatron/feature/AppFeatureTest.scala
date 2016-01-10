package com.tapatron.feature

import com.fasterxml.jackson.databind.module.SimpleModule
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup.operation.Insert
import com.tapatron.Server
import com.tapatron.common.DbSetupOperations
import com.tapatron.common.json.{LocalDateSerializer, LocalDateTimeSerializer}
import com.tapatron.domain.User
import com.tapatron.persistence.Post
import com.twitter.finagle.http.Status._
import com.twitter.finatra.http.test.{EmbeddedHttpServer, HttpTest}
import com.twitter.inject.Mockito
import com.twitter.inject.server.FeatureTest
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterEach, GivenWhenThen}

class AppFeatureTest extends FeatureTest with Mockito with HttpTest with BeforeAndAfterEach with GivenWhenThen {

  override val server = new EmbeddedHttpServer(new Server)

  private val config: Config = ConfigFactory.load("dbSetup.conf")

  private val dbConfig = new DriverManagerDestination(
    config.getString("db.url"),
    config.getString("db.username"),
    config.getString("db.password"))

  mapper.registerModule(new SimpleModule {
    addSerializer(LocalDateTimeSerializer)
    addSerializer(LocalDateSerializer)
  })

  override def beforeEach() = clearDatabase()

  protected def clearDatabase(): Unit = new DbSetup(dbConfig, DbSetupOperations.DeleteAll).launch()

  protected def persistUsers(users: Seq[User]): Unit = insert(DbSetupOperations.insertUsers(users))

  protected def persistPosts(posts: Seq[Post], byUser: User): Unit = {
    insert(DbSetupOperations.insertUsers(Seq(byUser)))
    insert(DbSetupOperations.insertPosts(posts))
  }

  protected def loginUserAndGetSessionToken(): String = {
    val response = server.httpPost(path = "/login",
      headers = Map("Authorization" -> "Basic c3RlaW46cGFzc3dvcmQ="),
      postBody = "",
      andExpect = Ok)

    val authCookie = response.headerMap.get("Set-Cookie")
    authCookie.get
  }

  private def insert(operation: Insert): Unit = {
    new DbSetup(dbConfig, operation).launch()
  }
}