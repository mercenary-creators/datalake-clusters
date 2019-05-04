package com.wf.datalake.clusters.test.main

import com.wf.datalake.DataLakeTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder

class MainTest : DataLakeTest() {

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Test
    fun test() {
        val pass = timed {
            encoder.encode("XXXXXXX")
        }
        info { pass }
    }
}