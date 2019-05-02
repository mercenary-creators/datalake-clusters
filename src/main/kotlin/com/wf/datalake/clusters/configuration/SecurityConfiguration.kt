package com.wf.datalake.clusters.configuration

import co.mercenary.creators.core.kotlin.*
import org.springframework.beans.factory.annotation.*
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.security.SecureRandom

@EnableWebSecurity
@EnableRedisHttpSession
class SecurityConfiguration : AbstractLogging() {

    private val encoder: PasswordEncoder by lazy {
        BCryptPasswordEncoder(16, SecureRandom())
    }

    @Value("\${datalake.admin.username}")
    private val username: String = EMPTY_STRING

    @Value("\${datalake.admin.password}")
    private val password: String = EMPTY_STRING

    @Autowired
    fun configureGlobal(conf: AuthenticationManagerBuilder, encoder: PasswordEncoder) {
        conf.inMemoryAuthentication().withUser(username).password(password).authorities("ADMIN", "ACTUATOR", "USER").and().passwordEncoder(encoder)
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = encoder
}