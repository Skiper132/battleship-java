package com.battleship.excepciones;

public class BarcoNoExistenteException extends Exception {
    public BarcoNoExistenteException() {
        super("El barco ingresado no existe");
    }
}
