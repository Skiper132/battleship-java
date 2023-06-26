package com.battleship.modelo;

import com.battleship.excepciones.CasillaYaAtacadaException;

/**
 * Representa una casilla en el tablero.
 */
public class Casilla {
    private enum Estado { VACIA, OCUPADA, ATACADA }

    Coordenada coordenada;
    Estado estado;

    public Casilla(char fila, int columna) {
        coordenada = new Coordenada();
        coordenada.fila = fila;
        coordenada.columna = columna;
        estado = Estado.VACIA;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public boolean esAtacada() {
        return estado == Estado.ATACADA;
    }

    public boolean esOcupada() {
        return estado == Estado.OCUPADA;
    }

    public ResultadoAtaque atacar() throws CasillaYaAtacadaException {
        if (estado == Estado.ATACADA) {
            throw new CasillaYaAtacadaException();
        } else {
            estado = Estado.ATACADA;
            return ResultadoAtaque.ACIERTO;
        }
    }

    public void ocupar() throws CasillaYaOcupadaException {
        if (estado == Estado.OCUPADA) {
            throw new CasillaYaOcupadaException();
        } else {
            estado = Estado.OCUPADA;
        }
    }
}

