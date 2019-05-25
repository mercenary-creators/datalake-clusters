package co.mercenary.creators.datalake.clusters.services.open

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/clusters")
class ClustersService : AbstractDataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/open/clusters") }

    @GetMapping("/roles")
    fun roles() = clock { query(sql = "SELECT username, authority FROM authorities") }

    @PostMapping("/echo")
    fun echo(@RequestBody body: Any) = clock { json("body" to body, "stat" to stat) }
}