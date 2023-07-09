package com.battleship.modelo;

public enum Direccion {
    NORTE(-1, 0),
    SUR(1, 0),
    ESTE(0, 1),
    OESTE(0, -1);

    private final int desplazamientoFila;
    private final int desplazamientoColumna;

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
