package com.tapatron.fixtures

import java.util.UUID.randomUUID
import java.util.{Date, UUID}

import com.tapatron.domain.Permission.{CREATE_POSTS, SUPER}
import com.tapatron.domain.{Post, Permissions, User}

object Common {
  val userId = UUID.fromString("ad2d5e58-12d9-4857-a952-c4e4be44caf3")
}

object PostFixtures {
  val sportsPost = Post(id = randomUUID(), title = "They won!!!", added = new Date().getTime + 1000, userId = Common.userId)
  val environmentPost = Post(id = randomUUID(), title = "High water levels detected", added = new Date().getTime + 100, userId = Common.userId)
  val politicsPost = Post(id = randomUUID(), title = "Election debate", new Date().getTime + 200, userId = Common.userId)

  val posts = Seq(sportsPost, environmentPost, politicsPost)
}

object UserFixtures {
  val adminUser = User(Common.userId, username = "stein", password = "password", permissions = new Permissions(Seq(CREATE_POSTS, SUPER)))

  val users = Seq(adminUser)
}
