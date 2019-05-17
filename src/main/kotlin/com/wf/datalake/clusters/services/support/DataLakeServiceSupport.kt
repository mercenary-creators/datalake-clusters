package com.wf.datalake.clusters.services.support

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.support.AbstractDataLakeSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.util.concurrent.ConcurrentHashMap

abstract class DataLakeServiceSupport : AbstractDataLakeSupport() {

    @Autowired
    private lateinit var template: JdbcTemplate

    @Autowired
    private lateinit var handlers: RequestMappingHandlerMapping

    val jdbc: JdbcTemplate
        get() = template

    private val prefix: String by lazy {
        getRequestMappingPath(this.javaClass)
    }

    protected val todosweb: WebClient by lazy {
        getWebClient("http://jsonplaceholder.typicode.com/todos")
    }

    protected val postsweb: WebClient by lazy {
        getWebClient("http://jsonplaceholder.typicode.com/posts")
    }

    protected fun query(sql: String, key: String = "results") = json(key to jdbc.queryForList(sql))

    protected fun getWebClient(base: String) = WebClient.create(base)

    protected fun clock(name: String = "real_time", block: () -> JSONObject) = NanoTicker().let { tick -> block().also { it[name] = tick(false) } }

    protected fun getCachedMappings(name: String) = cached.computeIfAbsent(name) { json(name to json("bindings" to getRequestMappingList(handlers, "method", "path", prefix))) }

    data class PostData(val userId: Int, val id: Int, val title: String, val body: String)

    data class TodoData(val userId: Int, val id: Int, val title: String, val completed: Boolean)

    internal companion object {

        internal val cached: ConcurrentHashMap<String, JSONObject> by lazy {
            ConcurrentHashMap<String, JSONObject>(5)
        }

        internal fun getRequestMappingPath(type: Class<*>): String = getRequestMappingBase(getRequestMappingType(type)) ?: "/"

        internal fun getRequestMappingType(type: Class<*>?): RequestMapping? = if (type != null) type.getAnnotation(RequestMapping::class.java) ?: getRequestMappingType(type.superclass) else null

        internal fun getRequestMappingBase(request: RequestMapping?): String? = if (request == null) null
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

        internal fun getRequestMappingList(handler: RequestMappingHandlerMapping, meth: String, link: String, prefix: String): List<JSONObject> {
            return handler.handlerMethods.keys.map { getRequestMappingInfo(it, meth, link) }.filter { it.asString(link).orEmpty().startsWith(prefix) }
        }

        internal fun getRequestMappingInfo(info: RequestMappingInfo, meth: String, link: String): JSONObject {
            return json(meth to info.methodsCondition.methods.toList().elementAtOrElse(0) { RequestMethod.GET }, link to info.patternsCondition.patterns.toList().elementAtOrElse(0) { "/" })
        }
    }
}
