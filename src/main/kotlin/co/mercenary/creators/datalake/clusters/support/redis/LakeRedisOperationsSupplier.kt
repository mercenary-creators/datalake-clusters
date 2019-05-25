package co.mercenary.creators.datalake.clusters.support.redis

interface LakeRedisOperationsSupplier {
    fun getRedisOperations(): LakeRedisOperations
}