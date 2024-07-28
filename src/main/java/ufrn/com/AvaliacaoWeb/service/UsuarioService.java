package ufrn.com.AvaliacaoWeb.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ufrn.com.AvaliacaoWeb.domain.Usuario;
import ufrn.com.AvaliacaoWeb.repository.UsuarioRepository;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {
    UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optional = repository.findUsuarioByUsername(username);
        if(optional.isPresent()) {
            return optional.get();
        }
        throw new UsernameNotFoundException("User not found");
    }

    public void create(Usuario usuario) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        repository.save(usuario);
    }

    public String getLoggedInUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
