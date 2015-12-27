package com.tapatron.domain

import java.util.UUID

case class Post(id: UUID = UUID.randomUUID(), title: String, added: Long)

