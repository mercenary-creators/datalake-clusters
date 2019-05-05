package com.wf.datalake.clusters.services

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.services.support.DataLakeQueryServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/datalake")
class DataLakeService : DataLakeQueryServiceSupport() {

    @GetMapping
    fun root() = clock { getCachedMappings() }

    @GetMapping("/posts")
    fun posts() = postsweb.get().retrieve().bodyToFlux<PostData>()

    @GetMapping("/users")
    fun users() = clock { query(sql = "SELECT username, enabled FROM users") }
}