package be.milete.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){

        UserDetails testUser = User
                .withDefaultPasswordEncoder()
                .username("testUser")
                .password("testPwd")
                .roles("USER")
                .build();

        UserDetails testAdmin = User
                .withDefaultPasswordEncoder()
                .username("testAdmin")
                .password("adminPwd")
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(testUser, testAdmin);
        logger.debug("inMemoryUserDetailsManager has been created");
        return inMemoryUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(GET, "/info/version", "/api-docs").permitAll()
                .requestMatchers(POST, "/news-channel").hasRole("ADMIN")
                .requestMatchers(PUT, "/news-channel/*").hasRole("ADMIN")
                .requestMatchers(GET, "/news-channel").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/**").hasAnyRole("USER", "ADMIN"))
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.realmName("myRealm"))
                .csrf(AbstractHttpConfigurer::disable);

        DefaultSecurityFilterChain build = http.build();
        logger.debug("securityFilterChain has been build");

        return build;
    }
}
