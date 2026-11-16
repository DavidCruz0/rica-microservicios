package com.rica.investigadores_service.investigadores.aplicacion;

import com.rica.investigadores_service.investigadores.dominio.Investigador;
import com.rica.investigadores_service.investigadores.dominio.CorreoInstitucional;
import com.rica.investigadores_service.investigadores.dominio.CorreoDuplicadoException;
import com.rica.investigadores_service.investigadores.dominio.InvestigadorRegistrado;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class InvestigadorFactory {

    private final RepositorioInvestigadores repositorioInvestigadores;
    private final ApplicationEventPublisher eventPublisher;

    @org.springframework.beans.factory.annotation.Autowired
    public InvestigadorFactory(RepositorioInvestigadores repositorioInvestigadores,
                               ApplicationEventPublisher eventPublisher) {
        this.repositorioInvestigadores = repositorioInvestigadores;
        this.eventPublisher = eventPublisher;
    }

    public InvestigadorFactory(RepositorioInvestigadores repositorioInvestigadores) {
        this(repositorioInvestigadores, event -> {});
    }

    public Investigador crear(String nombreCompleto, String correoInstitucional, String grupoInvestigacion) {
        CorreoInstitucional correo = new CorreoInstitucional(correoInstitucional);

        if (repositorioInvestigadores.existeCorreo(correo.valor())) {
            throw new CorreoDuplicadoException(
                    "Ya existe un investigador registrado con el correo " + correo.valor());
        }

        Investigador investigador = new Investigador(null, nombreCompleto, correo, grupoInvestigacion);
        eventPublisher.publishEvent(new InvestigadorRegistrado(correo.valor(), Instant.now()));

        return investigador;
    }
}