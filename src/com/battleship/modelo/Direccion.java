package com.battleship.modelo;

/**
 * Representa las direcciones posibles en las que se puede posicionar un barco.
 * Cada dirección tiene un incremento de fila y columna asociado, que se utiliza para
 * calcular las coordenadas de las casillas que ocupará el barco.
 */
public enum Direccion {
    NORTE(-1, 0),
    SUR(1, 0),
    ESTE(0, 1),
    OESTE(0, -1);

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
