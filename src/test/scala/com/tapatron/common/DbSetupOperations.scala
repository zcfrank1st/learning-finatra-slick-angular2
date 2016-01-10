package com.tapatron.common

import com.ninja_squad.dbsetup.Operations._
import com.ninja_squad.dbsetup.operation.Insert
import com.tapatron.domain.User
import com.tapatron.persistence.Post

object DbSetupOperations {
  val DeleteAll = deleteAllFrom("posts", "users")

  def insertUsers(users: Seq[User]): Insert = {
    val userBuilder = insertInto("users")
      .columns("id", "username", "password", "permissions")
    users.foreach(user => userBuilder.values(user.id, user.username, user.password, user.permissions.permissions.mkString(",")))
    userBuilder.build()
  }

  def insertPosts(posts: Seq[Post]): Insert = {
    val postBuilder = insertInto("posts")
      .columns("id", "title", "added", "user_id")
    posts.foreach(post => postBuilder.values(post.id, post.title, Long.box(post.added), post.userId))
    postBuilder.build()
  }
}
