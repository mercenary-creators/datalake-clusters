package com.wf.datalake.clusters.configuration

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfigurationAdapter : WebSecurityConfigurerAdapter() {
    override fun configure(conf: HttpSecurity) {
        conf.authorizeRequests().antMatchers("/datalake/**",  "/clusters/**", "/services/**").permitAll()
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority("ACTUATOR").and().httpBasic().and().csrf().disable()
    }
}