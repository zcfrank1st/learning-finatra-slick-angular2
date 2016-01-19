package com.tapatron

import com.typesafe.config.ConfigFactory
import slick.driver.PostgresDriver.api._

object DB {
  val config = ConfigFactory.load()
  val connection = Database.forConfig("database")
}