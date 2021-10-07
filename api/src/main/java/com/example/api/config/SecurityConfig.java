package com.example.api.config;

import javax.servlet.Filter;

import com.auth0.jwt.algorithms.Algorithm;
import com.example.api.component.JWTFilter;
import com.example.api.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Filter jwtFilter;
    private final UserRepository userRepository;

    public SecurityConfig(UserRepository repository, JWTFilter jwtFilter) {
        userRepository = repository;
        this.jwtFilter = jwtFilter;
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/user/register", "/api/user/login").permitAll()
                .antMatchers("/api/category", "/api/ingredient", "/api/opinion", "/api/recipe").permitAll()
                .antMatchers(HttpMethod.PATCH, "/api/user/password", "/api/user/email").authenticated()
                .antMatchers(HttpMethod.GET, "/api/user/*").hasAnyAuthority("ADMIN", "MODERATOR", "USER")
                .antMatchers(HttpMethod.PUT, "/api/user/*").hasAuthority("ADMIN") 
                .antMatchers(HttpMethod.POST, "/api/user/*").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/api/category/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/category/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.PATCH, "/api/category/**").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/category/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/category/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/api/ingredient/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/ingredient/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/ingredient/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/ingredient/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/api/opinion/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/opinion/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/opinion/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/opinion/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.GET, "/api/recipe/*").permitAll()
                .antMatchers(HttpMethod.PUT, "/api/recipe/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.POST, "/api/recipe/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers(HttpMethod.DELETE, "/api/recipe/*").hasAnyAuthority("ADMIN", "MODERATOR")
                .antMatchers("/api/user/**").hasAnyAuthority("USER", "MODERATOR", "ADMIN")
                .antMatchers("/", "/**").permitAll()
            .and()
            .cors()
            .and()
            .csrf().disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            .and()
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowCredentials(true);
        corsConfig.addAllowedOrigin("http://localhost:4200");
        corsConfig.addAllowedHeader("*");
        corsConfig.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
            username -> userRepository.findByLogin(username)
                                    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"))
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Algorithm jwtAlgorithm() {
        return Algorithm.HMAC256("assd323%&GHg");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
