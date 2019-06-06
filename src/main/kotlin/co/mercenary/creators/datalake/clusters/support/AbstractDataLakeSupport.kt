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
import co.mercenary.creators.datalake.clusters.support.db.SQL
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.*
import org.springframework.jdbc.core.JdbcTemplate

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware {

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

    protected val todosweb = getWebClient("http://jsonplaceholder.typicode.com/todos")

    protected val postsweb = getWebClient("http://jsonplaceholder.typicode.com/posts")

    protected fun getWebClient(base: String) = WebClient.create(base)

    protected fun query(@SQL sql: String, key: String = "results") = json(key to jdbc.queryForList(sql))

    protected fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    protected fun clock(name: String = "real_time", block: () -> JSONObject) = NanoTicker().let { tick -> block().also { it[name] = tick(false) } }

    protected fun getEnvironmentProperty(name: String): String? = context.environment.getProperty(name)

    protected fun getEnvironmentPropertyIrElse(name: String, other: String): String = context.environment.getProperty(name, other)

    protected fun getEnvironmentPropertyOrElseCall(name: String, other: () -> String): String = getEnvironmentProperty(name) ?: other()

    data class PostData(val userId: Int, val id: Int, val title: String, val body: String)

    data class TodoData(val userId: Int, val id: Int, val title: String, val completed: Boolean)
}