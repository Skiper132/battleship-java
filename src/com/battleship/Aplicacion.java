package com.battleship;

import com.battleship.controlador.ControladorJuego;
import com.battleship.excepciones.*;
import com.battleship.vista.Pantalla;
import com.battleship.utilidades.Lector;

public class Aplicacion {

    ControladorJuego controlador;
    Pantalla pantalla;

    public Aplicacion() {
        this.controlador = ControladorJuego.getInstancia();
        this.pantalla = new Pantalla();
    }

    public void iniciarJuego() {
        // Bienvenida y creación de jugadores
        pantalla.mostrarBienvenida();
        String[] nombresJugadores = new String[2];

        for (int i = 0; i < 2; i++) {
            nombresJugadores[i] = pantalla.pedirNombreJugador(i);
            try {
                controlador.crearJugador(nombresJugadores[i]);
            } catch (JugadorExistenteException | LimiteJugadoresException e) {
                pantalla.mostrarError(e.getMessage());
                i--; // Permite al usuario ingresar el nombre nuevamente
            }
        }
        // Posicionamiento de los barcos
        for (String nombreJugador : nombresJugadores) {
            controlador.setJugadorActivoPorNombre(nombreJugador);
            while (!controlador.todosLosBarcosPosicionados()) {
                pantalla.mostrarMensajePosicionamiento();
                pantalla.mostrarTablero();
                pantalla.mostrarBarcosNoPosicionados();
                pantalla.pedirDatosBarcoAPosicionar();

                try {
                    controlador.posicionarBarco(Lector.getUltimoBarcoCargado(), Lector.getUltimaCasillaCargada(), Lector.getUltimaDireccionCargada());
                    pantalla.mostrarExitoPosicionamiento();
                } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
                    pantalla.mostrarError(e.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        new Aplicacion().iniciarJuego();
    }

}
