package be.milete.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

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

        return new InMemoryUserDetailsManager(testUser, testAdmin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(GET, "/info/version", "/api-docs").permitAll()
                .requestMatchers(POST, "/news-channel").hasRole("ADMIN")
                .requestMatchers(GET, "/news-channel").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/**").hasAnyRole("USER", "ADMIN"))
                .httpBasic().and()
                .csrf().disable();

        return http.build();
    }
}
