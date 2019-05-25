package co.mercenary.creators.datalake.clusters.support

import java.text.SimpleDateFormat
import java.util.*

object TimeAndDate {

    fun getDefaultTimeZone(): TimeZone = TimeZone.getTimeZone("UTC")

    fun setDefaultTimeZone(zone: TimeZone = getDefaultTimeZone()) {
        TimeZone.setDefault(zone)
    }

    fun getDefaultDateFormat() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS z")
}