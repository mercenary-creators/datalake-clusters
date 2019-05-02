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

package com.wf.datalake.clusters.services.support

import co.mercenary.creators.core.kotlin.*
import com.wf.datalake.clusters.support.AbstractDataLakeSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.util.concurrent.ConcurrentHashMap
import org.springframework.web.servlet.mvc.method.RequestMappingInfo

abstract class DataLakeServiceSupport : AbstractDataLakeSupport() {

    @Autowired
    private lateinit var handler: RequestMappingHandlerMapping

    private val prefix: String? by lazy {
        getRequestMappingPath(this.javaClass)
    }

    private val cached: ConcurrentHashMap<String, JSONObject> by lazy {
        ConcurrentHashMap<String, JSONObject>(5)
    }

    protected fun clock(block: () -> JSONObject): JSONObject {
        val tick = NanoTicker()
        return block().also { it["data_lake_time"] = tick() }
    }

    protected fun getServiceMappingPrefix() = prefix

    protected fun getRequestMappingHandlerMapping() = handler

    protected fun getCachedMappings(name: String) = cached.computeIfAbsent(name) {
        json(name to json("bindings" to getMappingsList()))
    }

    protected fun getMappingsList(): List<JSONObject> {
        return getMappingsList(getServiceMappingPrefix())
    }

    protected fun getMappingsList(prefix: String?): List<JSONObject> {
        return getRequestMappingList(getRequestMappingHandlerMapping(), "method", "path", prefix)
    }

    protected companion object {

        fun getRequestMappingType(type: Class<*>?): RequestMapping? = if (type != null) {
            type.getAnnotation(RequestMapping::class.java) ?: getRequestMappingType(type.superclass)
        }
        else null

        fun getRequestMappingPath(type: Class<*>): String? = getRequestMappingBase(getRequestMappingType(type))

        fun getRequestMappingBase(request: RequestMapping?): String? = if (request == null) null
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

        fun getRequestMappingList(handler: RequestMappingHandlerMapping, meth: String, link: String, prefix: String?): List<JSONObject> {
            val filter: (JSONObject) -> Boolean = {
                if (prefix == null) false else it.asString(link).orEmpty().startsWith(prefix)
            }
            return handler.handlerMethods.keys.map { getRequestMappingInfo(it, meth, link) }.filter(filter)
        }

        fun getRequestMappingInfo(info: RequestMappingInfo, meth: String, link: String): JSONObject {
            return json(meth to info.methodsCondition.methods.toList().elementAtOrElse(0) { RequestMethod.GET }, link to info.patternsCondition.patterns.toTypedArray()[0])
        }
    }
}
