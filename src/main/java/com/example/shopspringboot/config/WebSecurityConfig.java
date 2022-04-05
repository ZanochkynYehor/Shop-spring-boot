package com.example.shopspringboot.config;

import com.example.shopspringboot.filter.CheckoutFilter;
import com.example.shopspringboot.filter.LoginSignUpFilter;
import com.example.shopspringboot.util.LoginFailureHandler;
import com.example.shopspringboot.util.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final LoginFailureHandler loginFailureHandler;
    private final LoginSuccessHandler loginSuccessHandler;

    public WebSecurityConfig(UserDetailsService userDetailsService,
                             LoginFailureHandler loginFailureHandler,
                             LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginFailureHandler = loginFailureHandler;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(new LoginSignUpFilter(), DefaultLoginPageGeneratingFilter.class);
        http.addFilterBefore(new CheckoutFilter(), X509AuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/checkout").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login?mustSignIn")
                .loginProcessingUrl("/login")
                .usernameParameter("email")
                .failureHandler(loginFailureHandler)
                .successHandler(loginSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/products")
                .deleteCookies("JSESSIONID");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}