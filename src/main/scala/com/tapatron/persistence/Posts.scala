package com.tapatron.persistence

import java.time.LocalDateTime
import java.util.UUID

import com.tapatron.domain.Post
import com.tapatron.persistence.SlickExtension._
import slick.driver.PostgresDriver.api._

final class Posts(tag: Tag) extends Table[Post](tag, "posts") {
  def id = column[UUID]("id", O.PrimaryKey)

  def title = column[String]("title")

  def added = column[LocalDateTime]("added")

  def * = (id, title, added) <>(Post.tupled, Post.unapply)
}

object Posts {
  val query = TableQuery[Posts]
}