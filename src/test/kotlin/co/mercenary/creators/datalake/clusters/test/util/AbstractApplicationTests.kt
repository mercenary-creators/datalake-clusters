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

package co.mercenary.creators.datalake.clusters.test.util

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.AbstractDataLakeSupport
import org.junit.Before
import org.junit.jupiter.api.*
import org.junit.jupiter.api.function.Executable
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import java.io.*
import java.nio.file.Path

@SpringBootTest
@RunWith(SpringRunner::class)
@TestPropertySource(properties = ["data.lake.redis.bean.name=datalake:test"])
abstract class AbstractApplicationTests(private val cancel: Boolean = true) : AbstractDataLakeSupport() {

    @Autowired
    private lateinit var pencoders: PasswordEncoder

    @Autowired
    private lateinit var scheduled: ScheduledAnnotationBeanPostProcessor

    protected val encoder: PasswordEncoder
        get() = pencoders

    @Before
    fun startup__() {
        if (cancel) {
            scheduled.setApplicationContext(context)
            scheduled.scheduledTasks.forEach {
                it.cancel()
            }
        }
    }

    fun getEnvironmentProperty(name: String): String? = context.environment.getProperty(name)

    fun getEnvironmentPropertyIrElse(name: String, other: String): String = context.environment.getProperty(name, other)

    fun getEnvironmentPropertyOrElseCall(name: String, other: () -> String): String = getEnvironmentProperty(name) ?: other()

    fun lines(data: URL, action: (String) -> Unit) {
        lines(data.toInputStream(), action)
    }

    fun lines(data: Path, action: (String) -> Unit) {
        lines(data.toFile(), action)
    }

    fun lines(data: File, action: (String) -> Unit) {
        data.forEachLine(Charsets.UTF_8, action)
    }

    fun lines(data: Resource, action: (String) -> Unit) {
        data.toInputStream().toReader(Charsets.UTF_8).forEachLine(action)
    }

    fun lines(data: InputStream = System.`in`, action: (String) -> Unit) {
        data.toReader(Charsets.UTF_8).forEachLine(action)
    }

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
