package co.mercenary.creators.datalake.clusters.support

import co.mercenary.creators.core.kotlin.*
import co.mercenary.creators.datalake.clusters.support.arch.RuntimeStatistics
import co.mercenary.creators.datalake.clusters.support.redis.LakeRedisBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.*
import org.springframework.jdbc.core.JdbcTemplate

abstract class AbstractDataLakeSupport : AbstractLogging(), ApplicationContextAware {
    @Autowired
    private lateinit var template: JdbcTemplate

    @Autowired
    private lateinit var datalake: LakeRedisBean

    private lateinit var application: ApplicationContext

    override fun setApplicationContext(context: ApplicationContext) {
        application = context
    }

    protected val stat: RuntimeStatistics
        get() = RuntimeStatistics.make()

    protected val jdbc: JdbcTemplate
        get() = template

    protected val redis: LakeRedisBean
        get() = datalake

    protected val context: ApplicationContext
        get() = application

    protected val todosweb = getWebClient("http://jsonplaceholder.typicode.com/todos")

    protected val postsweb = getWebClient("http://jsonplaceholder.typicode.com/posts")

    protected fun getWebClient(base: String) = WebClient.create(base)

    protected fun query(sql: String, key: String = "results") = json(key to jdbc.queryForList(sql))

    protected fun <T> timed(block: () -> T): T = timed({ info { it } }, block)

    protected fun clock(name: String = "real_time", block: () -> JSONObject) = NanoTicker().let { tick -> block().also { it[name] = tick(false) } }

    protected fun getEnvironmentProperty(name: String): String? = context.environment.getProperty(name)

    protected fun getEnvironmentPropertyIrElse(name: String, other: String): String = context.environment.getProperty(name, other)

    protected fun getEnvironmentPropertyOrElseCall(name: String, other: () -> String): String = getEnvironmentProperty(name) ?: other()

    data class PostData(val userId: Int, val id: Int, val title: String, val body: String)

    data class TodoData(val userId: Int, val id: Int, val title: String, val completed: Boolean)
}