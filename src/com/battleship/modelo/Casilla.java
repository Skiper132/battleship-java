package com.battleship.modelo;

import com.battleship.excepciones.CasillaYaAtacadaException;

/**
 * Representa una casilla en el tablero.
 */
public class Casilla {
    private Coordenada coordenada;
    private EstadoCasilla estado;
    private Barco barco;
    private char simbolo;

    /**
     * Crea una casilla en la posición especificada, crea una coordenada con la fila y columna especificadas y
     * establece el estado de la casilla a VACIA.
     *
     * @param fila    la fila de la casilla.
     * @param columna la columna de la casilla.
     */
    public Casilla(char fila, int columna) {
        this.coordenada = new Coordenada(fila, columna);
        this.estado = EstadoCasilla.VACIA;
        this.barco = null;
        this.simbolo = estado.getSimbolo();
    }

    /**
     * Devuelve la coordenada de la casilla.
     *
     * @return la coordenada de la casilla.
     */
    public Coordenada getCoordenada() {
        return coordenada;
    }

    /**
     * Devuelve el estado de la casilla.
     *
     * @return el estado de la casilla.
     */
    public EstadoCasilla getEstado() {
        return this.estado;
    }

    /**
     * Devuelve el barco de la casilla, si no hay barco devuelve null.
     *
     * @return el barco de la casilla.
     */
    public Barco getBarco() {
        return barco;
    }

    /**
     * Devuelve el símbolo de la casilla.
     *
     * @return el símbolo de la casilla.
     */
    public char getSimbolo() {
        return simbolo;
    }

    /**
     * Establece el estado de la casilla.
     *
     * @param estado el estado de la casilla.
     */
    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    /**
     * Establece el barco de la casilla.
     *
     * @param barco el barco de la casilla.
     */
    public void setBarco(Barco barco) {
        this.barco = barco;
    }

    /**
     * Establece el símbolo de la casilla.
     *
     * @param simbolo el símbolo de la casilla.
     */
    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }

    /**
     * Ataca la casilla y devuelve el resultado del ataque.
     *
     * @return el resultado del ataque.
     * @throws CasillaYaAtacadaException si la casilla ya está atacada.
     */
    public ResultadoAtaque atacar() throws CasillaYaAtacadaException {
        EstadoCasilla estadoActual = this.estado;
        if (estadoActual == EstadoCasilla.ATACADA) {
            throw new CasillaYaAtacadaException();
        }
        this.estado = EstadoCasilla.ATACADA;

        if (estadoActual == EstadoCasilla.OCUPADA) {
            return ResultadoAtaque.ACIERTO;
        } else {
            this.simbolo = '○';
            return ResultadoAtaque.FALLA;
        }
    }
}

