package com.battleship.modelo;

public class Jugador {
    private String nombre;
    private Tablero tablero;
    private Barco[] barcos;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
        this.barcos = new Barco[4];
        barcos[0] = new Barco(2, 1);
        barcos[1] = new Barco(2, 2);
        barcos[2] = new Barco(3, 1);
        barcos[3] = new Barco(4, 1);
    }

    public String getNombre() {
        return nombre;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Barco[] getBarcos() {
        return barcos;
    }

    /**
     * Verifica si todos los barcos del jugador están hundidos.
     *
     * @return true si todos los barcos están hundidos, false en caso contrario.
     */
    public boolean todosHundidos() {
        for (Barco barco : barcos) {
            if (barco.getEstado() != EstadoBarco.HUNDIDO) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica si todos los barcos del jugador están posicionados.
     * 
     * @return true si todos los barcos están posicionados, false en caso contrario.
     */
    public boolean todosPosicionados() {
        for (Barco barco : barcos) {
            if (barco.getEstado() != EstadoBarco.POSICIONADO) {
                return false;
            }
        }
        return true;
    }
}
