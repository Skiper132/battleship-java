package com.battleship.modelo;

public enum EstadoCasilla {
    VACIA('-'),
    OCUPADA('▉'),
    ATACADA('X');

    private final char simbolo;

    EstadoCasilla(char simbolo) {
        this.simbolo = simbolo;
    }

    public char getSimbolo() {
        return this.simbolo;
    }
}
