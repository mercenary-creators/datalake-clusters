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

package co.mercenary.creators.datalake.clusters.test.util

import co.mercenary.creators.kotlin.util.test.KotlinTestBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@TestPropertySource(properties = ["data.lake.redis.bean.name=datalake:test"])
abstract class AbstractApplicationTests(private val cancel: Boolean = true) : KotlinTestBase() {

    @Autowired
    private lateinit var password: PasswordEncoder

    protected val encoder: PasswordEncoder
        get() = password
}
