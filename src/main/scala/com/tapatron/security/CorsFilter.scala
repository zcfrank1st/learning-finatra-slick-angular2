package com.tapatron.security

import com.google.inject.Inject
import com.twitter.finagle.http.filter.Cors
import com.twitter.finagle.http.filter.Cors.{Policy, HttpFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Service, SimpleFilter}
import com.twitter.inject.requestscope.FinagleRequestScope
import com.twitter.util.Future

class CorsFilter @Inject()(requestScope: FinagleRequestScope, sessionStore: SessionStore) extends SimpleFilter[Request, Response] {
  val allowsOrigin  = { origin: String => Some(origin) }
  val allowsMethods = { method: String => Some(Seq("GET", "POST", "PUT", "DELETE")) }
  val allowsHeaders = { headers: Seq[String] => Some(headers) }

  val policy = Cors.Policy(allowsOrigin, allowsMethods, allowsHeaders, supportsCredentials = true)
  val cors = new HttpFilter(Cors.UnsafePermissivePolicy)

  override def apply(request: Request, service: Service[Request, Response]): Future[Response] = {
    cors.apply(request, service)
  }
}