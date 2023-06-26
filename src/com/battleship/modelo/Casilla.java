package com.battleship.modelo;

import com.battleship.excepciones.CasillaYaAtacadaException;

/**
 * Representa una casilla en el tablero.
 */
public class Casilla {
    private Coordenada coordenada;
    private EstadoCasilla estado;

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
     * Establece el estado de la casilla.
     *
     * @param estado el estado de la casilla.
     */
    public void setEstado(EstadoCasilla estado) {
        this.estado = estado;
    }

    // TODO: IMPLEMENTAR
    public ResultadoAtaque atacar() throws CasillaYaAtacadaException {
        if (estado == EstadoCasilla.ATACADA) {
            throw new CasillaYaAtacadaException();
        } else {
            estado = EstadoCasilla.ATACADA;
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

