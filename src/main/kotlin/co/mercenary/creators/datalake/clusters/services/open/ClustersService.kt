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

import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeSupport
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.client.bodyToFlux

@RestController
@RequestMapping("/open/clusters")
class ClustersService : AbstractDataLakeSupport() {

    @GetMapping
    fun root() = base("/open/clusters", "/roles" to GET, "/todos" to GET)

    @GetMapping("/roles")
    fun roles() = query(sql = "SELECT username, authority FROM authorities")

    @GetMapping("/todos")
    fun todos() = todosweb.get().retrieve().bodyToFlux<TodoData>()
}