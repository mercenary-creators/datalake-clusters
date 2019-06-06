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
import java.util.concurrent.atomic.*
import java.util.function.*

@RunWith(SpringRunner::class)
@SpringBootTest
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

    fun AtomicLong.shouldBe(value: Long, block: () -> Any?) = assertEquals(value, get(), block)

    fun AtomicLong.shouldBe(value: Int, block: () -> Any?) = assertEquals(value.toLong(), get(), block)

    fun AtomicLong.shouldBe(value: () -> Any?, block: () -> Any?) = assertEquals(value.invoke(), get(), block)

    fun AtomicLong.shouldBe(value: LongSupplier, block: () -> Any?) = assertEquals(value.asLong, get(), block)

    fun AtomicLong.shouldBe(value: IntSupplier, block: () -> Any?) = assertEquals(value.asInt.toLong(), get(), block)

    fun <T : Number> AtomicLong.shouldBe(value: Supplier<T>, block: () -> Any?) = assertEquals(value.get(), get(), block)

    fun AtomicInteger.shouldBe(value: Int, block: () -> Any?) = assertEquals(value, get(), block)

    fun AtomicInteger.shouldBe(value: Long, block: () -> Any?) = assertEquals(value.toInt(), get(), block)

    fun AtomicInteger.shouldBe(value: () -> Any?, block: () -> Any?) = assertEquals(value.invoke(), get(), block)

    fun AtomicInteger.shouldBe(value: IntSupplier, block: () -> Any?) = assertEquals(value.asInt, get(), block)

    fun AtomicInteger.shouldBe(value: LongSupplier, block: () -> Any?) = assertEquals(value.asLong.toInt(), get(), block)

    fun <T : Number> AtomicInteger.shouldBe(value: Supplier<T>, block: () -> Any?) = assertEquals(value.get(), get(), block)

    fun AtomicBoolean.shouldBe(value: Boolean, block: () -> Any?) = assertEquals(value, get(), block)

    fun AtomicBoolean.shouldBe(value: () -> Any?, block: () -> Any?) = assertEquals(value.invoke(), get(), block)

    fun AtomicBoolean.shouldBe(value: Supplier<Boolean>, block: () -> Any?) = assertEquals(value.get(), get(), block)

    fun AtomicBoolean.shouldBe(value: BooleanSupplier, block: () -> Any?) = assertEquals(value.asBoolean, get(), block)

    fun ByteArray?.shouldBe(value: ByteArray?, block: () -> Any?) = assertEquals(value, this, block)

    fun <T : Any?> List<T>.shouldNotBe(value: Iterable<*>?, block: () -> Any?) = assertNotEquals(value?.toList(), this, block)

    fun <T : Any> T?.shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this, block)

    fun <T : Any> (() -> T?).shouldNotBe(value: Any?, block: () -> Any?) = assertNotEquals(value, this.invoke(), block)

    fun <T : Any> (() -> T?).shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), this.invoke(), block)

    fun AtomicLong.shouldNotBe(value: Long, block: () -> Any?) = assertNotEquals(value, get(), block)

    fun AtomicLong.shouldNotBe(value: Int, block: () -> Any?) = assertNotEquals(value.toLong(), get(), block)

    fun AtomicLong.shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), get(), block)

    fun AtomicLong.shouldNotBe(value: LongSupplier, block: () -> Any?) = assertNotEquals(value.asLong, get(), block)

    fun AtomicLong.shouldNotBe(value: IntSupplier, block: () -> Any?) = assertNotEquals(value.asInt.toLong(), get(), block)

    fun <T : Number> AtomicLong.shouldNotBe(value: Supplier<T>, block: () -> Any?) = assertNotEquals(value.get(), get(), block)

    fun AtomicInteger.shouldNotBe(value: Int, block: () -> Any?) = assertNotEquals(value, get(), block)

    fun AtomicInteger.shouldNotBe(value: Long, block: () -> Any?) = assertNotEquals(value.toInt(), get(), block)

    fun AtomicInteger.shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), get(), block)

    fun AtomicInteger.shouldNotBe(value: IntSupplier, block: () -> Any?) = assertNotEquals(value.asInt, get(), block)

    fun AtomicInteger.shouldNotBe(value: LongSupplier, block: () -> Any?) = assertNotEquals(value.asLong.toInt(), get(), block)

    fun <T : Number> AtomicInteger.shouldNotBe(value: Supplier<T>, block: () -> Any?) = assertNotEquals(value.get(), get(), block)

    fun AtomicBoolean.shouldNotBe(value: Boolean, block: () -> Any?) = assertNotEquals(value, get(), block)

    fun AtomicBoolean.shouldNotBe(value: () -> Any?, block: () -> Any?) = assertNotEquals(value.invoke(), get(), block)

    fun AtomicBoolean.shouldNotBe(value: Supplier<Boolean>, block: () -> Any?) = assertNotEquals(value.get(), get(), block)

    fun AtomicBoolean.shouldNotBe(value: BooleanSupplier, block: () -> Any?) = assertNotEquals(value.asBoolean, get(), block)

    fun Double.shouldBeClose(value: Double, block: () -> Any?) = assertTrue((Math.abs(this - value) < 0.000001), block)

    fun Double.shouldBeClose(value: Double, delta: Double, block: () -> Any?) = assertTrue((Math.abs(this - value) < delta), block)
}
