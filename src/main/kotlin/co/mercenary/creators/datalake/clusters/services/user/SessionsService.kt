package co.mercenary.creators.datalake.clusters.services.user

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.*

@RestController
@RequestMapping("/user/sessions")
class SessionsService : AbstractDataLakeServiceSupport() {
    @GetMapping
    fun root() = clock { getCachedMappings("/user/sessions") }

    @GetMapping("/kill")
    fun kill(request: HttpServletRequest, response: HttpServletResponse, session: HttpSession) = clock {
        getLogoutResults(request, response, session, "SESSION", "SESSIONID", "JSESSIONID")
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
