package com.tapatron.persistence

import java.util.UUID

import slick.driver.PostgresDriver.api._

final class Posts(tag: Tag) extends Table[Post](tag, "posts") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def title = column[String]("title")

  def added = column[Long]("added")

  def * = (id, title, added) <>(Post.tupled, Post.unapply)
}

class PostsDao extends GenericDao[Post, Posts](TableQuery[Posts])
