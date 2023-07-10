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
            // Mientras no se hayan posicionado todos los barcos
            while (!controlador.getJugadorActivo().todosLosBarcosPosicionados()) {

                String opcion = Lector.cargarEntrada();
                switch (opcion) {
                    case "1":
                        while (!controlador.getJugadorActivo().todosLosBarcosPosicionados()) {
                            // Se limpia la pantalla para que el usuario no vea las casillas que ya se han posicionado
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
                    // Posicionamiento aleatorio: Se agregó esta opción para facilitar las pruebas, es posible que se elimine en la versión final
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
        // Mientras no se haya ganado o perdido
        while(!controlador.verificarDerrota()) {
            esperar(2000);
            pantalla.limpiarPantalla();
            // Se muestran el tablero del jugador activo y el tablero del enemigo (con las casillas ocupadas escondidas)
            pantalla.mostrarTurnoJugador();
            pantalla.ventanaTableros();

            String entrada = pantalla.pedirCasilla();

            // Si el usuario ingresa "EXIT", todos sus barcos se hunden automáticamente, por lo que pierde
            if (entrada.equalsIgnoreCase("EXIT")){
                controlador.autoDestruirBarcos();
            } else {
                Lector.cargarCasilla(entrada);
                try {
                    // Intenta atacar la casilla ingresada
                    pantalla.imprimirResultadoAtaque(controlador.atacarCasilla(controlador.getEnemigo(), Lector.getUltimaCasillaCargada()));
                    // Si el ataque fue exitoso, se cambia de jugador
                    controlador.cambiarJugadorActivo();
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
            controlador.cambiarJugadorActivo();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        new Aplicacion().iniciarJuego();
    }

}
