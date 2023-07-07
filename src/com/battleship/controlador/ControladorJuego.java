package com.battleship.controlador;

import com.battleship.excepciones.BarcoFueraDeRangoException;
import com.battleship.excepciones.BarcoNoPosicionableException;
import com.battleship.excepciones.BarcoYaPosicionadoException;
import com.battleship.excepciones.CasillaYaAtacadaException;
import com.battleship.modelo.*;

public class ControladorJuego {
    private Jugador jugador1;
    private Jugador jugador2;
    private Jugador jugadorActual;

    public ControladorJuego(String jugador12, String jugador22) {
        this.jugador1 = new Jugador("Jugador 1");
        this.jugador2 = new Jugador("Jugador 2");
    }


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

    public void atacarCasilla(Jugador jugador, Casilla casilla) throws CasillaYaAtacadaException{
        // Obtener la casilla del tablero del jugador
        Casilla casillaAtacada = jugador.getTablero().getCasilla(casilla.getCoordenada());
        // Obtener el barco de la casilla, si existe
        Barco barco = casillaAtacada.getBarco();
        try {
            ResultadoAtaque resultado = casillaAtacada.atacar();
            if (barco != null) {
                barco.recibirAtaque();
            }
            System.out.println(resultado.getMensaje());
        } catch (CasillaYaAtacadaException e) {
            System.out.println(e.getMessage());
        }
    }

    public void mostrarTablero(Jugador jugador) {
        Casilla[][] casillas = jugador.getTablero().getCasillas();

        // Imprime el encabezado de las columnas del tablero
        System.out.print("  ");
        for (int i = 0; i < casillas[0].length; i++) {
            System.out.print(i + 1 + " ");
        }
        System.out.println();

        // Imprime las filas del tablero
        for (int i = 0; i < casillas.length; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < casillas[i].length; j++) {
                // Ya no necesitamos obtener la coordenada ni buscar la casilla en el tablero
                Casilla casilla = casillas[i][j];
                // Obtenemos el símbolo de la casilla y lo imprimimos
                System.out.print(casilla.getSimbolo() + " ");
            }
            System.out.println();
        }
    }

    public void mostrarTableroEnemigo(Jugador jugador) {
        //TODO: Implementar
    }

    public String mostrarBarcosNoPosicionados(Jugador jugador) {
        String barcosNoPosicionados = "";
        /**
         * Mostrar en lista:
         * [Lancha 1]
         * [Lancha 2]
         * [Submarino]
         * [Acorazado]
         */
        for (Barco barco : jugador.getTablero().getBarcos()) {
            if (barco.getEstado() == EstadoBarco.NO_POSICIONADO) {
                barcosNoPosicionados += "[" + barco.getNombre() + "]\n";
            }
        }
        return barcosNoPosicionados;
    }

    public Jugador getJugadorPorNombre(String nombreJugador){
    }
}
