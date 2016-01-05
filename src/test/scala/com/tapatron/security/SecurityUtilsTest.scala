package com.tapatron.security

import org.scalatest.{FlatSpec, GivenWhenThen}

class SecurityUtilsTest extends FlatSpec with GivenWhenThen {

  behavior of "Security test utilities"

  it should "decode the basic auth header" in {
    val header = "Basic c3RlaW46cGFzc3dvcmQ="

    val creds = SecurityUtils.credentialsFromAuthHeader(header)

    assert(creds.right.get.username === "stein")
    assert(creds.right.get.password === "password")
  }

  it should "handle junk credentials" in {
    val headerJunk = "Basic 8wgq8wgweg0qweg"

    val creds = SecurityUtils.credentialsFromAuthHeader(headerJunk)

    assert(creds.isLeft === true)
  }



}
