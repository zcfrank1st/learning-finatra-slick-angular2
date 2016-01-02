package com.tapatron.fixtures

import java.util.Date
import java.util.UUID.randomUUID

import com.tapatron.persistence.Post

object PostFixtures {
  val sportsPost = Post(randomUUID(), "They won!!!", new Date().getTime)
  val environmentPost = Post(randomUUID(), "High water levels detected", new Date().getTime)
  val politicsPost = Post(randomUUID(), "Election debate", new Date().getTime)

  val posts = Seq(sportsPost, environmentPost, politicsPost)
}
