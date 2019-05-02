package com.wf.datalake.clusters.services

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.services.support.DataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/clusters")
class ClustersService : DataLakeServiceSupport() {

    @GetMapping
    fun mappings() = clock { getCachedMappings("clusters") }

    @GetMapping("/name")
    fun name() = clock { json("name" to "Dean S. Jones") }

    @GetMapping("/data")
    fun data() = clock { json("data" to sequenceOf(0..64 step 2)) }
}