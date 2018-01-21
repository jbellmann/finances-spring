package com.github.adeynack.finances.service

import org.apache.log4j.LogManager
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

/**
 * From https://jaxenter.com/rest-api-spring-java-8-112289.html
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
class SecurityConfiguration(
    private val fakeUserDetailsService: FakeUserDetailsService
) : WebSecurityConfigurerAdapter() {

    private val log = LogManager.getLogger(SecurityConfiguration::class.java)

    init {
        log.info("Initializing.")
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(fakeUserDetailsService)
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().anyRequest().fullyAuthenticated()
        http.httpBasic()
        http.csrf().disable()
    }
}

/**
 * From https://jaxenter.com/rest-api-spring-java-8-112289.html
 */
@Service
class FakeUserDetailsService(
    private val personRepository: PersonRepository
) : UserDetailsService {

    private val log = LogManager.getLogger(FakeUserDetailsService::class.java)

    init {
        log.info("Initializing.")
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        val person = personRepository.findByFirstNameEquals(username)
        if (person == null) {
            throw UsernameNotFoundException("Username $username not found")
        }
        return User(username, "password", getGrantedAuthorities(username))
    }

    private fun getGrantedAuthorities(username: String?): Collection<GrantedAuthority> {
        return if (username == "Johny") {
            listOf(GrantedAuthority { "ROLE_ADMIN" }, GrantedAuthority { "ROLE_BASIC" })
        } else {
            listOf(GrantedAuthority { "ROLE_BASIC" })
        }
    }

}

@Repository
class PersonRepository {

    private val log = LogManager.getLogger(PersonRepository::class.java)

    init {
        log.info("Initializing.")
    }

    fun findByFirstNameEquals(username: String?): Person? {
        return username?.let { Person(it) }
    }

}

data class Person(
    val username: String
)
