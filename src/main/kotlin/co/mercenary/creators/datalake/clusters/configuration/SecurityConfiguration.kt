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

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.session.data.redis.RedisFlushMode
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.security.SecureRandom
import javax.sql.DataSource

@EnableWebSecurity
@EnableRedisHttpSession(redisNamespace = "datalake:session", redisFlushMode = RedisFlushMode.IMMEDIATE)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    private val encoder: PasswordEncoder by lazy {
        BCryptPasswordEncoder(12, SecureRandom())
    }

    @Autowired
    fun configureAuthentication(conf: AuthenticationManagerBuilder, pass: PasswordEncoder, data: DataSource) {
        conf.jdbcAuthentication().dataSource(data).passwordEncoder(pass)
    }

    @Bean
    fun passwordEncoder() = encoder

    override fun configure(conf: HttpSecurity) {
        conf.authorizeRequests().antMatchers("/open/**").permitAll().antMatchers("/user/**").hasAuthority("USER")
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority("ACTUATOR").and().httpBasic().and().csrf().disable()
    }
}