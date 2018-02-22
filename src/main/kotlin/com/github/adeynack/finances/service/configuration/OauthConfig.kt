package com.github.adeynack.finances.service.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.client.BaseClientDetails
import org.springframework.stereotype.Service

/*

Based on:
https://jugbd.org/2017/09/19/implementing-oauth2-spring-boot-spring-security/

Get a token
http POST "localhost:8080/oauth/token?client_id=david&client_secret=secret&grant_type=password&username=david&password=secret"

Request a resource
http localhost:8080/api/account
http localhost:8080/api/account Authorization:"Bearer 06c7fc44-6552-4d24-a1ef-f29a3d2a85a4"

*/

private val userDavid = BaseClientDetails(
    "david",
    "oauth2-resource",
    "read,write",
    "client-credentials,password,refresh_token",
    "ROLE_USER"
).apply {
    clientSecret = "secret"
    accessTokenValiditySeconds = 5_000
    refreshTokenValiditySeconds = 50_000
}

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http

            .authorizeRequests()
            .antMatchers("/oauth").permitAll()

            .and()

            .authorizeRequests()
            .anyRequest().hasRole("USER")
    }
}

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(
    private val authenticationManager: AuthenticationManager
) : AuthorizationServerConfigurerAdapter() {

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security
            .tokenKeyAccess("permitAll()")
            .checkTokenAccess("isAuthenticated()")
            .allowFormAuthenticationForClients()
    }

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.withClientDetails { clientId ->
            when (clientId) {
                "david" -> userDavid
                else -> null
            }
        }
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
            .authenticationManager(authenticationManager)
            .allowedTokenEndpointRequestMethods(
                HttpMethod.GET,
                HttpMethod.POST
            )
    }
}

@Configuration
class AuthenticationManagerConfiguration {

    @Autowired
    fun authenticationManager(builder: AuthenticationManagerBuilder, userDetailsService: UserDetailsService) {
        builder.userDetailsService(userDetailsService)
    }

}

@Service
class CustomUserDetailsService : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails? =
        when (username) {
            null -> null
            "david" -> User(
                userDavid.clientId,
                userDavid.clientSecret,
                true,
                true,
                true,
                true,
                userDavid.authorities
            )
            else -> null
        }
}
