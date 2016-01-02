package com.tapatron.controller

import java.util.UUID
import javax.inject.Inject

import com.tapatron.error.Error
import com.tapatron.persistence.Post
import com.tapatron.service.PostService
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min, NotEmpty}
import com.twitter.util.Future

class PostController @Inject()(postService: PostService, responseMapper: ResponseMapper) extends Controller {

  get("/user") { request: GetPostsRequest =>
    val serviceOutcome: Future[Seq[Post]] = postService.posts(request.limit)
    serviceOutcome
  }

  post("/user") { request: CreatePostRequest =>
    val serviceOutcome: Future[Either[Error, Post]] = postService.create(request.title)
    responseMapper.toResponse(serviceOutcome, response)
  }

  delete("/user/:id") { request: DeletePostRequest =>
    val serviceOutcome: Future[Either[Error, Unit]] = postService.deleteById(request.id)
    responseMapper.toResponse(serviceOutcome, response)
  }

  put("/user/:id") { request: UpdatePostRequest =>
    postService.updateById(request.id, request.title)
  }
}

case class GetPostsRequest(@Min(1) @Max(100) @QueryParam limit: Int = 10)

case class CreatePostRequest(@NotEmpty title: String)

case class UpdatePostRequest(@RouteParam id: UUID, title: String)

case class DeletePostRequest(@RouteParam id: UUID)
