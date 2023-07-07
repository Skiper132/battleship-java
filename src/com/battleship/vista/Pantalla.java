package com.battleship.vista;

import com.battleship.controlador.ControladorJuego;
import com.battleship.utilidades.Lector;
public class Pantalla {
    //TODO: Implementar la vista
    public static void main(String[] args) {
        System.out.println("¡Bienvenido a Battleship! Para iniciar el juego, estableceremos los jugadores:");

        System.out.println("Jugador 1, por favor ingresa tu nombre:");
        String Jugador1 = Lector.cargarEntrada();
        System.out.println("Jugador 2, por favor ingresa tu nombre:");
        String Jugador2 = Lector.cargarEntrada();

        System.out.println("Creando jugadores...");
        ControladorJuego controladorJuego = new ControladorJuego(Jugador1, Jugador2);
        System.out.println("Jugadores creados.");
    }

    public static void buclePosicionarBarcos(String jugador) {
        
    }
}