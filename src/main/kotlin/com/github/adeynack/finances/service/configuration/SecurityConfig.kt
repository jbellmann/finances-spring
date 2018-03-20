package com.github.adeynack.finances.service.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ReactiveAuthenticationManagerAdapter
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.*
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
                .withUser(User.withUsername("kmeier")
                                .password("{noop}geheim")
                                .authorities("ROLE_USER")
                                .build())
    }

    @Bean("myAuthenticationManager")
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean("myUserDetailsService")
    override fun userDetailsServiceBean(): UserDetailsService {
        return super.userDetailsServiceBean()
    }

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.csrf().disable()
            .formLogin().disable()
            .authenticationManager(ReactiveAuthenticationManagerAdapter(authenticationManagerBean()))
            .authorizeExchange().anyExchange().authenticated()
        return http.build()
    }
}
