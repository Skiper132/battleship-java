package com.battleship.excepciones;

public class BarcoNoPosicionableException extends Exception {
    public BarcoNoPosicionableException() {
        super("El barco no se puede posicionar en la casilla indicada");
    }
}
