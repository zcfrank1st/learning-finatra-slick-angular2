package com.tapatron.common

import java.time.LocalDateTime

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

object LocalDateTimeParser extends StdSerializer[LocalDateTime](classOf[LocalDateTime]) {
  override def serialize(localDateValue: LocalDateTime, jgen: JsonGenerator, provider: SerializerProvider) {
    jgen.writeString(localDateValue.toString)
  }
}