package com.wf.datalake.clusters.services

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.services.support.DataLakeQueryServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/datalake")
class DataLakeService : DataLakeQueryServiceSupport() {

    @GetMapping
    fun root() = clock { getCachedMappings("datalake") }

    @GetMapping("/name")
    fun name() = clock { json("name" to "Dean S. Jones") }

    @GetMapping("/users")
    fun users() = clock { query(sql = "select * from users") }
}