package co.mercenary.creators.datalake.clusters.support.redis

interface LakeBindList<H, K, V> : LakeBindKeys<H> {
    fun size(): Long
    fun trim(beg: Long, end: Long)
    fun list(beg: Long, end: Long): List<V>
    operator fun get(index: Long): V?
    operator fun set(index: Long, data: V)
}