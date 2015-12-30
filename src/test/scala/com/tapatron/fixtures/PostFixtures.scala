package com.tapatron.fixtures

import java.time.LocalDateTime
import java.util.UUID.randomUUID

import com.tapatron.domain.Post

object PostFixtures {
  val sportsPost = Post(randomUUID(), "They won!!!", LocalDateTime.now())
  val environmentPost = Post(randomUUID(), "High water levels detected", LocalDateTime.now())
  val politicsPost = Post(randomUUID(), "Election debate", LocalDateTime.now())
}
