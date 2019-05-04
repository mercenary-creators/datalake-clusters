package com.wf.datalake.clusters

import com.wf.datalake.clusters.support.TimeAndDate
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataLakeApplication

fun main(args: Array<String>) {
    TimeAndDate.setDefaultTimeZone()
    runApplication<DataLakeApplication>(*args)
}
