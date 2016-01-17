package com.tapatron.security

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class SecurityUtilsTest extends FlatSpec with Matchers {

  behavior of "Security test utilities"

  it should "decode the basic auth header" in {
    val header = "Basic c3RlaW46cGFzc3dvcmQ="

    val creds = SecurityUtils.credentialsFromAuthHeader(header)

    assert(creds.get.username === "stein")
    assert(creds.get.password === "password")
  }

  it should "handle junk credentials" in {
    val headerJunk = "Basic 8wgq8wgweg0qweg"

    val creds = SecurityUtils.credentialsFromAuthHeader(headerJunk)

    assert(creds.isDefined === false)
  }

  it should "sign the session ID with the provided secret" in {
    val key = "6e111ba6-ce0e-4656-b8d9-958dc546c2a2"

    val signedSessionID = SecurityUtils.generateHmac(key)

    signedSessionID shouldBe "aaf689662fd346e4545daa61f197610982eb1900"
  }

}
