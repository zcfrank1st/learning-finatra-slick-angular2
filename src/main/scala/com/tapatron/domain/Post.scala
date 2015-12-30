package com.tapatron.domain

import java.time.LocalDateTime
import java.util.UUID
import java.util.UUID._

case class Post(id: UUID = randomUUID(), title: String, added: LocalDateTime)

