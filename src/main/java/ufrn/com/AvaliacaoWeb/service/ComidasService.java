package ufrn.com.AvaliacaoWeb.service;

import ufrn.com.AvaliacaoWeb.domain.Comidas;
import org.springframework.stereotype.Service;
import ufrn.com.AvaliacaoWeb.repository.ComidasRepository;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ComidasService {
    private final ComidasRepository comidasRepository;
    public ComidasService(ComidasRepository comidasRepository) {
        this.comidasRepository = comidasRepository;
    }
    public void create(Comidas comidas) {
        comidasRepository.save(comidas);
    }
    public Optional<Comidas> findById(Long id) {
        return comidasRepository.findById(id);
    }
    public void update(Comidas comidas) {
        comidasRepository.saveAndFlush(comidas);
    }
    public List<Comidas> findAll(){
        return comidasRepository.findAllByIsDeletedIsNull();
    }
    public void delete(Long id) {
        Comidas comida = comidasRepository.findById(id).get();
        Date date = new Date();
        comida.setIsDeleted(date);
        comidasRepository.saveAndFlush(comida);
    }
    public boolean existsById(Long id) {
        return comidasRepository.existsById(id);
    }
}
