package com.tapatron.persistence

import java.sql.{Date, Timestamp}
import java.time.{LocalDate, LocalDateTime, ZoneOffset}

import slick.driver.PostgresDriver.api._

object SlickExtension {

  implicit val JavaLocalDateTimeMapper = MappedColumnType.base[LocalDateTime, Timestamp](
    ldt => Timestamp.from(ldt.toInstant(ZoneOffset.ofHours(0))),
    ts => ts.toLocalDateTime
  )

  implicit val JavaLocalDateMapper = MappedColumnType.base[LocalDate, Date](
    d => Date.valueOf(d),
    d => d.toLocalDate
  )
}
