package com.battleship.excepciones;

/**
 * Excepción que se lanza cuando se intenta atacar una casilla que ya fue atacada.
 */
public class CasillaYaAtacadaException extends Exception {
        public CasillaYaAtacadaException() {
            super("La casilla ya fue atacada. Por favor, seleccione otra.");
        }
}
