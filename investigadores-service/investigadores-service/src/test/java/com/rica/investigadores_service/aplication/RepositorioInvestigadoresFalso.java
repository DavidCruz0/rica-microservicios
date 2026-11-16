package com.rica.investigadores_service.aplication;

import com.rica.investigadores_service.investigadores.aplicacion.RepositorioInvestigadores;
import com.rica.investigadores_service.investigadores.dominio.Investigador;
import java.util.*;

class RepositorioInvestigadoresFalso implements RepositorioInvestigadores {
    private final Map<Long, Investigador> almacen = new LinkedHashMap<>();
    private long siguienteId = 1;

    @Override
    public List<Investigador> listarTodos() {
        return new ArrayList<>(almacen.values());
    }

    @Override
    public Optional<Investigador> buscarPorId(Long id) {
        return Optional.ofNullable(almacen.get(id));
    }

    @Override
    public boolean existeCorreo(String correoInstitucional) {
        return almacen.values().stream()
                .anyMatch(i -> i.getCorreoInstitucional().valor().equals(correoInstitucional));
    }

    @Override
    public Investigador guardar(Investigador investigador) {
        if (investigador.getId() == null) {
            investigador.setId(siguienteId++);
        }
        almacen.put(investigador.getId(), investigador);
        return investigador;
    }

    @Override
    public Optional<Investigador> buscarPorCorreo(String correoInstitucional) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorCorreo'");
    }

    @Override
    public void eliminar(Investigador investigador) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
}
