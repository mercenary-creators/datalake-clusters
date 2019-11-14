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

package co.mercenary.creators.datalake.clusters.services.open

import co.mercenary.creators.datalake.clusters.support.*
import co.mercenary.creators.kotlin.util.*
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/datalake")
class DataLakeService : DataLakeSupport() {

    @GetMapping("/posts")
    fun posts() = getWebFlux<PostData>(PostData.path()).limit(15)

    @GetMapping("/todos")
    fun todos() = getWebFlux<TodoData>(TodoData.path()).limit(15)

    @GetMapping("/node")
    fun node() = queryList("SELECT * FROM nodes")

    @GetMapping("/list")
    fun list() = queryList("SELECT * FROM servers")

    @GetMapping("/roles")
    fun roles() = queryList("SELECT username, authority FROM authorities")

    @GetMapping("/users")
    fun users() = queryListOf<UserPartialData>("SELECT username, enabled FROM users")
}