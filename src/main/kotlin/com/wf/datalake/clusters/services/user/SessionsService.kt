package com.wf.datalake.clusters.services.user

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.support.DataLakeServiceSupport
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpSession

@RestController
@RequestMapping("/user/sessions")
class SessionsService : DataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/user/sessions") }

    @GetMapping("/kill")
    fun kill(session: HttpSession) = clock {
        val time = if (session.isNew) 0 else session.creationTime
        session.invalidate()
        json("kill" to true, "time" to time, "uuid" to uuid())
    }

    @GetMapping("/data")
    fun data(session: HttpSession) = clock {
        val json = json()
        session.setAttribute("/user/datalake/${uuid()}-time", Date())
        session.attributeNames.toList().filter { it != "SPRING_SECURITY_CONTEXT" }.forEach {
            json[it] = session.getAttribute(it)
        }
        json
    }
}
