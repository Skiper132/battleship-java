package com.battleship.modelo;

public class Tablero {
    Casilla[][] casillas;

    /**
     * Crear un tablero de 9x9 y lo inicializa con casillas.
     */
    public Tablero() {
        int filas = 9;
        int columnas = 9;
        casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            char fila = (char) ('A' + i);
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(fila, j + 1);
            }
        }
    }
}
