package com.battleship.excepciones;

public class LimiteJugadoresException extends Exception{
    public LimiteJugadoresException() {
        super("No se pueden crear más jugadores.");
    }
}
