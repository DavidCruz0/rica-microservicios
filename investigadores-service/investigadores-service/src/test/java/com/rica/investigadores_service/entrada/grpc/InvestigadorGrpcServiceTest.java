package com.rica.investigadores_service.entrada.grpc;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.rica.investigadores_service.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadores_service.investigadores.infraestructura.entrada.grpc.InvestigadorGrpcService;
import com.rica.investigadoresservice.grpc.InvestigadorRequest;
import com.rica.investigadoresservice.grpc.InvestigadorResponse;

import io.grpc.stub.StreamObserver;

public class InvestigadorGrpcServiceTest {
@Test void respondeSegunRepositorio() {
        var repo = mock(RepositorioInvestigadores.class);
        when(repo.existeCorreo("ana@uptc.edu.co")).thenReturn(true);
        var service = new InvestigadorGrpcService(repo);
        StreamObserver<InvestigadorResponse> observer = mock(StreamObserver.class);
        service.existeInvestigador(InvestigadorRequest.newBuilder().setCorreoInstitucional("ana@uptc.edu.co").build(),observer);
        verify(observer).onNext(InvestigadorResponse.newBuilder().setExiste(true).build());
        verify(observer).onCompleted();
        StreamObserver<InvestigadorResponse> missing = mock(StreamObserver.class);
        service.existeInvestigador(InvestigadorRequest.newBuilder().setCorreoInstitucional("nadie@uptc.edu.co").build(),missing);
        verify(missing).onNext(InvestigadorResponse.getDefaultInstance()); verify(missing).onCompleted();
    }
}
