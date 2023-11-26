package com.Abhay.Loveseat.Security;

import com.Abhay.Loveseat.Service.CustomSuccessHandler;
import com.Abhay.Loveseat.Service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    CustomUserDetailService customUserDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    CustomSuccessHandler customSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests)-> requests
                        .requestMatchers("/register","/otp-page","/login","/user-register","/verify",
                                "/static/**","/css/bootstrap.min.css","/css/tiny-slider.css","/css/style.css",
                                "/resentOtp","/images/**"
                                ).permitAll()
                        .requestMatchers("/home").hasAnyAuthority("USER")
                        .requestMatchers("/admin").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())

                .formLogin((form)->form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandler)
                )
                .logout((logout)-> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login"));
        return http.build();

    }
    @Autowired
    public  void  configure(AuthenticationManagerBuilder auth)throws Exception{
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder);
    }
    public AuthenticationFailureHandler customAuthenticationFailureHandler(){
        return  new SimpleUrlAuthenticationFailureHandler("/login?error=true");
    }


}
