package com.tapatron.common

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.twitter.finatra.json.modules.FinatraJacksonModule

object CustomJacksonModule extends FinatraJacksonModule {
  override val additionalJacksonModules = Seq(new JavaTimeModule)
}
