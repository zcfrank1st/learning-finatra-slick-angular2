package com.tapatron.fixtures

import java.util.UUID

import com.tapatron.domain.Post

object PostFixtures {
  val sportsPost = Post(UUID.randomUUID(), "They won!!!", 314515161)
  val environmentPost = Post(UUID.randomUUID(), "High levels detected", 314515165)
  val politicsPost = Post(UUID.randomUUID(), "Election debate", 314515169)
}
