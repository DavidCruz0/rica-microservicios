package com.rica.investigadores_service.investigadores.infraestructura.salida.persistencia;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.rica.investigadores_service.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadores_service.investigadores.dominio.Investigador;

@Component
public class InvestigadorRepositoryJpaAdapter implements RepositorioInvestigadores {

    private final InvestigadorRepository investigadorRepository;


    public InvestigadorRepositoryJpaAdapter(InvestigadorRepository investigadorRepository) {
        this.investigadorRepository = investigadorRepository;
    }

    @Override
    public List<Investigador> listarTodos() {
        return investigadorRepository.findAll();
    }

    @Override
    public Optional<Investigador> buscarPorId(Long id) {
        return investigadorRepository.findById(id);
    }

    @Override
    public boolean existeCorreo(String correoInstitucional) {
        return investigadorRepository.existsByCorreoInstitucional_Valor(correoInstitucional);
    }

    @Override
    public Investigador guardar(Investigador investigador) {
        return investigadorRepository.save(investigador);
    }
}
