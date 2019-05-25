package co.mercenary.creators.datalake.clusters.support.redis

import java.util.*
import java.util.concurrent.TimeUnit

interface LakeBindKeys<H> : LakeRedisOperationsSupplier {
    fun name(): H
    fun save(): Boolean
    fun rename(name: H)
    fun getExpireTime(): Long
    fun setExpireTime(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS)
    fun getExpireDate(): Date
    fun setExpireDate(date: Date)
}