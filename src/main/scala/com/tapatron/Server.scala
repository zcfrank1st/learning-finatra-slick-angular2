package com.tapatron

import com.tapatron.common.json.CustomJacksonModule
import com.tapatron.controller.{PostController, UserController}
import com.tapatron.security.{AuthFilter, AuthModule, CorsFilter}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.filters.CommonFilters
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.logging.filter.{LoggingMDCFilter, TraceIdMDCFilter}
import com.twitter.finatra.logging.modules.Slf4jBridgeModule
import com.twitter.inject.requestscope.FinagleRequestScopeFilter
import com.typesafe.config.ConfigFactory

object ServerMain extends Server

class Server extends HttpServer {
  val config = ConfigFactory.load()

  override def jacksonModule = CustomJacksonModule

  override def modules = Seq(Slf4jBridgeModule, AppModule, AuthModule)

  override def defaultFinatraHttpPort = s":${config.getString("webApi.port")}"

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .filter[FinagleRequestScopeFilter[Request, Response]]
      .filter[AuthFilter]
      .filter[CorsFilter]
      .add[UserController]
      .add[PostController]
  }
}
