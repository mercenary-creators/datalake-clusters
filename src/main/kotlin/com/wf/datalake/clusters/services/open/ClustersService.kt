package com.wf.datalake.clusters.services.open

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.services.support.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/clusters")
class ClustersService : DataLakeServiceSupport() {

    @GetMapping
    fun root() = clock { getCachedMappings("clusters") }

    @GetMapping("/roles")
    fun roles() = clock { query(sql = "SELECT username, authority FROM authorities") }

    @PostMapping("/echo")
    fun echo(@RequestBody body: Any) = clock { json("body" to body, "uuid" to uuid()) }
}