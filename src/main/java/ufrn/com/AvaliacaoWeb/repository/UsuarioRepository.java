package ufrn.com.AvaliacaoWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.com.AvaliacaoWeb.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByUsername(String username);
}
