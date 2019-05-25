package co.mercenary.creators.datalake.clusters.support.redis

interface LakeBindHash<H, K, V> : LakeBindKeys<H> {
    fun size(): Long
    fun keys(): List<K>
    fun list(): List<V>
    fun maps(): Map<K, V>
    fun exists(name: K): Boolean
    fun delete(keys: List<K>): Long
    fun delete(vararg keys: K): Long
    fun append(data: Map<K, V>)
    fun append(vararg data: Pair<K, V>)
    fun increment(name: K, delta: Double): Double
    fun increment(name: K, delta: Long = 1L): Long
    fun decrement(name: K, delta: Double): Double
    fun decrement(name: K, delta: Long = 1L): Long
    operator fun get(name: K): V?
    operator fun set(name: K, data: V?)
}