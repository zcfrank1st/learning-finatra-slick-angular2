package com.tapatron.security

import com.tapatron.controller.Credentials
import com.tapatron.error.BadCredentialsError
import org.apache.commons.codec.binary.{Base64 => ApacheBase64}

object SecurityUtils {

  object Base64 {
    def decode(encoded: String) = new String(ApacheBase64.decodeBase64(encoded.getBytes))

    def encode(decoded: String) = new String(ApacheBase64.encodeBase64(decoded.getBytes))
  }

  def credentialsFromAuthHeader(authHeader: String): Either[BadCredentialsError, Credentials] = {
    if (!authHeader.contains("Basic ")) {
      Left(BadCredentialsError("Invalid credentials"))
    } else {
      val baStr = authHeader.replaceFirst("Basic ", "")
      val decoded = Base64.decode(baStr)
      if (decoded.contains(":")) {
        val Array(user, password) = new String(decoded).split(":")
        Right(Credentials(user, password))
      } else {
        Left(BadCredentialsError("Invalid credentials"))
      }
    }
  }
}
