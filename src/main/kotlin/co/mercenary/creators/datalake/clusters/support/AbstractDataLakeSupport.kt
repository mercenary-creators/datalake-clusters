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

import co.mercenary.creators.kotlin.json
import co.mercenary.creators.kotlin.json.JSONAware
import co.mercenary.creators.kotlin.util.timed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.*
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.reactive.function.client.*

abstract class AbstractDataLakeSupport : mu.KLogging(), ApplicationContextAware {

    @Autowired
    private lateinit var template: JdbcTemplate

    private lateinit var application: ApplicationContext

    override fun setApplicationContext(context: ApplicationContext) {
        application = context
    }

    protected val jdbc: JdbcTemplate
        get() = template

    protected val context: ApplicationContext
        get() = application

    protected val todosweb = WebClient.create("http://jsonplaceholder.typicode.com/todos")

    protected val postsweb = WebClient.create("http://jsonplaceholder.typicode.com/posts")

    protected inline fun <reified T : Any> getFlux(web: WebClient) = web.get().retrieve().bodyToFlux<T>()

    protected fun query(sql: String, vararg args: Any?) = json("results" to jdbc.queryForList(sql, *args))

    protected fun base(base: String, vararg args: Pair<String, RequestMethod>) = json("base" to base, "mappings" to (listOf("/" to GET) + listOf(*args)))

    protected fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    fun info(block: () -> Any?) {
        logger.info(block)
    }

    fun warn(block: () -> Any?) {
        logger.warn(block)
    }

    fun debug(block: () -> Any?) {
        logger.debug(block)
    }

    fun debug(cause: Throwable, block: () -> Any?) {
        logger.debug(cause, block)
    }

    fun error(block: () -> Any?) {
        logger.error(block)
    }

    fun error(cause: Throwable, block: () -> Any?) {
        logger.error(cause, block)
    }

    data class PostData(val userId: Int, val id: Int, val title: String, val body: String) : JSONAware {
        override fun toString() = toJSONString()
    }

    data class TodoData(val userId: Int, val id: Int, val title: String, val completed: Boolean) : JSONAware {
        override fun toString() = toJSONString()
    }

    companion object {
        val GET = RequestMethod.GET
    }
}