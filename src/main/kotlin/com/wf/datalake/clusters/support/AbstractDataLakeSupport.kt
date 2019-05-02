package com.wf.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.context.*

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware {

    private var context: ApplicationContext? = null

    override fun setApplicationContext(context: ApplicationContext) {
        this.context = context
    }

    protected open fun getApplicationContext(): ApplicationContext? = this.context

    protected open fun getEnvironmentPropertyOrElse(name: String, other: String = EMPTY_STRING): String {
        return getApplicationContext()?.environment?.getProperty(name, other) ?: other
    }

    protected open fun getEnvironmentPropertyOrElse(name: String, other: () -> String): String {
        return getApplicationContext()?.environment?.getProperty(name) ?: other()
    }

    protected open fun <T> timed(block: () -> T): T = timed({ info { it } }, block)
}