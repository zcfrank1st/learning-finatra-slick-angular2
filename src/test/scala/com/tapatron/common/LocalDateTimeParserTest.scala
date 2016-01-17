package com.tapatron.common

import java.time.LocalDateTime

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.tapatron.common.json.LocalDateTimeSerializer
import org.junit.runner.RunWith
import org.scalatest.FreeSpec
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class LocalDateTimeParserTest extends FreeSpec {
  val mapper = new ObjectMapper()
  val module = new SimpleModule()
  module.addSerializer(classOf[LocalDateTime], LocalDateTimeSerializer)
  mapper.registerModule(module)

  "LocalDateTimeSerializer" - {

    "should serialize the date as a string" in {
      val ldt = LocalDateTime.of(2014, 1, 1, 13, 30)
      val serialized = mapper.writeValueAsString(ldt)

      assert(serialized === "\"2014-01-01T13:30\"")
    }

    "should handle nulls" in {
      val ldt = null

      val serialized = mapper.writeValueAsString(ldt)

      assert(serialized === "null")
    }
  }
}
