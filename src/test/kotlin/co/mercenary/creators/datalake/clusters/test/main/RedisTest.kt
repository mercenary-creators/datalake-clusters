package co.mercenary.creators.datalake.clusters.test.main

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.DataLakeTest
import org.junit.Test

class RedisTest : DataLakeTest() {
    @Test
    fun test() {
        val uuid = uuid()
        var data = redis.execute {
            json("test" to it.bind().hash<String, Any>().increment(uuid))
        }
        info { data }
        data = redis.execute {
            json("test" to it.bind().hash<String, Any>().increment(uuid))
        }
        info { data }
        data = redis.execute {
            json("test" to it.bind().hash<String, Any>().increment(uuid))
        }
        info { data }
        data = redis.execute {
            json("test" to it.bind().hash<String, Any>().decrement(uuid))
        }
        info { data }
        info { redis.name() }
        redis.stop()
    }
}