package com.tapatron.security

import com.google.inject.Inject
import com.tapatron.domain.User
import com.tapatron.security.UserFilter.sessionTokenFrom
import com.twitter.finagle.http.{CookieMap, Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future

class UserFilter @Inject()(requestScope: FinagleRequestScope, sessionStore: SessionStore) extends SimpleFilter[Request, Response] {

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    val userFromSessionToken = for {
      token <- sessionTokenFrom(request.cookies)
      user <- sessionStore.resolveUserFrom(token)
    } yield user
    requestScope.seed[Option[User]](userFromSessionToken)
    service(request)
  }
}

object UserFilter {
  def sessionTokenFrom(cookies: CookieMap): Option[String] = {
    cookies.get(Cookies.SessionTokenName).map(cookie => cookie.value)
  }
}