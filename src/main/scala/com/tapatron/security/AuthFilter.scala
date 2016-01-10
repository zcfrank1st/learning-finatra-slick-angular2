package com.tapatron.security

import com.google.inject.Inject
import com.tapatron.domain.User
import com.twitter.finagle.http.{Cookie, CookieMap, Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future

class AuthFilter @Inject()(requestScope: FinagleRequestScope, sessionStore: SessionStore) extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val user: Option[User] = AuthFilter.resolveUserFromRequest(request, sessionStore)
    requestScope.seed[Option[User]](user)
    service(request)
  }
}

object AuthFilter {
  def resolveUserFromRequest(request: Request, sessionStore: SessionStore):Option[User] = {
    sessionTokenFrom(request.cookies).flatMap(token =>
      sessionStore.resolveUserFrom(token)
    )
  }

  def sessionTokenFrom(cookies: CookieMap): Option[String] = {
    val sessionTokenCookie: Option[Cookie] = cookies.get(Cookies.SessionTokenName)
    sessionTokenCookie.map(cookie => cookie.value)
  }
}
