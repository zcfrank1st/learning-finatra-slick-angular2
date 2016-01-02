package com.tapatron.common

import java.util.Date

import com.ninja_squad.dbsetup.Operations._
import com.tapatron.fixtures.PostFixtures

object DbSetupOperations {
  val DeleteAll = deleteAllFrom("posts")

  val postBuilder = insertInto("posts")
    .columns("id", "title", "added")
  PostFixtures.posts.foreach(post => postBuilder.values(post.id, post.title, Long.box(new Date().getTime)))
  val postFixtures = postBuilder.build()
}
