package com.battleship.modelo;

/**
 * Representa un barco en el tablero.
 */
public class Barco {
    private Casilla[] casillas;
    private EstadoBarco estado;
    private String nombre;
    /**
     * Crea un barco de tamaño especificado.
     *
     * @param tamano el tamaño del barco.
     */
    public Barco(int longitud) {
        this.casillas = new Casilla[longitud];
        this.estado = EstadoBarco.NO_POSICIONADO;
        switch (longitud) {
            case 2:
                this.nombre = "Lancha";
                break;
            case 3:
                this.nombre = "Submarino";
                break;
            case 4:
                this.nombre = "Acorazado";
                break;
            default:
                this.nombre = "Barco";
                break;
        }
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
     * Captura un array de casillas y lo asigna al barco.
     */
    public void setCasillas(Casilla[] casillas) {
        this.casillas = casillas;
        for (Casilla casilla : casillas) {
            casilla.setEstado(EstadoCasilla.OCUPADA);;
        }
        this.estado = EstadoBarco.POSICIONADO;
    }
}