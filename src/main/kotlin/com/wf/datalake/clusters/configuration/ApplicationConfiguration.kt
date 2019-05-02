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

package com.wf.datalake.clusters.configuration

import co.mercenary.creators.core.kotlin.json.module.CoreKotlinModule
import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.wf.datalake.clusters.support.TimeAndDate
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.context.annotation.*
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.*
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
@ServletComponentScan
@Profile("development", "production")
class ApplicationConfiguration : WebMvcConfigurer {

    @Bean
    fun datalakeJackson(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder.featuresToDisable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .dateFormat(TimeAndDate.getDefaultDateFormat()).timeZone(TimeAndDate.getDefaultTimeZone())
                .modulesToInstall(CoreKotlinModule(), KotlinModule(), ParameterNamesModule(), JodaModule())
                .featuresToEnable(JsonGenerator.Feature.ESCAPE_NON_ASCII, JsonParser.Feature.ALLOW_COMMENTS, JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
        }
    }

    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8)
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/resources/**")
            .addResourceLocations("/resources/", "/other-resources/")
            .setCachePeriod(3600)
            .resourceChain(true)
            .addResolver(PathResourceResolver())
    }
}