package com.tapatron.security

import com.tapatron.controller.Credentials
import com.tapatron.error.BadCredentialsError
import org.apache.commons.codec.binary.{Base64 => ApacheBase64}

object SecurityUtils {

  object Base64 {
    def decode(encoded: String) = new String(ApacheBase64.decodeBase64(encoded.getBytes))

    def encode(decoded: String) = new String(ApacheBase64.encodeBase64(decoded.getBytes))
  }

  def credentialsFromAuthHeader(authHeader: String): Option[Credentials] = {
    if (!authHeader.contains("Basic ")) {
      None
    } else {
      val baStr = authHeader.replaceFirst("Basic ", "")
      val decoded = Base64.decode(baStr)
      if (decoded.contains(":")) {
        val Array(user, password) = decoded.split(":")
        Some(Credentials(user, password))
      } else {
        None
      }
    }
  }

  def authHeaderFromCredentials(credentials: Credentials): String = {
    "Basic " + Base64.encode(s"${credentials.username}:${credentials.password}")
  }
}
