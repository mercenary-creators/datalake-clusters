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
    fun root() = clock { getCachedMappings("services") }

    @GetMapping("/prefix")
    fun prefix() = clock { json("prefix" to prefix) }

    @PostMapping("/echo")
    fun echo(@RequestBody body: JSONObject) = clock { json("echo" to body, "uuid" to uuid()) }

    @GetMapping("/list")
    fun list() = clock { json("list" to JSONArray(root(), clusters.root(), datalake.root())) }

    @GetMapping("/todos")
    fun todos() = todosweb.get().retrieve().bodyToFlux<TodoData>()

    @GetMapping("/todo/{id}")
    fun todo(@PathVariable(required = false) id: Int = 1) = todosweb.get().uri("/{id}", id).retrieve().bodyToMono<TodoData>()

    @GetMapping("/posts")
    fun posts() = postsweb.get().retrieve().bodyToFlux<PostData>()

    @GetMapping("/post/{id}")
    fun post(@PathVariable(required = false) id: Int = 1) = postsweb.get().uri("/{id}", id).retrieve().bodyToMono<PostData>()
}