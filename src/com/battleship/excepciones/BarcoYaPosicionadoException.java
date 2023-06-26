package com.battleship.excepciones;
/**
 * Excepcion que se lanza cuando se intenta posicionar un barco que ya se encuentra posicionado
 */
public class BarcoYaPosicionadoException extends Exception{
    public BarcoYaPosicionadoException() {
        super("El barco ya se encuentra posicionado");
    }
}
