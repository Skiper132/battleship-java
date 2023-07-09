package com.battleship.modelo;

public enum ResultadoAtaque {
    ACIERTO("¡Has acertado!"),
    FALLA("¡Has fallado!");

    private final String mensaje;

    ResultadoAtaque(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return this.mensaje;
    }
}
