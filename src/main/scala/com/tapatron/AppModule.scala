package com.tapatron

import com.google.inject.{Provides, Singleton}
import com.tapatron.security.SessionStore
import com.twitter.inject.TwitterModule
import slick.driver.PostgresDriver.api._

object AppModule extends TwitterModule {

  @Singleton
  @Provides
  def database() = Database.forConfig("database")

  @Singleton
  @Provides
  def sessionStore() = new SessionStore()
}
