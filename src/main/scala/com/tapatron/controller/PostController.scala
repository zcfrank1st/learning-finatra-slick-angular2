package com.tapatron.controller

import java.util.UUID
import javax.inject.{Inject, Singleton}

import com.google.inject.Provider
import com.tapatron.service.PostService
import com.twitter.finagle.http.Status
import com.twitter.finatra.request.{QueryParam, RouteParam}
import com.twitter.finatra.validation.{Max, Min, NotEmpty}

@Singleton
class PostController @Inject()(postService: PostService, subject: Provider[Option[UUID]]) extends Controller(subject) {

  get("/post") { request: GetPostsRequest =>
    val posts = postService.posts(request.limit)
    toResponse(posts, response)
  }

  post("/post") { request: CreatePostRequest =>
    requireUser { user =>
      val posts = postService.create(request.title, user)
      toResponse(posts, response, Status.Created)
    }
  }

  delete("/post/:id") { request: DeletePostRequest =>
    requireUser { user =>
      val deleted = postService.deleteById(request.id)
      toResponse(deleted, response)
    }
  }

  put("/post/:id") { request: UpdatePostRequest =>
    requireUser { user =>
      val updatedPost = postService.updateById(request.id, request.title)
      toResponse(updatedPost, response)
    }
  }
}

case class GetPostsRequest(@Min(1) @Max(100) @QueryParam limit: Int = 10)

case class CreatePostRequest(@NotEmpty title: String)

case class UpdatePostRequest(@RouteParam id: UUID, title: String)

case class DeletePostRequest(@RouteParam id: UUID)
