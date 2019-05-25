package co.mercenary.creators.datalake.clusters.test.main

import co.mercenary.creators.datalake.clusters.DataLakeTest
import org.junit.Test

class PasswordTest : DataLakeTest() {
    @Test
    fun test() {
        lines { line ->
            info { line }
            val pass = timed {
                encoder.encode(line)
            }
            info { pass }
        }
    }
}