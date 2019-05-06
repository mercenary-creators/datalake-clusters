package com.wf.datalake.clusters.support

import java.text.SimpleDateFormat
import java.util.*

object TimeAndDate {

    private val DEFAULT_TIME_ZONE: TimeZone by lazy {
        TimeZone.getTimeZone("UTC")
    }

    private val DEFAULT_DATE_TEXT: SimpleDateFormat by lazy {
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS z")
    }

    fun getDefaultTimeZone(): TimeZone = DEFAULT_TIME_ZONE.clone() as TimeZone

    fun setDefaultTimeZone(zone: TimeZone = DEFAULT_TIME_ZONE) {
        TimeZone.setDefault(zone)
    }

    fun getDefaultDateFormat(): SimpleDateFormat = DEFAULT_DATE_TEXT.clone() as SimpleDateFormat
}