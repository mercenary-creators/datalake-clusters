package com.wf.datalake.clusters.services.support

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate

abstract class DataLakeQueryServiceSupport : DataLakeServiceSupport() {

    @Autowired
    private lateinit var template: JdbcTemplate

    val jdbc: JdbcTemplate
        get() = template

    fun query(sql: String, key: String = "results") = json(key to jdbc.queryForList(sql))
}