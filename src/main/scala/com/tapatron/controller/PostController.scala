package com.tapatron.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import com.google.inject.Provider
import com.tapatron.error.Error
import com.tapatron.persistence.{User, Post}
import com.tapatron.service.PostService
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min, NotEmpty}
import com.twitter.util.Future

@Singleton
class PostController @Inject()(postService: PostService, currentUser: Provider[Option[User]]) extends Controller {

  get("/post") { request: GetPostsRequest =>
    currentUser.get().map { user =>
      postService.posts(request.limit)
    } getOrElse  {
      response.unauthorized
    }.toFuture
  }

  post("/post") { request: CreatePostRequest =>
    val serviceOutcome: Future[Either[Error, Post]] = postService.create(request.title)
    ResponseCreator.create(serviceOutcome, response)
  }

  delete("/post/:id") { request: DeletePostRequest =>
    val serviceOutcome: Future[Either[Error, Unit]] = postService.deleteById(request.id)
    ResponseCreator.create(serviceOutcome, response)
  }

  put("/post/:id") { request: UpdatePostRequest =>
    postService.updateById(request.id, request.title)
  }
}

case class GetPostsRequest(@Min(1) @Max(100) @QueryParam limit: Int = 10)

case class CreatePostRequest(@NotEmpty title: String)

case class UpdatePostRequest(@RouteParam id: UUID, title: String)

case class DeletePostRequest(@RouteParam id: UUID)
