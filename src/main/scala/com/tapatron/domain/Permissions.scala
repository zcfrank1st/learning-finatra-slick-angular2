package com.tapatron.domain

class Permissions(val permissions: Seq[Permission] = Seq()) {
  def hasPermission(permission: Permission): Boolean = permissions.contains(permission)
}

object Permissions {
  def simpleUser(): Permissions = new Permissions(Seq())
}
