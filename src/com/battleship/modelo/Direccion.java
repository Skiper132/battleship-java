package com.battleship.modelo;

/**
 * Representa las direcciones posibles en las que se puede posicionar un barco.
 * Cada dirección tiene un incremento de fila y columna asociado, que se utiliza para
 * calcular las coordenadas de las casillas que ocupará el barco.
 */
public enum Direccion {
    NORTE(0, -1),
    SUR(0, 1),
    ESTE(1, 0),
    OESTE(-1, 0);

    private final int desplazamientoFila;
    private final int desplazamientoColumna;

    // Constructor privado para que no se pueda instanciar desde fuera.
    private Direccion(int desplazamientoFila, int desplazamientoColumna) {
        this.desplazamientoFila = desplazamientoFila;
        this.desplazamientoColumna = desplazamientoColumna;
    }

    public int getDesplazamientoFila() {
        return desplazamientoFila;
    }

    public int getDesplazamientoColumna() {
        return desplazamientoColumna;
    }

}
