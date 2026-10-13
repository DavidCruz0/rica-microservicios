package com.rica.investigadores_service.aplication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rica.investigadores_service.investigadores.aplicacion.InvestigadorFactory;
import com.rica.investigadores_service.investigadores.aplicacion.InvestigadorService;
import com.rica.investigadores_service.investigadores.aplicacion.PublicadorEventos;
import com.rica.investigadores_service.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadores_service.investigadores.dominio.CorreoDuplicadoException;
import com.rica.investigadores_service.investigadores.dominio.CorreoInstitucional;
import com.rica.investigadores_service.investigadores.dominio.Investigador;

@ExtendWith(MockitoExtension.class)
public class InvestigadorServiceTest {
    @Mock RepositorioInvestigadores repositorio;
    @Mock PublicadorEventos eventos;
    InvestigadorService servicio;
    Investigador investigador;
    @BeforeEach void preparar() {
        servicio = new InvestigadorService(repositorio, new InvestigadorFactory(repositorio), eventos);
        investigador = new Investigador(7L, "Ana", new CorreoInstitucional("ana@uptc.edu.co"), "Grupo");
    }
    @Test void registrarGuardaYPublicaEvento() {
        when(repositorio.guardar(any())).thenAnswer(i -> { Investigador x=i.getArgument(0); x.setId(7L); return x; });
        assertEquals(7L, servicio.registrar("Ana", "ana@uptc.edu.co", "Grupo").getId());
        verify(eventos).publicar("investigadores.registrados", "ana@uptc.edu.co",
                Map.of("id",7L,"correoInstitucional","ana@uptc.edu.co","nombreCompleto","Ana"));
    }
    @Test void duplicadoNoGuardaNiPublica() {
        when(repositorio.existeCorreo("ana@uptc.edu.co")).thenReturn(true);
        assertThrows(CorreoDuplicadoException.class, () -> servicio.registrar("Ana","ana@uptc.edu.co","Grupo"));
        verify(repositorio, never()).guardar(any()); verifyNoInteractions(eventos);
    }

}
