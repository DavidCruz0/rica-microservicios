package com.rica.investigadores_service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.rica.investigadores_service.investigadores.aplicacion.InvestigadorUseCase;
import com.rica.investigadores_service.investigadores.infraestructura.salida.mensajeria.OutboxEventRepository;
import com.rica.investigadores_service.investigadores.infraestructura.salida.persistencia.InvestigadorRepository;

@SpringBootTest @ActiveProfiles("test")
public class OutboxTransaccionTest {
    @Autowired InvestigadorUseCase servicio;
    @Autowired InvestigadorRepository investigadores;
    @MockitoSpyBean OutboxEventRepository outbox;
    @Test void registroYEventoSeConfirmanJuntos() {
        var investigador = servicio.registrar("Nombre con \"comillas\"", "atomicidad@uptc.edu.co", "Grupo");
        assertTrue(investigadores.existsById(investigador.getId()));
        assertTrue(outbox.findAll().stream().anyMatch(e -> e.getClave().equals("atomicidad@uptc.edu.co") && !e.isPublicado()));
    }
    @Test void falloEnOutboxRevierteElInvestigador() {
        doThrow(new IllegalStateException("fallo escritura outbox")).when(outbox).save(any());
        assertThrows(IllegalStateException.class, () -> servicio.registrar("Ana", "rollback@uptc.edu.co", "Grupo"));
        assertFalse(investigadores.existsByCorreoInstitucional_Valor("rollback@uptc.edu.co"));
    }

    @BeforeEach
    void limpiar() {
        investigadores.deleteAll();
        outbox.deleteAll();
    }
}
