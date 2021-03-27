package co.id.telkom.epuskesmas;

import co.id.telkom.epuskesmas.auth.BasicAuth;
import co.id.telkom.epuskesmas.response.HandlerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private BasicAuth basicAuth;

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> {
            HandlerResponse.responseForbidden(response, "");
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            HandlerResponse.responseForbidden(response, "ACCESS DENIED");
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // TODO:
        // - Basic Auth provider still always accessing database
        //   to get username and password to be verified
        // - Change to JWT if possible
        auth.authenticationProvider(basicAuth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .httpBasic()
                .realmName("e-Puskesmas Realm")
            .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/error").permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/api/v1/**").hasAuthority("USER")
                .anyRequest().authenticated()
            .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint())
                .accessDeniedHandler(accessDeniedHandler());
    }
}
