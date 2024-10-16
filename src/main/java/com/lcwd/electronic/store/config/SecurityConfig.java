package com.lcwd.electronic.store.config;

import com.lcwd.electronic.store.security.JWTAuthenticationEntryPoint;
import com.lcwd.electronic.store.security.JWTAuthenticationFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Autowired
    private JWTAuthenticationFilter filter;
    @Autowired
    private JWTAuthenticationEntryPoint entryPoint;

    private final String [] PUBLIC_URLS={
            "/swagger-ui/**",  // Swagger UI path
            "/v3/api-docs/**", // OpenAPI 3.0 docs
            "/swagger-resources/**",  // Swagger resources
            "/webjars/**"  // Swagger UI assets
    };


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

          //DISABLE THE CORS
    //   httpSecurity.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.disable());


        //For Enable the CORS OR WE HAVE TO ADD THE ANNOTATION ON CONTROLLERS(// @CrossOrigin)
        httpSecurity.cors(httpSecurityCorsConfigurer ->
                httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        //METHODS
                        CorsConfiguration corsConfiguration=new CorsConfiguration();
                     //   corsConfiguration.addAllowedOrigin("http://localhost:3030");
                       // corsConfiguration.setAllowedOrigins(List.of("http://localhost:1010","http://localhost:6060"));
                        corsConfiguration.setAllowedOriginPatterns(List.of(""));
                        corsConfiguration.setAllowedMethods(List.of(""));
                        corsConfiguration.setAllowedHeaders(List.of(""));
                        corsConfiguration.setAllowCredentials(true);
                        corsConfiguration.setMaxAge(30000L);
                        return corsConfiguration;
                    }
                }));



        //DISABLE THE CSRF
        httpSecurity.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());






        httpSecurity.authorizeHttpRequests(request ->
            request.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()

                    .requestMatchers("/users/**").permitAll()
                    .requestMatchers(HttpMethod.POST,"/users").permitAll()
                   // .requestMatchers(HttpMethod.DELETE,"/users").hasRole("ADMIN")
                    .requestMatchers("/category/**").permitAll()
                    .requestMatchers(HttpMethod.GET,"/product/**").permitAll()
                    .requestMatchers("/product").hasRole("ADMIN")
                    .requestMatchers("/cart/**").permitAll()
                    .requestMatchers("/orders/**").permitAll()
                    .requestMatchers("/auth/generateToken").permitAll()
                    .requestMatchers(PUBLIC_URLS).permitAll()
//                    .requestMatchers(HttpMethod.GET,"/product").permitAll()
                   // .requestMatchers(HttpMethod.DELETE,"/users").hasRole("ADMIN")
                  //  .requestMatchers("/cart").permitAll()

        );

       // httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.formLogin(Customizer.withDefaults());

        httpSecurity.exceptionHandling(ex->ex.authenticationEntryPoint(entryPoint));
        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();

    }


}





