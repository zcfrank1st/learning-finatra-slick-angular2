package com.tapatron.persistence

import java.util.UUID
import javax.inject.Inject

import slick.driver.PostgresDriver.api._

final class Posts(tag: Tag) extends Table[Post](tag, "posts") with EntityKey {
  def id = column[UUID]("id", O.PrimaryKey)

  def title = column[String]("title")

  def added = column[Long]("added")

  def * = (id, title, added) <>(Post.tupled, Post.unapply)
}

class PostsDao @Inject()(db: Database) extends GenericDao[Post, Posts] (TableQuery[Posts], db)
