package com.gmail.kaminski.viktar.onlinemarket.controller.config;

import com.gmail.kaminski.viktar.onlinemarket.controller.config.handler.AppUrlAuthenticationSuccessHandler;
import com.gmail.kaminski.viktar.onlinemarket.controller.config.handler.LoginAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final GlobalValue globalValue;

    public WebSecurityConfig(UserDetailsService userDetailsService, GlobalValue globalValue) {
        this.userDetailsService = userDetailsService;
        this.globalValue = globalValue;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/profile",
                        "/profile/edit")
                .hasAnyRole(globalValue.getCustomerRoleName(), globalValue.getSaleRoleName(), globalValue.getAdministratorRoleName())
                .antMatchers(
                        "/articles",
                        "/articles/*",
                        "/items",
                        "/orders")
                .hasAnyRole(globalValue.getCustomerRoleName(), globalValue.getSaleRoleName())
                .antMatchers(
                        "/reviews/new",
                        "/items/*/buy",
                        "/articles/*/comments/add")
                .hasRole(globalValue.getCustomerRoleName())
                .antMatchers(
                        "/articles/{articleId}/comments/{commentId}/delete",
                        "/articles/*/delete",
                        "/articles/*/edit",
                        "/articles/new",
                        "/items/*",
                        "/items/upload",
                        "/items/*/copy",
                        "/items/*/delete",
                        "/orders/*",
                        "/orders/*/switchstatus")
                .hasRole(globalValue.getSaleRoleName())
                .antMatchers(
                        "/users",
                        "/users/new",
                        "/users/*/changepassword",
                        "/users/*/changerole",
                        "/reviews",
                        "/reviews/*/delete",
                        "/reviews/*/hide")
                .hasRole(globalValue.getAdministratorRoleName())
                .antMatchers("/error", "/403", "/login", "/css/**")
                .permitAll()
                .antMatchers("/**")
                .denyAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(12);
    }


}
