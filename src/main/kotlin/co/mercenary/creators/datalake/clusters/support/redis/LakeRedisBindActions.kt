package co.mercenary.creators.datalake.clusters.support.redis

interface LakeRedisBindActions {
    fun <K, V> hash(): LakeBindHash<String, K, V>
}