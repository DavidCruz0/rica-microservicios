package com.rica.investigadores_service.salida.mensajeria;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

import com.rica.investigadores_service.investigadores.infraestructura.salida.mensajeria.OutboxEvent;
import com.rica.investigadores_service.investigadores.infraestructura.salida.mensajeria.OutboxEventRepository;
import com.rica.investigadores_service.investigadores.infraestructura.salida.mensajeria.OutboxPoller;

public class OutboxPollerTest {
    @Test void marcaPublicadoSoloTrasConfirmacion() {
        var repo = mock(OutboxEventRepository.class);
        KafkaTemplate<String,String> kafka = mock(KafkaTemplate.class);
        var evento = new OutboxEvent("registrados","ana","{}");
        when(repo.findTop50ByPublicadoFalseOrderByIdAsc()).thenReturn(List.of(evento));
        when(kafka.send("registrados","ana","{}")).thenReturn(CompletableFuture.completedFuture(null));
        new OutboxPoller(repo,kafka).publicarPendientes(); assertTrue(evento.isPublicado());
    }
    @Test void falloConservaPendienteYOrdenYPermiteReintento() {
        var repo = mock(OutboxEventRepository.class);
        KafkaTemplate<String,String> kafka = mock(KafkaTemplate.class);
        var primero = new OutboxEvent("registrados","ana","{}");
        var segundo = new OutboxEvent("eliminados","ana","{}");
        when(repo.findTop50ByPublicadoFalseOrderByIdAsc()).thenReturn(List.of(primero,segundo));
        when(kafka.send("registrados","ana","{}")).thenReturn(CompletableFuture.failedFuture(new RuntimeException("Kafka caído")));
        var poller = new OutboxPoller(repo,kafka); poller.publicarPendientes();
        assertFalse(primero.isPublicado()); assertFalse(segundo.isPublicado());
        verify(kafka, never()).send("eliminados","ana","{}");
        when(kafka.send(anyString(),anyString(),anyString())).thenReturn(CompletableFuture.completedFuture(null));
        poller.publicarPendientes(); assertTrue(primero.isPublicado()); assertTrue(segundo.isPublicado());
    }
}
