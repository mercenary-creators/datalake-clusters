package co.mercenary.creators.datalake.clusters.services.open

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/datalake")
class DataLakeService : AbstractDataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/open/datalake") }

    @GetMapping("/posts")
    fun posts() = postsweb.get().retrieve().bodyToFlux<PostData>()

    @GetMapping("/users")
    fun users() = clock { query(sql = "SELECT username, enabled FROM users") }
}