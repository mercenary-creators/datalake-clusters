package co.mercenary.creators.datalake.clusters.configuration

import co.mercenary.creators.datalake.clusters.support.redis.LakeRedisBean
import org.springframework.beans.factory.annotation.*
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest
import org.springframework.context.annotation.*
import org.springframework.data.redis.connection.RedisConnectionFactory
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

    @Bean
    fun lakeRedisBean(conf: RedisConnectionFactory, @Value("\${data.lake.redis.bean.name:datalake:bean}") name: String) = LakeRedisBean(conf, name)

    override fun configure(conf: HttpSecurity) {
        conf.authorizeRequests().antMatchers("/open/**").permitAll().antMatchers("/user/**").hasAuthority("USER")
            .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAuthority("ACTUATOR").and().httpBasic().and().csrf().disable()
    }
}