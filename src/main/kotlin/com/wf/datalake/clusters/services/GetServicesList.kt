package com.wf.datalake.clusters.services

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import com.wf.datalake.clusters.services.support.DataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/services")
class GetServicesList : DataLakeServiceSupport() {

    @Autowired
    private lateinit var clusters: ClustersService

    @Autowired
    private lateinit var datalake: DataLakeService

    @GetMapping
    fun root() = clock { getCachedMappings() }

    @GetMapping("/list")
    fun list() = clock { json("list" to JSONArray(root(), clusters.root(), datalake.root())) }

    @GetMapping("/todos")
    fun todos() = todosweb.get().retrieve().bodyToFlux<TodoData>()
}