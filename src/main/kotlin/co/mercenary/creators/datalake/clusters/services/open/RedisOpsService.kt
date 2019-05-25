package co.mercenary.creators.datalake.clusters.services.open

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/redisops")
class RedisOpsService : AbstractDataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/open/redisops") }

    @GetMapping("/data")
    fun maps() = clock {
        redis.execute {
            val bind = it.bind().hash<String, Any>()
            json("data" to bind.maps())
        }
    }

    @PostMapping("/save")
    fun save(@RequestBody body: Any) = clock {
        redis.execute {
            val uuid = uuid()
            val bind = it.bind().hash<String, Any>()
            val next = bind.increment("save:next")
            bind[uuid] = json("body" to body, "next" to next, "stat" to stat.toJSONObject())
            bind.save()
            json("data" to bind.maps())
        }
    }

    @GetMapping("/keys")
    fun keys() = clock {
        redis.execute {
            val bind = it.bind().hash<String, Any>()
            json("keys" to bind.keys())
        }
    }

    @GetMapping("/kill")
    fun kill() = clock {
        redis.execute {
            val bind = it.bind().hash<String, Any>()
            val many = bind.delete(bind.keys())
            json("many" to many, "keys" to bind.keys())
        }
    }
}