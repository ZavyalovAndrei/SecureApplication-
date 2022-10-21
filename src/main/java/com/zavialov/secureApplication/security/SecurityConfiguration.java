package com.zavialov.secureApplication.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder managerBuilder) throws Exception {
        managerBuilder.inMemoryAuthentication()
                .withUser("admin").password("{noop}7788").authorities("by-userdata", "by-age", "by-city")
                .and()
                .withUser("user1111").password("{noop}1111").authorities("by-age", "by-city");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin()
                .and()
                .authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/by-city").hasAuthority("by-city")
                .and()
                .authorizeRequests().antMatchers("/by-age").hasAuthority("by-age")
                .and()
                .authorizeRequests().antMatchers("/by-name-surname").hasAuthority("by-userdata")
                .and()
                .authorizeRequests().anyRequest().authenticated();
    }
}