package com.rica.publicaciones_service.publicaciones;

public class LimiteAnualExcedidoException extends RuntimeException {
    public LimiteAnualExcedidoException(String mensaje) {
        super(mensaje);
    }
}
