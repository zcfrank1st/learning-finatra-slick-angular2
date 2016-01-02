package com.tapatron

import com.google.inject.Provides
import com.twitter.inject.TwitterModule
import slick.driver.PostgresDriver.api._

object AppModule extends TwitterModule {

  @Provides
  def database() = {
    Database.forConfig("database")
  }

}
