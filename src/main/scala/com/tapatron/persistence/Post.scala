package com.tapatron.persistence

import java.util.UUID

case class Post(id: UUID, title: String, added: Long) extends Entity
