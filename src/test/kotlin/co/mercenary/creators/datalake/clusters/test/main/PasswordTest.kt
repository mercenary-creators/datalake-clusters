/*
 * Copyright (c) 2020, Mercenary Creators Company. All rights reserved.
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

package co.mercenary.creators.datalake.clusters.test.main

import co.mercenary.creators.datalake.clusters.support.*
import co.mercenary.creators.kotlin.boot.UserPartialData
import co.mercenary.creators.kotlin.json.*
import co.mercenary.creators.kotlin.util.*
import org.junit.Test

class PasswordTest : DataLakeTest() {
    @Test
    fun test() {
        warn { dash() }
        info { here() }
        val data = json("author" to author, "age" to 56.75.years + 1.minute)
        data.size shouldBe 2
        call(data)
        info { data }
        info { data.size }
        data.size shouldBe 3
        val dean = json("username" to "dean", "enabled" to 1).toDataType<UserPartialData>()
        info { dean }
        dean.enabled shouldBe true
        val john = json("username" to "john", "enabled" to "false").toDataType<UserPartialData>()
        info { john }
        john.enabled shouldBe false
        val root = mapOf("username" to "root", "enabled" to "true").toDataType<UserPartialData>()
        info { root }
        root.enabled shouldNotBe false
    }

    fun call(data: json) {
        info { data }
        info { data.size }
        data.size shouldBe 2
        warn { dash() }
        info { here() }
        data["date"] = dateOf()
    }
}
