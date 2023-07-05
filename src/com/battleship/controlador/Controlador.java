//Anyel
package com.battleship.controlador;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;
import com.battleship.excepciones.BarcoYaPosicionadoException;
import com.battleship.modelo.Barco;
import com.battleship.modelo.Casilla;
import com.battleship.modelo.Direccion;
import com.battleship.modelo.EstadoBarco;
import com.battleship.modelo.Tablero;

public class Controlador {
    public void posicionarBarco(Tablero tablero, Barco barco, Casilla casillaInicial, Direccion direccion) throws BarcoYaPosicionadoException {

    if(barco.getEstado() == EstadoBarco.POSICIONADO) {
        throw new BarcoYaPosicionadoException();
    }

    try {
        // Utilizar el método del Tablero para asignar las casillas al barco
        Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

        // Configurar las casillas para el barco
        barco.setCasillas(casillasParaElBarco);
    } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
        // Manejar las excepciones aquí
        System.out.println(e.getMessage());
    }
}

}
