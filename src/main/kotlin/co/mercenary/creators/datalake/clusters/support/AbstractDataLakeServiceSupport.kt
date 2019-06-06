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

package co.mercenary.creators.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.*
import java.util.*

abstract class AbstractDataLakeServiceSupport : AbstractDataLakeSupport() {

    @Autowired
    private lateinit var handlers: RequestMappingHandlerMapping

    private val prefix: String by lazy {
        getRequestMappingPath(this.javaClass)
    }

    protected fun getLogoutResults(request: HttpServletRequest, response: HttpServletResponse, session: HttpSession, vararg args: String): JSONObject {
        val list = ArrayList<String>()
        if (args.isNotEmpty()) {
            val path = request.contextPath.trim().removeSuffix(ROOT).trim() + ROOT
            val look = (request.cookies?.toList() ?: emptyList()).filter { it != null }.map { it.name to it }.toMap()
            args.forEach { name ->
                val find = look[name]
                if (find != null) {
                    list += name
                    Cookie(name, null).also { dead ->
                        dead.maxAge = 0
                        dead.path = path
                        response.addCookie(dead)
                    }
                }
            }
        }
        return json("id" to session.id, "time" to Date(session.creationTime), "made" to session.isNew, "last" to Date(session.lastAccessedTime), "list" to list).also { session.invalidate() }
    }

    protected fun getCachedMappings(name: String) = cached.computeIfAbsent(name) { json(name to json("bindings" to getRequestMappingList(handlers, prefix))) }

    companion object {

        private const val ROOT = "/"

        private val cached = ConcurrentHashMap<String, JSONObject>()

        private fun getRequestMappingPath(type: Class<*>): String = getRequestMappingBase(getRequestMappingType(type)) ?: ROOT

        private fun getRequestMappingType(type: Class<*>?): RequestMapping? = if (type != null) type.getAnnotation(RequestMapping::class.java) ?: getRequestMappingType(type.superclass) else null

        private fun getRequestMappingBase(request: RequestMapping?): String? = if (request == null) null
        else {
            var base: String? = null
            request.path.also {
                if (it.isNullOrEmpty().not()) {
                    base = toTrimOrNull(it[0])
                }
            }
            if (base == null) {
                request.value.also {
                    if (it.isNullOrEmpty().not()) {
                        base = toTrimOrNull(it[0])
                    }
                }
            }
            base
        }

        private fun getRequestMappingList(handler: RequestMappingHandlerMapping, prefix: String) =
                handler.handlerMethods.keys.map {
                    json("method" to it.methodsCondition.methods.toList().elementAtOrElse(0) { RequestMethod.GET }, "path" to it.patternsCondition.patterns.toList().elementAtOrElse(0) { ROOT })
                }.filter { it.asString("path").orEmpty().startsWith(prefix) }

    }
}
