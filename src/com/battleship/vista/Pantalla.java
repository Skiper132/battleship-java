package com.battleship.vista;

import com.battleship.controlador.ControladorJuego;
import com.battleship.utilidades.Lector;

public class Pantalla {

    ControladorJuego controlador;

    public Pantalla() {
        this.controlador = ControladorJuego.getInstancia();
    }

    public void mostrarBienvenida() {
        System.out.println("\n¡Bienvenido a Batalla Naval!");
        System.out.println("Por favor, introduce el nombre de los jugadores...\n");
    }

    public String pedirNombreJugador(int numeroJugador) {
        System.out.print("Jugador " + numeroJugador + ": ");
        return Lector.cargarEntrada();
    }

    public void mostrarExitoCreacionJugador(String nombreJugador) {
        System.out.println ("Jugador " + nombreJugador + " creado con éxito.");
    }

    public void mostrarTurnoJugador() {
        System.out.println("\nTurno de " + controlador.getJugadorActivo().getNombre());
    }

    public void mostrarTablero() {
        System.out.println(controlador.mostrarTablero());
    }

    public void mostrarBarcosNoPosicionados() {
        System.out.println("Lista de barcos por posicionar:\n");
        System.out.println(controlador.mostrarBarcosNoPosicionados());
    }

    public void mostrarMensajePosicionamiento(String nombreJugador) {
        System.out.println("\n" + nombreJugador + ", posiciona tus barcos:\n");
    }

    public void pedirDatosBarcoAPosicionar() {
        System.out.println("\nPor favor, introduce el nombre del barco que deseas posicionar:");
        Lector.cargarBarco();
        System.out.println("Introduce la casilla inicial y la dirección (N, S, E, O) en donde deseas posicionar tu " + Lector.getUltimoBarcoCargado().getNombre() + " separado por un espacio. Ejemplo: A1 N");
        Lector.cargarPosicionamientoBarco();
        System.out.println("\nBarco: " + Lector.getUltimoBarcoCargado().getNombre() + " | Casilla inicial: " + Lector.getUltimaCasillaCargada().getCoordenada().toString() + " | Dirección: " + Lector.getUltimaDireccionCargada().name());
        System.out.println("Posicionando barco...");
    }

    public void mostrarError(String mensaje) {
        System.out.println("\nError: " + mensaje + "\n");
    }

    public void mostrarExitoPosicionamiento() {
        System.out.println("\n¡Barco posicionado con éxito!\n");
    }

    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
