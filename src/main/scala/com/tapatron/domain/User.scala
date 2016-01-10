package com.tapatron.domain

import java.util.UUID
import java.util.UUID._

import com.tapatron.persistence.Entity

case class User(id: UUID = randomUUID(),
                username: String,
                password: String,
                permissions: Permissions = new Permissions(Seq())) extends Entity {

  def hasPermissionTo(permission: Permission): Boolean = permissions.contains(permission)
}
