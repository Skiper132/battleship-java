package com.battleship.excepciones;

public class DireccionInvalidaException extends Exception {
    public DireccionInvalidaException() {
        super("La dirección ingresada es inválida");
    }
}
