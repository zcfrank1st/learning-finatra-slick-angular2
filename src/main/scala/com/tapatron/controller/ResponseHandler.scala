package com.tapatron.controller

import com.tapatron.error.{ServerError, UnauthorizedError}
import com.twitter.finagle.http.{Response, Status}
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.inject.Logging
import com.twitter.util.Future

object ResponseHandler extends Logging {

  def create(outcome: Future[_], responseBuilder: ResponseBuilder, status: Status = Status.Ok): Future[Response] = {
    outcome flatMap { result =>
      status match {
        case Status.Ok => responseBuilder.ok(result).toFuture
        case Status.Created => responseBuilder.created(result).toFuture
        case _ => responseBuilder.internalServerError.toFuture
      }
    } rescue {
      case UnauthorizedError(s) =>
        debug(s)
        responseBuilder.unauthorized.toFutureException
      case ServerError(s, e) =>
        error(s, e)
        responseBuilder.internalServerError.toFutureException
    }
  }
}
