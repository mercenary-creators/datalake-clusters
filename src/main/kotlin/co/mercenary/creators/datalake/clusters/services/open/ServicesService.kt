package co.mercenary.creators.datalake.clusters.services.open

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/services")
class ServicesService : AbstractDataLakeServiceSupport() {
    @Autowired
    private lateinit var clusters: ClustersService

    @Autowired
    private lateinit var datalake: DataLakeService

    @GetMapping
    fun root() = clock { getCachedMappings("/open/services") }

    @GetMapping("/list")
    fun list() = clock { json("list" to listOf(root(), clusters.root(), datalake.root())) }

    @GetMapping("/todos")
    fun todos() = todosweb.get().retrieve().bodyToFlux<TodoData>()
}