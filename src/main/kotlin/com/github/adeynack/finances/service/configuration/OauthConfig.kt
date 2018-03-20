import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

/*
Based on:
https://docs.spring.io/spring-security-oauth2-boot/docs/2.0.0.RELEASE/reference/htmlsingle/#boot-features-security-oauth2-authorization-server

Get a token
http POST "localhost:8080/oauth/token?client_id=david&client_secret=secret&grant_type=password&username=david&password=secret"

Request a resource
http localhost:8080/api/account
http localhost:8080/api/account Authorization:"Bearer 06c7fc44-6552-4d24-a1ef-f29a3d2a85a4"

*/

//@Configuration
//@EnableAuthorizationServer
//class CustomAuthorizationServerConfigurer
//    : AuthorizationServerConfigurerAdapter() {
//
//    override fun configure(clients: ClientDetailsServiceConfigurer) {
//        clients.inMemory()
//            .withClient("david")
//            .authorizedGrantTypes("password")
//            .secret("{noop}secret")
//            .scopes("all")
//    }
//}

//@Configuration
//@EnableResourceServer
//class ResourceServerConfig : ResourceServerConfigurerAdapter() {
//
//    override fun configure(http: HttpSecurity) {
//        http
//
//            .authorizeRequests()
//            .antMatchers("/oauth").permitAll()
//
//            .and()
//
//            .authorizeRequests()
//            .anyRequest().hasRole("USER")
//    }
//}
