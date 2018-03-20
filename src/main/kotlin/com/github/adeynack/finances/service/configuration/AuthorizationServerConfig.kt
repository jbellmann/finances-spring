package com.github.adeynack.finances.service.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer

@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig(val authenticationManager : AuthenticationManager, val userDetailsService: UserDetailsService) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
            .withClient("david")
            .authorizedGrantTypes("authorization_code", "refresh_token", "password")
            .secret("{noop}secret")
            .scopes("all")
            .autoApprove(true)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager).userDetailsService(userDetailsService)
    }
}
