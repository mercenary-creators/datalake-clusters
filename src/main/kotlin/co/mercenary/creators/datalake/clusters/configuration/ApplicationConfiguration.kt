/*
 * Copyright (c) 2019, Mercenary Creators Company. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.mercenary.creators.datalake.clusters.configuration

import co.mercenary.creators.kotlin.json.module.MercenaryKotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.*
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
@ServletComponentScan
class ApplicationConfiguration : WebMvcConfigurer {

    @Bean
    fun datalakeJackson() = Jackson2ObjectMapperBuilderCustomizer {
        it.simpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS z").timeZone("UTC").modulesToInstall(MercenaryKotlinModule(), ParameterNamesModule())
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(3600).resourceChain(true).addResolver(PathResourceResolver())
    }
}