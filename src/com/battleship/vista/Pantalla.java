package com.battleship.vista;

import com.battleship.controlador.ControladorJuego;
import com.battleship.modelo.ResultadoAtaque;
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

    public void imprimirExitoCreacionJugador(String nombreJugador) {
        System.out.println ("Jugador " + nombreJugador + " creado con éxito.");
    }

    public void imprimirMensajeInicioPosicionamiento() {
        System.out.println("\n" + controlador.getJugadorActivo().getNombre() + ", posiciona tus barcos:\n");
    }

    public void mostrarTurnoJugador() {
        System.out.println("\nTurno de " + controlador.getJugadorActivo().getNombre());
    }

    public void imprimirTablero() {
        System.out.println(controlador.mostrarTablero());
    }
    public void imprimirTableroEnemigo(){
        System.out.println(controlador.mostrarTableroEnemigo());
    }
    public void imprimirBarcosNoPosicionados() {
        System.out.println("Lista de barcos por posicionar:\n");
        System.out.println(controlador.mostrarBarcosNoPosicionados());
    }

    public void pedirDatosBarcoAPosicionar() {
        System.out.println("\nPor favor, introduce el nombre del barco que deseas posicionar:");
        Lector.cargarBarco();
        System.out.println("Introduce la casilla inicial y la dirección (N, S, E, O) en donde deseas posicionar tu " + Lector.getUltimoBarcoCargado().getNombre() + " separado por un espacio. Ejemplo: A1 N");
        Lector.cargarPosicionamientoBarco();
        System.out.println("\nBarco: " + Lector.getUltimoBarcoCargado().getNombre() + " | Casilla inicial: " + Lector.getUltimaCasillaCargada().getCoordenada().toString() + " | Dirección: " + Lector.getUltimaDireccionCargada().name());
        System.out.println("Posicionando barco...");
    }

    public void imprimirError(String mensaje) {
        System.out.println("\nError: " + mensaje + "\n");
    }

    public void imprimirExitoPosicionamiento() {
        System.out.println("\n¡Barco posicionado con éxito!\n");
    }
    public void imprimirResultadoAtaque(ResultadoAtaque resultadoAtaque) {
        System.out.println(controlador.mostrarResultadoAtaque(resultadoAtaque));
    }

    public void ventanaPosicionamiento() {
        imprimirMensajeInicioPosicionamiento();
        imprimirTablero();
        imprimirBarcosNoPosicionados();
    }

    public void imprimirOpcionesDePosicionamiento() {
        System.out.println("""
                ¿Cómo quieres posicionar tus barcos?
                [1] Manualmente
                [2] Aleatoriamente
                """);
    }

    public void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    public void ventanaTableros() {
        System.out.println("\nTu tablero:\n");
        imprimirTablero();
        System.out.println("\nTablero enemigo (" + controlador.getEnemigo().getNombre() + "):\n");
        imprimirTableroEnemigo();
    }

    public String pedirCasilla() {
        System.out.println("Introduce la casilla que deseas atacar (Ejemplo: A1) o escribe 'EXIT' para rendirte:)");
        return Lector.cargarEntrada();
    }

    public void imprimirGanador() {
        System.out.println("\n¡" + controlador.getEnemigo().getNombre() + " ha ganado la partida!");;
        System.out.println("Imprimiendo ubicaciones de barcos de los jugadores...");
    }

    public void imprimirBarcosPosicionados(){
        System.out.println(controlador.mostrarBarcosPosicionados());
    }

    public void imprimirJugador() {
        System.out.println(controlador.getJugadorActivo().getNombre());
    }

    public void ventanaInformacionFinal() {
        imprimirJugador();
        imprimirTablero();
        imprimirBarcosPosicionados();
    }

    public void mensajeSalida() {
        System.out.println("\n¡Gracias por jugar a Batalla Naval!");
        System.out.println("Presiona ENTER para salir...");
    }
}
