package com.battleship.modelo;

public class Jugador {
    private String nombre;
    private Tablero tableroPropio;
    private Tablero tableroEnemigo;
    Barco[] barcos;

    Jugador(String nombre) {
        this.nombre = nombre;
        this.tableroPropio = new Tablero();
        this.tableroEnemigo = new Tablero();
        this.barcos = new Barco[5];
        this.barcos[0] = new Barco(4);
        this.barcos[1] = new Barco(3);
        this.barcos[2] = new Barco(2);
        this.barcos[3] = new Barco(2);
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTableroPropio() {
        return tableroPropio;
    }

    public Tablero getTableroEnemigo() {
        return tableroEnemigo;
    }

    public Barco[] getBarcos() {
        return barcos;
    }
}
