package com.tapatron.security

import java.util.concurrent.{ConcurrentHashMap => JConcurrentHashMap}
import java.util.{Map => JMap}

import com.tapatron.persistence.User

import scala.collection.JavaConversions.asScalaSet

class SessionStore {
  val tokens: JMap[User, String] = new JConcurrentHashMap[User, String]()

  def isAuthenticated(userId: String, tokenUnderTest: String): Boolean = {
    Option(tokens.get(userId)).contains(tokenUnderTest)
  }

  def addToken(user: User, token: String) = {
    tokens.put(user, token)
  }

  def resolveUserFrom(token: String): Option[User] = {
    asScalaSet(tokens.entrySet())
      .filter(entry => entry.getValue == token)
      .map(entry => entry.getKey)
      .headOption
  }

  def removeToken(token: String) = {
    tokens.values.remove(token)
  }
}
