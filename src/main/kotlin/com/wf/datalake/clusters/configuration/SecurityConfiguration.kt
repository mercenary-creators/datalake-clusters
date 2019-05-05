package com.wf.datalake.clusters.configuration

import co.mercenary.creators.core.kotlin.AbstractLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.security.SecureRandom
import javax.sql.DataSource

@EnableWebSecurity
@EnableRedisHttpSession
class SecurityConfiguration : AbstractLogging() {

    private val encoder: PasswordEncoder by lazy {
        BCryptPasswordEncoder(16, SecureRandom())
    }

    @Autowired
    fun configureAuthentication(conf: AuthenticationManagerBuilder, pass: PasswordEncoder, data: DataSource) {
        conf.jdbcAuthentication().dataSource(data).passwordEncoder(pass)
            .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?")
            .authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username=?")
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = encoder
}