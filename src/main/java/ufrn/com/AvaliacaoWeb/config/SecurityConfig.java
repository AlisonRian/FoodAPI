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
                    auth.requestMatchers("/login","/index","/logout",
                            "/salvarUsuario","/finalizarCompra").permitAll();
                    auth.requestMatchers("/cadastroPage", "/admin", "/salvar",
                            "/editar","/deletar").hasRole("ADMIN");
                    auth.requestMatchers("/verCarrinho", "/adicionarCarrinho","/finalizarCompra").hasRole("USER");
                    auth.anyRequest().permitAll();
                })
                .formLogin(login -> login.loginPage("/login")
                        .defaultSuccessUrl("/redirect", true)
                        .failureUrl("/login?error=true")
                )
                .logout(l -> {
                    l.logoutUrl("/logout");
                    l.logoutSuccessUrl("/login?logout=true");
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
