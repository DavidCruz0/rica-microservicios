package com.rica.investigadores_service.plataforma;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {
    @GetMapping("/api/status")
    public Map<String, String> status(){
        return Map.of(
            "proyecto","RICA-MicroServicios",
            "estado", "operativo"
        );
    }
}
