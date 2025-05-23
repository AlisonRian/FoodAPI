package ufrn.com.AvaliacaoWeb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.com.AvaliacaoWeb.domain.Comidas;

import java.util.List;

public interface ComidasRepository extends JpaRepository<Comidas, Long> {
    List<Comidas> findAllByIsDeletedIsNull();
}

