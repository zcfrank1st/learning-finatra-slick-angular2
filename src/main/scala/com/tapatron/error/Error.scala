package com.tapatron.error

abstract class Error(val reason: String, val status: Int)

final case class BadCredentialsError(override val reason: String) extends Error(reason, 400)

final case class NotFoundError(override val reason: String) extends Error(reason, 404)

final case class UnauthorizedError(override val reason: String) extends Error(reason, 403)

final case class ServerError(override val reason: String) extends Error(reason, 500)
