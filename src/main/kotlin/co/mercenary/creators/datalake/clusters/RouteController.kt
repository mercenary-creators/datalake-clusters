package co.mercenary.creators.datalake.clusters

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class RouteController {

    @GetMapping("/{path:[^.]*}")
    fun redirect() = "forward:/"
}
