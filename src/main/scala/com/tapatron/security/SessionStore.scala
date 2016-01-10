package com.tapatron.security

import java.util.concurrent.{ConcurrentHashMap => JConcurrentHashMap}
import java.util.{Map => JMap}

import com.tapatron.domain.User

class SessionStore {

  val tokens: JMap[Token, User] = new JConcurrentHashMap[Token, User]()

  def addToken(token: Token, user: User) = {
    tokens.put(token, user)
  }

  def resolveUserFrom(token: Token): Option[User] = Option(tokens.get(token))

  def removeToken(token: Token) = {
    tokens.remove(token)
  }
}
