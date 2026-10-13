package com.rica.investigadores_service.investigadores.infraestructura.entrada.grpc;

import org.springframework.stereotype.Service;
    
import com.rica.investigadores_service.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadoresservice.grpc.InvestigadorGrpcGrpc;
import com.rica.investigadoresservice.grpc.InvestigadorRequest;
import com.rica.investigadoresservice.grpc.InvestigadorResponse;

import io.grpc.stub.StreamObserver;

@Service
public class InvestigadorGrpcService extends InvestigadorGrpcGrpc.InvestigadorGrpcImplBase {

    private final RepositorioInvestigadores repositorio;

    public InvestigadorGrpcService(RepositorioInvestigadores repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void existeInvestigador(InvestigadorRequest request, StreamObserver<InvestigadorResponse> responseObserver) {
        boolean existe = repositorio.existeCorreo(request.getCorreoInstitucional());
        responseObserver.onNext(InvestigadorResponse.newBuilder().setExiste(existe).build());
        responseObserver.onCompleted();
    }
}
