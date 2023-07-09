package com.battleship.modelo;

/**
 * Representa un barco en el tablero.
 */
public class Barco {
    private String nombre;
    private Casilla[] casillas;
    private EstadoBarco estado;
    /**
     * Crea un barco de tamaño especificado.
     *
     * @param tamano el tamaño del barco.
     * @param contadorBarco el número del barco. (1 o 2 para Lanchas, 1 para Submarinos y Acorazados)
     */
    public Barco(int longitud, int contadorBarco) {
        this.casillas = new Casilla[longitud];
        this.estado = EstadoBarco.NO_POSICIONADO;
        this.nombre = NombreBarco.obtenerNombre(longitud, contadorBarco);
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
     *
     * @return el estado del barco.
     */
    public EstadoBarco getEstado() {
        return estado;
    }

    /**
     * Devuelve el nombre del barco.
     *
     * @return el nombre del barco.
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * Captura un array de casillas y lo asigna al barco.
     */
    public void setCasillas(Casilla[] casillas) {
        this.casillas = casillas;
        for (Casilla casilla : casillas) {
            casilla.setEstado(EstadoCasilla.OCUPADA);
            // Asigna el barco a cada casilla, de esta forma cada casilla sabe a qué barco pertenece.
            casilla.setBarco(this);
        }
        this.estado = EstadoBarco.POSICIONADO;
    }

    /**
     * Recorre las casillas del barco y si todas están atacadas, cambia el estado del barco a HUNDIDO.
     */
    public void recibirAtaque() {
        boolean todasAtacadas = true;
        for (Casilla casilla : casillas) {
            if (casilla.getEstado() != EstadoCasilla.ATACADA) {
                todasAtacadas = false;
                break;
            }
        }
        if (todasAtacadas) {
            this.estado = EstadoBarco.HUNDIDO;
        }
    }

}