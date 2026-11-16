package com.rica.investigadores_service.investigadores.aplicacion;

import com.rica.investigadores_service.investigadores.dominio.Investigador;

import java.util.List;

public interface InvestigadorUseCase {
    List<Investigador> listarTodos();
    Investigador buscarPorId(Long id);
    Investigador registrar(String nombreCompleto, String correoInstitucional, String grupoInvestigacion);
    void iniciarEliminacion(Long id);
    void confirmarEliminacion(String correoInstitucional);
}