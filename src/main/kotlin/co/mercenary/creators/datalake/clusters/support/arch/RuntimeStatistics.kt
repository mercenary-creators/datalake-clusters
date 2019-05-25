package co.mercenary.creators.datalake.clusters.support.arch

import java.lang.management.ManagementFactory
import java.util.*

data class RuntimeStatistics(val started: Date, val elapsed: Long, val process: Long) : ArchAware {
    override fun toString(): String {
        return toJSONString()
    }

    companion object {
        val bean = ManagementFactory.getRuntimeMXBean()!!
        val proc = (bean.name?.split("@")?.get(0) ?: "0").toLong()
        fun make() = RuntimeStatistics(Date(bean.startTime), bean.uptime, proc)
    }
}