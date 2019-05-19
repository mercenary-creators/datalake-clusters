package com.wf.datalake.clusters.services.user

import com.wf.datalake.clusters.support.DataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/datalake")
class UserLakeService : DataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/user/datalake") }

    @GetMapping("/node")
    fun node() = clock { query(sql = "SELECT * FROM nodes", key = "nodes") }

    @GetMapping("/list")
    fun data() = clock { query(sql = "SELECT * FROM servers", key = "servers") }
}
