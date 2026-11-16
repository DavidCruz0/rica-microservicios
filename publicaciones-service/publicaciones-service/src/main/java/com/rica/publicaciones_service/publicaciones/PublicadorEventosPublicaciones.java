package com.rica.publicaciones_service.publicaciones;

import java.util.Map;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.rica.publicaciones_service.publicaciones.dominio.Publicacion;

import tools.jackson.databind.ObjectMapper;

@Component
public class PublicadorEventosPublicaciones {
    static final String TOPICO_PUBLICACION_REGISTRADA = "publicaciones.registradas";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public PublicadorEventosPublicaciones(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publicacionRegistrada(Publicacion publicacion) {
        String contenido = objectMapper.writeValueAsString(Map.of(
                "id", publicacion.getId(),
                "investigadorCorreo", publicacion.getInvestigadorCorreo(),
                "titulo", publicacion.getTitulo(),
                "tipo", publicacion.getTipo(),
                "anio", publicacion.getAnio()));
        kafkaTemplate.send(TOPICO_PUBLICACION_REGISTRADA, publicacion.getInvestigadorCorreo(), contenido);
    }

}