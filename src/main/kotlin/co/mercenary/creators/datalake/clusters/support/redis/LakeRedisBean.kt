package co.mercenary.creators.datalake.clusters.support.redis

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.*
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.*
import org.springframework.data.redis.serializer.*
import java.util.*
import java.util.concurrent.TimeUnit

class LakeRedisBean(conf: RedisConnectionFactory, private val name: String) : AbstractLogging(), LakeRedis, InitializingBean, BeanClassLoaderAware {

    private lateinit var classloader: ClassLoader

    private val template = RedisTemplate<String, Any>()

    private val redisops: LakeRedisOperations by lazy {
        object : LakeRedisOperations, LakeRedis by this, LakeRedisActions by LakeRedisActionsBean() {}
    }

    init {
        template.setConnectionFactory(conf)
    }

    override fun afterPropertiesSet() {
        template.setBeanClassLoader(classloader)
        template.keySerializer = KEYS_SERIALIZER
        template.valueSerializer = JSON_SERIALIZER
        template.hashKeySerializer = KEYS_SERIALIZER
        template.hashValueSerializer = JSON_SERIALIZER
        template.afterPropertiesSet()
    }

    override fun setBeanClassLoader(classloader: ClassLoader) {
        this.classloader = classloader
    }

    override fun name() = name

    override fun stop() = template.delete(name())

    override fun keys() = template.keys(name()).toList()

    override fun exists(name: String) = template.hasKey(name)

    override fun counts(keys: List<String>): Long {
        return when (keys.isEmpty()) {
            true -> 0
            else -> template.countExistingKeys(keys.distinct())
        }
    }

    override fun counts(vararg keys: String) = counts(keys.toList())

    override fun counts(keys: Sequence<String>) = counts(keys.toList())

    override fun counts(keys: Iterable<String>) = counts(keys.toList())

    override fun execute(block: (LakeRedisOperations) -> JSONObject): JSONObject {
        return block(redisops)
    }

    companion object {
        private val KEYS_SERIALIZER = StringRedisSerializer()
        private val JSON_SERIALIZER = GenericJackson2JsonRedisSerializer()
    }

    inner class LakeRedisActionsBean : LakeRedisActions {
        override fun bind() = LakeRedisBindActionsBean()

        inner class LakeRedisBindActionsBean : LakeRedisBindActions {
            override fun <K, V> hash(): LakeBindHash<String, K, V> {
                return LakeBindHashBean(template.boundHashOps<K, V>(name()))
            }
        }

        abstract inner class AbstractLakeBindKeys<K>(private val bind: BoundKeyOperations<K>) : LakeBindKeys<K> {
            override fun name(): K {
                return bind.key
            }

            override fun save(): Boolean {
                return bind.persist() ?: false
            }

            override fun rename(name: K) {
                bind.rename(name)
            }

            override fun getExpireTime(): Long {
                return bind.expire ?: 0
            }

            override fun setExpireTime(time: Long, unit: TimeUnit) {
                bind.expire(time, unit)
            }

            override fun getExpireDate(): Date {
                return Date(bind.expire ?: 0)
            }

            override fun setExpireDate(date: Date) {
                bind.expireAt(date)
            }

            override fun getRedisOperations(): LakeRedisOperations {
                return redisops
            }
        }

        inner class LakeBindHashBean<K, V>(private val bind: BoundHashOperations<String, K, V>) : LakeBindHash<String, K, V>, AbstractLakeBindKeys<String>(bind) {
            override fun append(data: Map<K, V>) {
                if (data.isNotEmpty()) {
                    bind.putAll(data)
                }
            }

            override fun append(vararg data: Pair<K, V>) {
                if (data.isNotEmpty()) {
                    bind.putAll(mapOf(*data))
                }
            }

            override fun size(): Long {
                return bind.size() ?: 0
            }

            override fun keys(): List<K> {
                return bind.keys().orEmpty().toList()
            }

            override fun list(): List<V> {
                return bind.values().orEmpty()
            }

            override fun maps(): Map<K, V> {
                return bind.entries().orEmpty()
            }

            override fun exists(name: K): Boolean {
                return bind.hasKey(name as Any) ?: false
            }

            override fun delete(keys: List<K>): Long {
                return if (keys.isEmpty()) 0 else bind.delete(*(keys as List<*>).toTypedArray()) ?: 0
            }

            override fun delete(vararg keys: K): Long {
                return delete(keys.toList())
            }

            override fun increment(name: K, delta: Double): Double {
                return bind.increment(name, Math.abs(delta)) ?: 0.0
            }

            override fun increment(name: K, delta: Long): Long {
                return bind.increment(name, Math.abs(delta)) ?: 0
            }

            override fun decrement(name: K, delta: Double): Double {
                return bind.increment(name, -Math.abs(delta)) ?: 0.0
            }

            override fun decrement(name: K, delta: Long): Long {
                return bind.increment(name, -Math.abs(delta)) ?: 0
            }

            override operator fun get(name: K): V? {
                return bind.get(name as Any)
            }

            override operator fun set(name: K, data: V?) {
                if (data != null) bind.put(name, data) else bind.delete(name)
            }
        }
    }
}