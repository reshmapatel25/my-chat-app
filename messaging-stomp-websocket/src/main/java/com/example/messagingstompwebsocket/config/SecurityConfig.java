package com.example.messagingstompwebsocket.config;

import com.example.messagingstompwebsocket.filter.AuthenticationFilter;
import com.example.messagingstompwebsocket.filter.AuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //@Autowired
   // private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*public SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder)    {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDetailsService = (UserDetailsServiceImpl) userDetailsService;
    }*/

        // Authentication : User --> Roles
        protected void configure(AuthenticationManagerBuilder auth)
                throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

            /*auth.inMemoryAuthentication()
                    .passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
                    .withUser("user1").password("secret1").roles("USER")
                    .and()
                    .withUser("admin1").password("secret1").roles("USER", "ADMIN");*/
        }
    private static UrlBasedCorsConfigurationSource configurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
        // Authorization : Role -> Access
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().headers().frameOptions().disable().and()
                     .httpBasic().and()
                    .authorizeRequests()
                    .antMatchers("/login","/users/register","/gs-guide-websocket","/gs-guide-websocket/**").permitAll()
                    .antMatchers("/**").authenticated()
                //    .antMatchers("/user").hasRole("USER")
                //    .antMatchers("/users").hasRole("ADMIN")
                    .and()
                    .addFilter(new AuthenticationFilter(authenticationManager(),messagingTemplate))
                    .addFilter(new AuthorizationFilter(authenticationManager()))
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .cors(httpSecurityCorsConfigurer -> {
                        httpSecurityCorsConfigurer.configurationSource(configurationSource());
                    });
        }

    }
