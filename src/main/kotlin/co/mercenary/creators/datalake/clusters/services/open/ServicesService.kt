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

@RestController
@RequestMapping("/open/services")
class ServicesService : AbstractDataLakeSupport() {

    @GetMapping
    fun root() = base("/open/services", "/node" to GET, "/list" to GET)

    @GetMapping("/node")
    fun node() = query(sql = "SELECT * FROM nodes")

    @GetMapping("/list")
    fun list() = query(sql = "SELECT * FROM servers")
}