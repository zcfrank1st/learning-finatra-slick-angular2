package com.tapatron

import slick.driver.PostgresDriver.api._

trait DB {
  val db = Database.forConfig("database")
}
