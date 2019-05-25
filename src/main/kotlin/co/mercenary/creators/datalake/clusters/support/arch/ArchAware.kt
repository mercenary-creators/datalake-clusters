package co.mercenary.creators.datalake.clusters.support.arch

import co.mercenary.creators.core.kotlin.json
import co.mercenary.creators.core.kotlin.json.*

interface ArchAware: JSONAware, JSONObjectProvider {
    override fun toJSONObject(): JSONObject {
        return json(toByteArray())
    }
}