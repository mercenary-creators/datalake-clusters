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

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeServiceSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/open/services")
class ServicesService : AbstractDataLakeServiceSupport() {

    @Autowired
    private lateinit var clusters: ClustersService

    @Autowired
    private lateinit var datalake: DataLakeService

    @GetMapping
    fun root() = clock { getCachedMappings("/open/services") }

    @GetMapping("/list")
    fun list() = clock { json("list" to listOf(root(), clusters.root(), datalake.root())) }

    @GetMapping("/todos")
    fun todos() = todosweb.get().retrieve().bodyToFlux<TodoData>()
}