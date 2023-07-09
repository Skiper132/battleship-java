package com.battleship.excepciones;
/**
 * Excepción que se lanza cuando se intenta posicionar un barco en una casilla que no es válida.
 */
public class BarcoNoPosicionableException extends Exception {
    public BarcoNoPosicionableException() {
        super("El barco choca con otro barco.");
    }
}
