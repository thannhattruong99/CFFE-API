package com.common.config;


import com.util.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.ws.rs.HttpMethod;

import java.util.List;

import static com.util.PathConstant.*;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] PERMIT_PATHS = {"/v3/api-docs/**", "/swagger-ui/**",
            "/swagger-ui.html","/swagger-ui/index.html?configUrl=/api-docs/swagger-config", "/swagger-ui/favicon-32x32.png"};

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean(name = "adminAuthorities")
    public List<String> getAdminAuthorities(){
        List<String> adminAuthorities = FileHelper.loadResource(ADMIN_AUTHORITY_PATH);
        return adminAuthorities;
    }

    @Bean(name = "guestAuthorities")
    public List<String> getGuestAuthorities(){
        List<String> adminAuthorities = FileHelper.loadResource(GUEST_AUTHORITY_PATH);
        return adminAuthorities;
    }

    @Bean(name = "managerAuthorities")
    public List<String> getManagerAuthorities(){
        List<String> adminAuthorities = FileHelper.loadResource(MANAGER_AUTHORITY_PATH);
        return adminAuthorities;
    }

    @Bean(name = "applicationAuthorities")
    public List<String> getApplicationAuthorities(){
        List<String> adminAuthorities = FileHelper.loadResource(APPLICATION_AUTHORITY_PATH);
        return adminAuthorities;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(PERMIT_PATHS).permitAll()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, REXP_ALL_PATH);
        web.ignoring().antMatchers(
                PERMIT_PATHS);
    }

}
