package ufrn.com.AvaliacaoWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.com.AvaliacaoWeb.domain.Comidas;

public interface ComidasRepository extends JpaRepository<Comidas, Long> {
}

