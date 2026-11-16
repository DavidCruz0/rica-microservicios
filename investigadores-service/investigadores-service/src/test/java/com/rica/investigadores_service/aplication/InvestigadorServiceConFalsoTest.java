package com.rica.investigadores_service.aplication;

import org.junit.jupiter.api.Test;

import com.rica.investigadores_service.investigadores.aplicacion.InvestigadorFactory;
import com.rica.investigadores_service.investigadores.aplicacion.InvestigadorService;
import com.rica.investigadores_service.investigadores.dominio.Investigador;

import static org.assertj.core.api.Assertions.assertThat;

class InvestigadorServiceConFalsoTest {

    @Test
    void registraYRecuperaUnInvestigadorSinNingunaDependenciaDeSpringNiDeBaseDeDatos() {
        RepositorioInvestigadoresFalso repositorio = new RepositorioInvestigadoresFalso();
        InvestigadorFactory factory = new InvestigadorFactory(repositorio);
        InvestigadorService service = new InvestigadorService(
                repositorio,
                factory,
                (topico, clave, contenido) -> {
                });

        Investigador guardado = service.registrar("Ana Torres", "ana.torres@uptc.edu.co", "GIT-UPTC");

        assertThat(guardado.getId()).isNotNull();
        assertThat(service.buscarPorId(guardado.getId()).getNombreCompleto()).isEqualTo("Ana Torres");
    }
}
