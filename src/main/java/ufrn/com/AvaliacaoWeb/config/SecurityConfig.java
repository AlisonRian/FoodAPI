package ufrn.com.AvaliacaoWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login").permitAll();
                    auth.requestMatchers("/index").permitAll();
                    auth.requestMatchers("/logout").permitAll();
                    auth.requestMatchers("/salvarUsuario").permitAll();
                    auth.requestMatchers("/finalizarCompra").permitAll();
                    auth.requestMatchers("/cadastroPage").hasRole("ADMIN");
                    auth.requestMatchers("/admin").hasRole("ADMIN");
                    auth.requestMatchers("/salvar").hasRole("ADMIN");
                    auth.requestMatchers("/editar/").hasRole("ADMIN");
                    auth.requestMatchers("/deletar").hasRole("ADMIN");
                    auth.requestMatchers("/verCarrinho").hasRole("USER");
                    auth.requestMatchers("/adicionarCarrinho").hasRole("USER");
                    auth.requestMatchers("/finalizarCompra").hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> login.loginPage("/login"))
                .logout(l -> {
                    l.logoutUrl("/logout");
                    l.clearAuthentication(true);
                    l.deleteCookies().invalidateHttpSession(true);
                })
                .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
