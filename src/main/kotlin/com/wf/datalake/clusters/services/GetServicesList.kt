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

package com.wf.datalake.clusters.services

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import com.wf.datalake.clusters.services.support.DataLakeServiceSupport
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/services")
class GetServicesList : DataLakeServiceSupport() {

    @Autowired
    private lateinit var clusters: ClustersService

    @Autowired
    private lateinit var datalake: DataLakeService

    @GetMapping
    fun mappings() = getCachedMappings("services")

    @PostMapping("/echo")
    fun echo(@RequestBody body: JSONObject) = json("echo" to body, "uuid" to uuid())

    @GetMapping("/list")
    fun list() = timed { json("list" to JSONArray(mappings(), clusters.mappings(), datalake.mappings())) }
}