package com.battleship.excepciones;
/**
 * Excepcion que se lanza cuando se intenta posicionar un barco fuera del rango del tablero
 */
public class BarcoFueraDeRangoException extends Exception{
    public BarcoFueraDeRangoException() {
        super("El barco se encuentra fuera del rango del tablero");
    }
}
