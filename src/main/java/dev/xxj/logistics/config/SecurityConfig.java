package dev.xxj.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * SecurityConfig class creates basic security-related java beans. It creates
 * a {@link UserDetailsService} bean that provides user details for authentication, a
 * {@link PasswordEncoder} bean that encodes passwords, and a {@link SecurityFilterChain} bean
 * that configures endpoint security for the application.
 *
 * @author Frank-Xiao
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Creates a {@link UserDetailsService} bean that provides user details for authentication.
     * The bean is populated with two users: one with the role "USER" and one with the roles
     * "ADMIN" and "USER".
     * <p>
     * The password for each user is encoded using the {@link PasswordEncoder} bean.
     *
     * @param encoder    the autowired {@link PasswordEncoder} bean
     * @param dataSource the autowired {@link DataSource} bean defined in applications.properties
     * @return a {@link UserDetailsService} bean initiated with two users,
     * one is user with password "password" and role "USER",
     * the other is admin with password "password" and roles "ADMIN" and "USER".
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder,
                                                 DataSource dataSource) {
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("password"))
                .roles("ADMIN", "USER")
                .build();
        UserDetails admin1 = User.builder()
                .username("admin1")
                .password(encoder.encode("password"))
                .roles("ADMIN", "USER")
                .build();
        UserDetails admin2 = User.builder()
                .username("admin2")
                .password(encoder.encode("password"))
                .roles("ADMIN", "USER")
                .build();
        return new InMemoryUserDetailsManager(user, admin, admin1, admin2);
    }

    /**
     * Creates a {@link PasswordEncoder} bean that encodes passwords using BCrypt encoding.
     * <p>
     * This bean instantiates a {@link BCryptPasswordEncoder} object to encode passwords.
     *
     * @return a {@link PasswordEncoder} bean that encodes passwords using BCrypt encoding.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a {@link SecurityFilterChain} bean that configures endpoint security for the application.
     * <p>
     * This bean configures the following security settings:
     * <ul>
     *     <li>Important request types like POST, PUT, DELETE, PATCH require the role "ADMIN"</li>
     *     <li>All requests requires authentication</li>
     *     <li>CSRF protection is disabled</li>
     *     <li>Form login and http basic authentication is enabled with default settings</li>
     * </ul>
     *
     * @param http the autowired {@link HttpSecurity} bean to be configured
     * @return a configured {@link SecurityFilterChain} bean that manages endpoint security for the application
     * @throws Exception if an error occurs while configuring the {@link HttpSecurity} bean
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.POST).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE).hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH).hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }
}
