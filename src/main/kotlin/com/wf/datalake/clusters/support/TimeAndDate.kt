package com.wf.datalake.clusters.support

import java.text.SimpleDateFormat
import java.util.*

object TimeAndDate {

    private val DEFAULT_TIMEZOME: TimeZone by lazy {
        TimeZone.getTimeZone("UTC")
    }

    fun getDefaultTimeZone(): TimeZone = DEFAULT_TIMEZOME

    fun setDefaultTimeZone() {
        TimeZone.setDefault(getDefaultTimeZone())
    }

    fun getDefaultDateFormat(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS z")
}