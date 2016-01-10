package com.tapatron.security

import org.scalatest.FlatSpec

class SecurityUtilsTest extends FlatSpec {

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



}
