package com.airline.android

import org.junit.Assert
import org.junit.Test

class UtilsTest {
    @Test
    fun testSha256() {
        val initial = "test"
        Assert.assertEquals("9F86D081884C7D659A2FEAA0C55AD015A3BF4F1B2B0B822CD15D6C15B0F00A08".toLowerCase(), initial.hash())
    }
}