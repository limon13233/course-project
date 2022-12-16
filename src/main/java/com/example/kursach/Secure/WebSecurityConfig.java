package com.example.kursach.Secure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login", "/registration","/main_login.css","/**").permitAll().
                antMatchers("/profile").hasAnyAuthority("USER").
                antMatchers("/Add","/OrderCreate","/AddMeembershipUser","/AddBookUser","/edit","/Statistic","/Delete","/ViewOrder").hasAnyAuthority("EMPLOYEE").
                antMatchers("/backup","/editrole").hasAnyAuthority("ADMIN").
        anyRequest().authenticated().
                and().formLogin().loginPage("/login").permitAll().
                and().logout().permitAll().
                and().csrf().disable().cors().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).
                passwordEncoder(passwordEncoder).
                usersByUsernameQuery("SELECT username, password, active FROM user WHERE username = ?").
                authoritiesByUsernameQuery("SELECT u.username, ur.roles FROM user u INNER JOIN user_roles ur on u.id = ur.id_user WHERE username = ?");
    }

    @Lazy
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}

