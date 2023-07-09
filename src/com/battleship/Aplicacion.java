//Aldo Apicella, Edwin Ureña, Luis Montenegro, Anyel Zhang
package com.battleship;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;
import com.battleship.excepciones.CasillaYaAtacadaException;
import com.battleship.excepciones.JugadorExistenteException;
import com.battleship.excepciones.LimiteJugadoresException;

import com.battleship.controlador.ControladorJuego;
import com.battleship.vista.Pantalla;
import com.battleship.utilidades.Lector;

public class Aplicacion {

    ControladorJuego controlador;
    Pantalla pantalla;

    public Aplicacion() {
        this.controlador = ControladorJuego.getInstancia();
        this.pantalla = new Pantalla();
    }

    // Pausa la ejecución del programa durante un tiempo especificado en milisegundos.
    public static void esperar(int milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void iniciarJuego() throws InterruptedException {
        // Bienvenida y creación de jugadores
        pantalla.mostrarBienvenida();
        String[] nombresJugadores = new String[2];

        for (int i = 0; i < 2; i++) {
            nombresJugadores[i] = pantalla.pedirNombreJugador(i + 1);
            try {
                controlador.crearJugador(nombresJugadores[i]);
            } catch (JugadorExistenteException | LimiteJugadoresException e) {
                pantalla.imprimirError(e.getMessage());
                i--; // Permite al usuario ingresar el nombre nuevamente
            }
        }
        // Posicionamiento de los barcos
        for (String nombreJugador : nombresJugadores) {
            controlador.setJugadorActivoPorNombre(nombreJugador);

            pantalla.imprimirMensajeInicioPosicionamiento();
            pantalla.imprimirOpcionesDePosicionamiento();

            while (!controlador.getJugadorActivo().todosLosBarcosPosicionados()) {

                String opcion = Lector.cargarEntrada();
                switch (opcion) {
                    case "1":
                        while (!controlador.getJugadorActivo().todosLosBarcosPosicionados()) {
                            esperar(2000);
                            pantalla.limpiarPantalla();

                            pantalla.ventanaPosicionamiento();
                            pantalla.pedirDatosBarcoAPosicionar();

                            try {
                                controlador.posicionarBarco(Lector.getUltimoBarcoCargado(), Lector.getUltimaCasillaCargada(), Lector.getUltimaDireccionCargada());
                                pantalla.imprimirExitoPosicionamiento();
                            } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
                                pantalla.imprimirError(e.getMessage());
                            }
                        }
                        break;
                    case "2":
                        controlador.posicionarBarcosAleatoriamente();
                        break;
                    default:
                        pantalla.imprimirError("Opción inválida.");
                }
            }
        }

        // Se establece el primer jugador creado como activo antes de iniciar
        controlador.setJugadorActivoPorNombre(nombresJugadores[0]);

        // Inicio del juego
        while(!controlador.verificarVictoria()) {
            esperar(2000);
            pantalla.limpiarPantalla();

            pantalla.mostrarTurnoJugador();
            pantalla.ventanaTableros();

            String entrada = pantalla.pedirCasilla();

            if (entrada.equalsIgnoreCase("EXIT")){
                controlador.autoDestruirBarcos();
            } else {
                Lector.cargarCasilla(entrada);
                try {
                    pantalla.imprimirResultadoAtaque(controlador.atacarCasilla(controlador.getEnemigo(), Lector.getUltimaCasillaCargada()));
                    controlador.cambiarJugadorActual();
                } catch (CasillaYaAtacadaException e) {
                    pantalla.imprimirError(e.getMessage());
                }
            }
        }

        // Fin del juego
        pantalla.imprimirGanador();
        for (String nombreJugador : nombresJugadores) {
            controlador.setJugadorActivoPorNombre(nombreJugador);
            pantalla.ventanaInformacionFinal();
            controlador.cambiarJugadorActual();
        }

    }
    
    public static void main(String[] args) throws InterruptedException {
        new Aplicacion().iniciarJuego();
    }

}
