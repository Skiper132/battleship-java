package com.battleship.controlador;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;
import com.battleship.excepciones.BarcoYaPosicionadoException;
import com.battleship.modelo.*;

public class Controlador {
    public void posicionarBarco(Jugador jugador, Barco barco, Casilla casillaInicial, Direccion direccion) throws BarcoYaPosicionadoException {

        if(barco.getEstado() == EstadoBarco.POSICIONADO) {
            throw new BarcoYaPosicionadoException();
        }

        Tablero tablero = jugador.getTablero();

        try {
            // Utilizar el método del Tablero para asignar las casillas al barco
            Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

            // Configurar las casillas para el barco
            barco.setCasillas(casillasParaElBarco);
            // Añadir el barco a la lista de barcos del tablero
            tablero.agregarBarco(barco);
        } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
            // Manejar las excepciones aquí
            System.out.println(e.getMessage());
        }
    }

}
