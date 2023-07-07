package com.battleship.utilidades;

import com.battleship.excepciones.CoordenadaInvalidaException;
import com.battleship.modelo.Coordenada;

/**
 * Validador contiene métodos para validar entradas de datos.
 */
public class Validador {
    public static Coordenada convertirCoordenada(String coordenada) throws CoordenadaInvalidaException {
        if (coordenada.length() != 2) {
            throw new CoordenadaInvalidaException();
        }
        char letra = coordenada.charAt(0);
        int numero = Integer.parseInt(coordenada.substring(1));
        if (letra < 'A' || letra > 'J' || numero < 1 || numero > 10) {
            throw new CoordenadaInvalidaException();
        }
        return new Coordenada(letra, numero);
    }
}

