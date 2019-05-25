package co.mercenary.creators.datalake.clusters.services.user

import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user/datalake")
class UserLakeService : AbstractDataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/user/datalake") }

    @GetMapping("/node")
    fun node() = clock { query(sql = "SELECT * FROM nodes", key = "nodes") }

    @GetMapping("/list")
    fun data() = clock { query(sql = "SELECT * FROM servers", key = "servers") }
}
