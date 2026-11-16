package com.rica.investigadores_service.investigadores.aplicacion;

import com.rica.investigadores_service.investigadores.dominio.InvestigadorRegistrado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InvestigadorRegistradoListener {

    private static final Logger log = LoggerFactory.getLogger(InvestigadorRegistradoListener.class);

    @EventListener
    public void registrar(InvestigadorRegistrado evento) {
        log.info("Investigador registrado: {}", evento.correoInstitucional());
    }
}
