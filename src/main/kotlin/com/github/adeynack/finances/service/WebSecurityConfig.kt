package com.github.adeynack.finances.service

import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
class WebSecurityConfig(
    private val authenticationEntryPoint: MyBasicAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.inMemoryAuthentication()
            .withUser("user").password("pass").authorities("USER")
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/api/health").permitAll()
            .anyRequest().authenticated()
            .and()
            .httpBasic().authenticationEntryPoint(authenticationEntryPoint)
//        http.addFilterAfter(CustomFilter(), BasicAuthenticationFilter::class.java)
    }
}

@Component
class MyBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

    override fun commence(request: HttpServletRequest, response: HttpServletResponse, authException: AuthenticationException) {
        response.addHeader("WWW-Authenticate", """Basic realm=$realmName""")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.println("HTTP Status 401 - ${authException.message}")
    }

    override fun afterPropertiesSet() {
        realmName = "Finances"
        super.afterPropertiesSet()
    }
}
