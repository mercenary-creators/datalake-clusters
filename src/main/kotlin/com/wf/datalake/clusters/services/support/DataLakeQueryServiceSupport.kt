package com.wf.datalake.clusters.services.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

abstract class DataLakeQueryServiceSupport : DataLakeServiceSupport() {

    @Autowired
    private lateinit var database: DataSource

    private val template: JdbcTemplate by lazy {
        JdbcTemplate(database)
    }

    protected fun template() = template

    protected fun query(sql: String, key: String = "results") = json(key to template().queryForList(sql), "uuid" to uuid())
}