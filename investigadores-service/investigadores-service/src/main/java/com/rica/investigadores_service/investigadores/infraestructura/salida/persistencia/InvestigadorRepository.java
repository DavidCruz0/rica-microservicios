package com.rica.investigadores_service.investigadores.infraestructura.salida.persistencia;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rica.investigadores_service.investigadores.dominio.CorreoInstitucional;
import com.rica.investigadores_service.investigadores.dominio.Investigador;

public interface InvestigadorRepository extends JpaRepository<Investigador, Long> {

    boolean existsByCorreoInstitucional_Valor(String valor);

    Investigador findByCorreoInstitucional(CorreoInstitucional correoInstitucional);

    Optional<Investigador> findByCorreoInstitucional_Valor(String correoInstitucional);
}
