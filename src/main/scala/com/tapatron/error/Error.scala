package com.tapatron.error

abstract class Error(val reason: String, val status: Int)

final case class NotFoundError(override val reason: String) extends Error(reason, 404)

final case class ServerError(override val reason: String) extends Error(reason, 500)
