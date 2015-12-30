package com.tapatron.common

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.module.SimpleModule
import com.twitter.finatra.json.modules.FinatraJacksonModule

object CustomJacksonModule extends FinatraJacksonModule {

  override val additionalJacksonModules = Seq(
    new SimpleModule {
      addSerializer(LocalDateTimeParser)
    })

  override val serializationInclusion = Include.NON_EMPTY
}
