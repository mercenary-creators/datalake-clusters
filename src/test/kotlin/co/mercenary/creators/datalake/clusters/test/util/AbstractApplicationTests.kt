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

import co.mercenary.creators.datalake.clusters.support.DataLakeSupport
import co.mercenary.creators.kotlin.util.toSafeString
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner

@SpringBootTest
@RunWith(SpringRunner::class)
@TestPropertySource(properties = ["data.lake.redis.bean.name=datalake:test"])
abstract class AbstractApplicationTests(private val cancel: Boolean = true) : DataLakeSupport() {

    @Autowired
    private lateinit var password: PasswordEncoder

    protected val encoder: PasswordEncoder
        get() = password

    fun assertEach(vararg list: Executable) {
        if (list.isNotEmpty()) {
            Assertions.assertAll(*list)
        }
    }

    fun assertEach(list: List<Executable>) {
        if (list.isNotEmpty()) {
            Assertions.assertAll(list)
        }
    }

    fun assumeThat(condition: Boolean, executable: Executable) {
        Assumptions.assumingThat(condition, executable)
    }

    fun assertTrue(condition: Boolean, block: () -> Any?) {
        Assertions.assertTrue(condition, toSafeString(block))
    }

    fun assertFalse(condition: Boolean, block: () -> Any?) {
        Assertions.assertFalse(condition, toSafeString(block))
    }

    fun assertEquals(expected: Any?, actual: Any?, block: () -> Any?) {
        Assertions.assertEquals(expected, actual, toSafeString(block))
    }

    fun assertEquals(expected: ByteArray?, actual: ByteArray?, block: () -> Any?) {
        Assertions.assertArrayEquals(expected, actual, toSafeString(block))
    }

    fun assertNotEquals(expected: Any?, actual: Any?, block: () -> Any?) {
        Assertions.assertNotEquals(expected, actual, toSafeString(block))
    }

    fun <T : Any?> List<T>.shouldBe(value: Iterable<*>?, block: () -> Any?) = assertEquals(value?.toList(), this, block)

    fun <T : Any> T?.shouldBe(value: Any?, block: () -> Any?) = assertEquals(value, this, block)

    fun <T : Any> (() -> T?).shouldBe(value: Any?, block: () -> Any?) = assertEquals(value, this.invoke(), block)

    fun <T : Any> (() -> T?).shouldBe(value: () -> Any?, block: () -> Any?) = assertEquals(value.invoke(), this.invoke(), block)

    fun ByteArray?.shouldBe(value: ByteArray?, block: () -> Any?) = assertEquals(value, this, block)

    fun <T : Any?> List<T>.shouldNotBe(value: Iterable<*>?, block: () -> Any?) = assertNotEquals(value?.toList(), this, block)

    fun <T : Any> T?.shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this, block)

    fun <T : Any> (() -> T?).shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this.invoke(), block)

    fun <T : Any> (() -> T?).shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), this.invoke(), block)
}
