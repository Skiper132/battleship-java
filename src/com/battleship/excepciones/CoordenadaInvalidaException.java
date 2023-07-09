package com.battleship.excepciones;

public class CoordenadaInvalidaException extends Exception{
    public CoordenadaInvalidaException() {
        super("La coordenada ingresada es inválida");
    }
}
