package com.battleship.modelo;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    Casilla[][] casillas;
    List<Barco> barcos;

    /**
     * Constructor de la clase Tablero.
     */
    public Tablero() {
        int filas = 9;
        int columnas = 9;

        this.casillas = new Casilla[filas][columnas];
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                this.casillas[i][j] = new Casilla((char) ('A' + i), j + 1); // A1, A2, A3, ..., I9 
            }
        }
        this.barcos = new ArrayList<>();
    }

    /**
     * Devuelve los barcos del tablero.
     *
     * @return los barcos del tablero.
     */
    public List<Barco> getBarcos() {
        return barcos;
    }

    /**
     * Devuelve las casillas del tablero.
     *
     * @return las casillas del tablero.
     */
    public Casilla[][] getCasillas() {
        return casillas;
    }

    /**
     * Devuelve la casilla en la coordenada especificada.
     *
     * @param coordenada la coordenada de la casilla.
     * @return la casilla en la coordenada especificada.
     */

    public Casilla getCasilla(Coordenada coordenada) {
        int fila = coordenada.getFila() - 'A';
        int columna = coordenada.getColumna() - 1;
        return casillas[fila][columna];
    }

    /**
     * Asigna las casillas al barco en la coordenada especificada desplazándose en la dirección especificada.
     * <p>
     * @param barco el barco al que se le asignarán las casillas.
     * @param coordenadaInicial la coordenada inicial donde se asignará la primera casilla del barco.
     * @param desplazamientoFila el desplazamiento en la fila para asignar las casillas.
     * @param desplazamientoColumna el desplazamiento en la columna para asignar las casillas.
     * @return las casillas asignadas al barco.
     * @throws BarcoNoPosicionableException si el barco no se puede posicionar en la coordenada especificada.
     * @throws BarcoFueraDeRangoException si el barco se sale del tablero.
     */
    public Casilla[] asignarCasillasParaBarco(Barco barco, Coordenada coordenadaInicial, int desplazamientoFila, int desplazamientoColumna)
            throws BarcoNoPosicionableException, BarcoFueraDeRangoException {

        Casilla[] casillasParaElBarco = new Casilla[barco.getLongitud()];
        // coordenadaActual es una copia de coordenadaInicial, sirve como puntero para recorrer las casillas
        Coordenada coordenadaActual = new Coordenada(coordenadaInicial.getFila(), coordenadaInicial.getColumna());

        // Asignar las casillas al barco
        for (int i = 0; i < barco.getLongitud(); i++) {
            if (coordenadaActual.getFila() < 'A' || coordenadaActual.getFila() > 'I' || coordenadaActual.getColumna() < 1 || coordenadaActual.getColumna() > 9) {
                throw new BarcoFueraDeRangoException();
            }
            if (getCasilla(coordenadaActual).getEstado() == EstadoCasilla.OCUPADA) {
                throw new BarcoNoPosicionableException();
            }
            casillasParaElBarco[i] = getCasilla(coordenadaActual);

            // Actualizar la coordenada para la próxima iteración
            coordenadaActual.setFila((char) (coordenadaActual.getFila() + desplazamientoFila));
            coordenadaActual.setColumna(coordenadaActual.getColumna() + desplazamientoColumna);
        }

        return casillasParaElBarco;
    }

    /**
     * Agrega un barco a la lista de barcos del tablero.
     *
     * @param barco el barco a agregar.
     */
    public void agregarBarco(Barco barco) {
        barcos.add(barco);
    }

    
}
