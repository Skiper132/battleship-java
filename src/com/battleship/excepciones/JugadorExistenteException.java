package com.battleship.excepciones;

public class JugadorExistenteException extends Exception{
    public JugadorExistenteException() {
        super("El jugador ya existe.");
    }
}
