package com.wf.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.context.*
import org.springframework.util.StringValueResolver

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware, EmbeddedValueResolverAware {

    private lateinit var resolve: StringValueResolver

    private lateinit var application: ApplicationContext

    override fun setApplicationContext(context: ApplicationContext) {
        application = context
    }

    override fun setEmbeddedValueResolver(resolver: StringValueResolver) {
        resolve = resolver
    }

    protected val context: ApplicationContext
        get() = application

    protected open fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    protected open fun getEnvironmentProperty(name: String): String? = context.environment.getProperty(name)

    protected open fun getEnvironmentProperty(name: String, other: String): String = context.environment.getProperty(name, other)

    protected open fun getEnvironmentPropertyOrElse(name: String, other: () -> String = { EMPTY_STRING }): String = getEnvironmentProperty(name) ?: other()
}