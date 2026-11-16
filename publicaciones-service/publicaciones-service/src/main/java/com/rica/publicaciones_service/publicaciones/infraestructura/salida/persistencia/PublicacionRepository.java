package com.rica.publicaciones_service.publicaciones.infraestructura.salida.persistencia;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rica.publicaciones_service.publicaciones.dominio.Publicacion;

import java.util.List;

public interface PublicacionRepository extends MongoRepository<Publicacion, String> {

    List<Publicacion> findByInvestigadorCorreo(String investigadorCorreo);
    long countByInvestigadorCorreoAndAnio(String investigadorCorreo, Integer anio);

}
