package com.battleship.vista;

import com.battleship.modelo.Casilla;
import com.battleship.modelo.Coordenada;
import com.battleship.modelo.Tablero;

public class VistaTablero {

    public static void mostrarTablero(Tablero tablero) {
        Casilla[][] casillas = tablero.getCasillas();

        // Imprime el encabezado de las columnas del tablero
        System.out.print("  ");
        for (int i = 0; i < casillas[0].length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        // Imprime las filas del tablero
        for (int i = 0; i < casillas.length; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < casillas[i].length; j++) {
                Coordenada coordenada = new Coordenada((char) ('A' + i), j + 1);
                Casilla casilla = tablero.getCasilla(coordenada);
                System.out.print(obtenerSimboloCasilla(casilla) + " ");
            }
            System.out.println();
        }
    }

    private static char obtenerSimboloCasilla(Casilla casilla) {
        switch (casilla.getEstado()) {
            case VACIA:
                return '-';
            case OCUPADA:
                return '▉';
            case ATACADA:
                return 'X';
            default:
                return '?';
        }
    }
}
