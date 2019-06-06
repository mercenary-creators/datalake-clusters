/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
