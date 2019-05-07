package com.wf.datalake.clusters.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.*
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession
import java.security.SecureRandom
import javax.sql.DataSource

@EnableWebSecurity
@EnableRedisHttpSession
class SecurityConfiguration {

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

    @Configuration
    inner class SecurityConfigurationAdapter : WebSecurityConfigurerAdapter() {
        override fun configure(conf: HttpSecurity) {
            conf.authorizeRequests().antMatchers("/datalake/**", "/clusters/**", "/services/**").permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority("ACTUATOR").and().httpBasic().and().csrf().disable()
        }
    }
}