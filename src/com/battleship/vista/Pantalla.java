package com.battleship.vista;

import com.battleship.controlador.Controlador;
import com.battleship.modelo.Casilla;
import com.battleship.modelo.Tablero;
public class Pantalla {
    //TODO: Implementar la vista
    public static void main(String[] args) {
        Controlador controlador = new Controlador();
        
    }

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
                // Ya no necesitamos obtener la coordenada ni buscar la casilla en el tablero
                Casilla casilla = casillas[i][j];
                // Obtenemos el símbolo de la casilla y lo imprimimos
                System.out.print(casilla.getSimbolo() + " ");
            }
            System.out.println();
        }
    }
}