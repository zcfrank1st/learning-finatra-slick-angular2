package com.tapatron.controller

import com.tapatron.error.Error
import com.twitter.finagle.http.{Status, Response}
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future

object ResponseCreator {

  def create(outcome: Future[Either[Error, _]], responseBuilder: ResponseBuilder, status: Status): Future[Response] = {
    outcome flatMap {
      case Left(error) => responseBuilder.status(error.status).body(error.reason).toFuture
      case Right(result) => status match {
        case Status.Ok => responseBuilder.ok(result).toFuture
        case Status.Created => responseBuilder.created(result).toFuture
      }
    } rescue {
      case throwable => responseBuilder.internalServerError.toFutureException
    }
  }
}
