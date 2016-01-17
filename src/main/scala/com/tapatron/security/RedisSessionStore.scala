package com.tapatron.security

import java.util.UUID

import com.redis._

trait SessionStore {
  def put(token: SessionID, userId: UUID)

  def resolveUser(token: SessionID): Option[UUID]

  def remove(token: SessionID)
}

class RedisSessionStore(val redis: RedisClient) extends SessionStore {

  def put(token: SessionID, userId: UUID) = redis.set(token.value, userId.toString)

  def resolveUser(token: SessionID): Option[UUID] = redis.get(token.value).map(userID => UUID.fromString(userID))

  def remove(token: SessionID) = redis.del(token.value)
}
