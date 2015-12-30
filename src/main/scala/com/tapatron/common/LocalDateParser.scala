package com.tapatron.common

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

object LocalDateParser extends StdSerializer[LocalDate](classOf[LocalDate]) {
  override def serialize(localDateValue: LocalDate, jgen: JsonGenerator, provider: SerializerProvider) {
    jgen.writeString(localDateValue.format(DateTimeFormatter.ISO_DATE))
  }
}