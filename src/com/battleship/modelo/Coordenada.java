package com.battleship.modelo;

/**
 * Representa una coordenada en el tablero.
 */
public class Coordenada {
    private char fila;
    private int columna;

    public Coordenada(char fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public char getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int i) {
        this.columna = i;
    }

    public void setFila(char c) {
        this.fila = c;
    }
}