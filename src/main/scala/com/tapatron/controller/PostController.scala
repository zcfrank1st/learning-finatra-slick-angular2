package com.tapatron.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import com.google.inject.Provider
import com.tapatron.domain.User
import com.tapatron.error.Error
import com.tapatron.persistence.Post
import com.tapatron.service.PostService
import com.twitter.finagle.http.Status
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min, NotEmpty}
import com.twitter.util.Future

@Singleton
class PostController @Inject()(postService: PostService, subject: Provider[Option[User]]) extends Controller {

  get("/post") { request: GetPostsRequest =>
    postService.posts(request.limit)
  }

  post("/post") { request: CreatePostRequest =>
    subject.get().map { user =>
      val serviceOutcome: Future[Either[Error, Post]] = postService.create(request.title, user)
      ResponseCreator.create(serviceOutcome, response, Status.Created)
    } getOrElse {
      response.unauthorized
    }.toFuture
  }

  delete("/post/:id") { request: DeletePostRequest =>
    subject.get().map { user =>
      val serviceOutcome: Future[Either[Error, Unit]] = postService.deleteById(request.id)
      ResponseCreator.create(serviceOutcome, response, Status.Ok)
    } getOrElse {
      response.unauthorized
    }.toFuture
  }

  put("/post/:id") { request: UpdatePostRequest =>
    subject.get().map { user =>
      postService.updateById(request.id, request.title)
    } getOrElse {
      response.unauthorized
    }.toFuture
  }
}

case class GetPostsRequest(@Min(1) @Max(100) @QueryParam limit: Int = 10)

case class CreatePostRequest(@NotEmpty title: String)

case class UpdatePostRequest(@RouteParam id: UUID, title: String)

case class DeletePostRequest(@RouteParam id: UUID)
