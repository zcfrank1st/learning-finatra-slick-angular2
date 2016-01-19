package com.tapatron

import com.twitter.inject.TwitterModule

object AppModule extends TwitterModule {

  Runtime.getRuntime.addShutdownHook(
    new Thread(new Runnable() {
      override def run(): Unit = {
        DB.connection.close()
      }
    }))
}
