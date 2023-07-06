package com.battleship.modelo;

import java.util.HashMap;

public class Jugador {
    private String nombre;
    private HashMap<String, Barco> barcos;


    public Jugador(String nombre) {
        this.nombre = nombre;
        this.barcos = new HashMap<>();
        crearBarcos();
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * Crea los barcos del jugador, funciona con el enum NombreBarco para crear los barcos, utiliza 2 ciclos
     * anidados para crear la cantidad de barcos especificada en el enum y los agrega al HashMap barcos.
     */
    private void crearBarcos() {
        for (NombreBarco nombreBarco : NombreBarco.values()) {
            for (int i = 0; i < nombreBarco.cantidad; i++) {
                String nombreBarcoCompleto = NombreBarco.obtenerNombre(nombreBarco.longitud, i+1);
                barcos.put(nombreBarcoCompleto, new Barco(nombreBarco.longitud, i+1));
            }
        }
    }

    /**
     * Devuelve un barco del jugador según su nombre.
     * 
     * @param nombre el nombre del barco.
     * @return el barco.
     */
    public Barco obtenerBarco(String nombre) {
        return barcos.get(nombre);
    }

    /**
     * Recorre todos los barcos del jugador y devuelve true si todos están hundidos.
     * 
     * @return true si todos los barcos están hundidos, false en caso contrario.
     */
    public boolean todosLosBarcosHundidos() {
        for (Barco barco : barcos.values()) {
            if (barco.getEstado() != EstadoBarco.HUNDIDO) {
                return false;
            }
        }
        return true;
    }
}
