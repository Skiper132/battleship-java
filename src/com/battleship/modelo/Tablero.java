package com.battleship.modelo;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;
import com.battleship.excepciones.BarcoYaPosicionadoException;

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
            char fila = (char) ('A' + i); // A, B, C, D, E, F, G, H, I
            for (int j = 0; j < columnas; j++) {
                casillas[i][j] = new Casilla(fila, j + 1);
            }
        }
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


    public void posicionarBarco(Barco barco, Casilla casillaInicial, Direccion direccion)
            throws BarcoNoPosicionableException, BarcoFueraDeRangoException, BarcoYaPosicionadoException {

        if(barco.getEstado() == EstadoBarco.POSICIONADO) {
            throw new BarcoYaPosicionadoException();
        }

        Casilla[] casillasParaElBarco = new Casilla[barco.getLongitud()];
        Coordenada coordenadaActual = casillaInicial.getCoordenada();

        int incrementoFila = 0;
        int incrementoColumna = 0;

        // Ajustar el incremento de la coordenada en función de la dirección especificada
        switch (direccion) {
            case NORTE:
                incrementoColumna = -1;
                break;
            case SUR:
                incrementoColumna = 1;
                break;
            case ESTE:
                incrementoFila = 1;
                break;
            case OESTE:
                incrementoFila = -1;
                break;
        }

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
            coordenadaActual.setFila((char) (coordenadaActual.getFila() + incrementoFila));
            coordenadaActual.setColumna(coordenadaActual.getColumna() + incrementoColumna);
        }

        // Configurar las casillas para el barco
        barco.setCasillas(casillasParaElBarco);
    }

}
