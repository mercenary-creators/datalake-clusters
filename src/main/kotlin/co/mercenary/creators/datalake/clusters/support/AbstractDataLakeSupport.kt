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

import co.mercenary.creators.kotlin.boot.AbstractApplicationSupport
import co.mercenary.creators.kotlin.json
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.reactive.function.client.*

abstract class AbstractDataLakeSupport : AbstractApplicationSupport() {

    @Autowired
    private lateinit var template: JdbcTemplate

    protected val jdbc: JdbcTemplate
        get() = template

    protected val todosweb = WebClient.create("http://jsonplaceholder.typicode.com/todos")

    protected val postsweb = WebClient.create("http://jsonplaceholder.typicode.com/posts")

    protected inline fun <reified T : Any> getFlux(web: WebClient) = web.get().retrieve().bodyToFlux<T>()

    protected fun query(sql: String, vararg args: Any?) = json("results" to jdbc.queryForList(sql, *args))

    data class PostData(val userId: Int, val id: Int, val title: String, val body: String)

    data class TodoData(val userId: Int, val id: Int, val title: String, val completed: Boolean)
}