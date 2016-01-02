package com.tapatron.controller

import com.tapatron.error.Error
import com.tapatron.persistence.Post
import com.twitter.finagle.http.Response
import com.twitter.finatra.http.response.ResponseBuilder
import com.twitter.util.Future

class ResponseMapper {

  def toResponse(outcome: Future[Either[Error, _]], responseBuilder: ResponseBuilder): Future[Response] = {
    outcome flatMap {
      case Left(error) => responseBuilder.status(error.status).body(error.reason).toFuture
      case Right(result) => responseBuilder.ok(result).toFuture
    } rescue {
      case t => responseBuilder.internalServerError.toFutureException
    }
  }

}
