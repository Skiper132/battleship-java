package com.battleship.excepciones;

/**
 * Excepcion que se lanza cuando se intenta posicionar un barco en una casilla que ya se encuentra ocupada
 */
public class CasillaYaOcupadaException extends Exception{
    public CasillaYaOcupadaException() {
        super("La casilla ya se encuentra ocupada");
    }
}
