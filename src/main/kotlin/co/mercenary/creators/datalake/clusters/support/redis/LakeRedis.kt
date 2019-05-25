package co.mercenary.creators.datalake.clusters.support.redis

import co.mercenary.creators.core.kotlin.*

interface LakeRedis {
    fun name(): String
    fun stop(): Boolean
    fun keys(): List<String>
    fun exists(name: String): Boolean
    fun counts(keys: List<String>): Long
    fun counts(vararg keys: String): Long
    fun counts(keys: Iterable<String>): Long
    fun counts(keys: Sequence<String>): Long
    fun execute(block: (LakeRedisOperations) -> JSONObject): JSONObject
}