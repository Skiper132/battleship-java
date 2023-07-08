package com.battleship.vista;

import com.battleship.controlador.ControladorJuego;
import com.battleship.utilidades.Lector;
public class Pantalla {
    private static ControladorJuego controlador = ControladorJuego.getInstancia();
    public static void main(String[] args) {
        System.out.println("¡Bienvenido a Battleship! Para iniciar el juego, estableceremos los jugadores:");

        System.out.println("Jugador 1, por favor ingresa tu nombre:");
        String jugador1 = Lector.cargarEntrada();
        controlador.crearJugador(jugador1);
        System.out.println("¡Bienvenido " + jugador1 + "!");

        System.out.println("Jugador 2, por favor ingresa tu nombre:");
        String jugador2 = Lector.cargarEntrada();
        controlador.crearJugador(jugador2);
        System.out.println("¡Bienvenido " + jugador2 + "!\n");
        // Colocamos los barcos de los jugadores en un bucle
        for (int i = 0; i < 2; i++) {
            controlador.setJugadorActivoPorNombre(i == 0 ? jugador1 : jugador2); 
            System.out.println("¡Ahora vamos a posicionar los barcos! " + controlador.getJugadorActivo().getNombre() + ", por favor posiciona tus barcos.");
            buclePosicionarBarcos();
        }
    }

    public static void cambiarJugadorActivo(String nombreJugador) {
        // Cambia el jugador activo al jugador con el nombre especificado
        controlador.setJugadorActivoPorNombre(nombreJugador);
    }

    public static void imprimirTablero() {
        System.out.println(controlador.mostrarTablero());
    }

    public static void imprimirBarcosNoPosicionados() {
        System.out.println(controlador.mostrarBarcosNoPosicionados());
    }
    public static void buclePosicionarBarcos() {
        while (!controlador.todosLosBarcosPosicionados()) {
            imprimirTablero();

            System.out.println("Listado de Barcos para posicionar en tu tablero:");
            imprimirBarcosNoPosicionados();

            System.out.println("Escribe el nombre del barco que quieras posicionar:");
            String nombreBarco = Lector.cargarBarco();

            System.out.println("Elige la casilla donde quieras posicionar el barco y su dirección \n Por ejemplo: 'B5 N' Intentará posicionar el barco a partir de la casilla B5 en dirección norte (hacia arriba):");
            Lector.cargarPosicionamientoBarco();

            String nombreCasilla = Lector.getUltimaCasillaCargada().getCoordenada().toString();
            String nombreDireccion = Lector.getUltimaDireccionCargada().toString();
            System.out.printf("Intentando posicionar el barco %s en la casilla %s en dirección %s...\n", nombreBarco, nombreCasilla, nombreDireccion);
            Boolean barcoPosicionado = controlador.posicionarBarco(controlador.getBarcoPorNombre(nombreBarco), Lector.getUltimaCasillaCargada(), Lector.getUltimaDireccionCargada());
            if (barcoPosicionado) {
                System.out.println("Barco posicionado correctamente.");
            } else {
                System.out.println("No se pudo posicionar el barco debido a un error, por favor intenta de nuevo.");
            }
        }
    }
}