package com.wf.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.context.*

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware {

    private lateinit var application: ApplicationContext

    override fun setApplicationContext(context: ApplicationContext) {
        application = context
    }

    protected val context: ApplicationContext
        get() = application

    protected open fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    protected open fun getEnvironmentProperty(name: String): String? = context.environment.getProperty(name)

    protected open fun getEnvironmentPropertyIrElse(name: String, other: String): String = context.environment.getProperty(name, other)

    protected open fun getEnvironmentPropertyOrElseCall(name: String, other: () -> String): String = getEnvironmentProperty(name) ?: other()
}