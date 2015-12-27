package com.tapatron.controller

import java.util.UUID
import javax.inject.Inject

import com.tapatron.service.PostService
import com.twitter.finatra.http.Controller
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min, NotEmpty}

class PostController @Inject()(postService: PostService) extends Controller {

  get("/user") { request: GetPostsRequest =>
    postService.posts(request.limit)
  }

  post("/user") { request: AddPostRequest =>
    val post = postService.create(request.title)
    response.created(post)
  }

  delete("/user/:id") { request: DeleteRequest =>
    postService.deleteById(request.id)
  }

  put("/user/:id") { request: UpdateRequest =>
    postService.updateById(request.id, request.title)
  }
}

case class GetPostsRequest(@Min(1) @Max(100) @QueryParam limit: Int = 10)

case class AddPostRequest(@NotEmpty title: String)

case class UpdateRequest(@RouteParam id: UUID, title: String)

case class DeleteRequest(@RouteParam id: UUID)
