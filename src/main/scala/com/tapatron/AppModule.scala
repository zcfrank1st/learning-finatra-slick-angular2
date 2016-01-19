package com.tapatron

import com.twitter.inject.TwitterModule
import com.typesafe.config.{Config, ConfigFactory}

object AppModule extends TwitterModule {

  override protected def configure(): Unit = {
    bind[Config].toInstance(ConfigFactory.load())
  }

  Runtime.getRuntime.addShutdownHook(
    new Thread(new Runnable() {
      override def run(): Unit = {
        DB.connection.close()
      }
    }))
}
