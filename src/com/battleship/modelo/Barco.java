package com.battleship.modelo;

import com.battleship.excepciones.BarcoNoPosicionableException;

/**
 * Representa un barco en el tablero.
 */
public class Barco {
    private Casilla[] casillas;
    private EstadoBarco estado;
    /**
     * Crea un barco de tamaño especificado.
     *
     * @param tamano el tamaño del barco.
     */
    public Barco(int longitud) {
        this.casillas = new Casilla[longitud];
    }

    /**
     * Devuelve las casillas que ocupa el barco.
     *
     * @return las casillas que ocupa el barco.
     */
    public Casilla[] getCasillas() {
        return casillas;
    }

    /**
     * Devuelve la cantidad de casillas que ocupa el barco.
     *
     * @return la cantidad de casillas que ocupa el barco.
     */
    public int getLongitud() {
        return casillas.length;
    }

    /**
     * Devuelve el estado del barco.
     * @return el estado del barco.
     */
    public EstadoBarco getEstado() {
        return estado;
    }

    /**
     * Recorre un array de casillas y comprueba que todas estén vacías, si es así, 
     * asigna el array de casillas al barco y cambia el estado del barco a POSICIONADO.
     *
     * @param casillas
     * @throws BarcoNoPosicionableException
     */
    public void setCasillas(Casilla[] casillas) throws BarcoNoPosicionableException {
        for (Casilla casilla : casillas) {
            if (casilla.getEstado() != EstadoCasilla.VACIA) {
                throw new BarcoNoPosicionableException();
            }
        }

        this.casillas = casillas;
        for (Casilla casilla : casillas) {
            casilla.setEstado(EstadoCasilla.OCUPADA);;
        }
        this.estado = EstadoBarco.POSICIONADO;
    }
}