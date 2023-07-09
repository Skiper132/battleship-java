package com.battleship.controlador;

import com.battleship.excepciones.*;
import com.battleship.modelo.*;

import java.util.HashMap;

public class ControladorJuego {
    private static ControladorJuego instanciaUnica = null;
    private HashMap<String, Jugador> jugadores;
    private Jugador jugadorActivo;

    private ControladorJuego() {
        this.jugadores = new HashMap<>();
        this.jugadorActivo = null;
    }

    public static ControladorJuego getInstancia() { // Singleton
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorJuego();
        }
        return instanciaUnica;
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    public Jugador getJugador(String nombre) {
        return this.jugadores.get(nombre);
    }

    public void setJugadorActivoPorNombre(String nombre) {
        this.jugadorActivo = this.jugadores.get(nombre);
    }

    public void crearJugador(String nombre) throws JugadorExistenteException, LimiteJugadoresException{
        if(this.jugadores.size() < 2) {
            if(this.jugadores.containsKey(nombre)) {
                throw new JugadorExistenteException(); // Lanza excepción
            } else {
                Jugador jugador = new Jugador(nombre);
                this.jugadores.put(nombre, jugador);
            }
        } else {
            throw new LimiteJugadoresException(); // Lanza excepción
        }
    }

    public void cambioTurno() {
        if (jugadorActivo.getNombre().equals(jugadores.get(0).getNombre())) {
            jugadorActivo = jugadores.get(1);
        } else {
            jugadorActivo = jugadores.get(0);
        }
    }

    public String generarStringTablero(Jugador jugador) {
        String tableroStr = "";

        Casilla[][] casillas = jugador.getTablero().getCasillas();

        tableroStr += "  ";
        for (int i = 0; i < casillas[0].length; i++) {
            tableroStr += (i + 1) + " ";
        }
        tableroStr += "\n";

        for (int i = 0; i < casillas.length; i++) {
            tableroStr += (char) ('A' + i) + " ";

            for (int j = 0; j < casillas[i].length; j++) {
                Casilla casilla = casillas[i][j];
                tableroStr += casilla.getSimbolo() + " ";
            }

            tableroStr += "\n";
        }

        return tableroStr;
    }

    public String mostrarTablero() {
        return generarStringTablero(jugadorActivo);
    }

    public boolean todosLosBarcosPosicionados() {
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getEstado() == EstadoBarco.NO_POSICIONADO) {
                return false;
            }
        }
        return true;
    }

    public String mostrarBarcosNoPosicionados() {
        String barcosNoPosicionados = "";
        /**
         * Mostrar en lista:
         * [Lancha 1]
         * [Lancha 2]
         * [Submarino]
         * [Acorazado]
         */
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getEstado() == EstadoBarco.NO_POSICIONADO) {
                barcosNoPosicionados += "[" + barco.getNombre() + "] Tamaño: " + barco.getLongitud() + " casillas\n";
            }
        }
        return barcosNoPosicionados;
    }

    public Barco getBarcoPorNombre(String nombre) {
        return buscarBarcoPorNombre(nombre);
    }

    public Barco getBarcoNoPosicionadoPorNombre(String nombre) throws BarcoNoExistenteException, BarcoYaPosicionadoException {
        Barco barco = buscarBarcoPorNombre(nombre);

        if (barco == null) {
            throw new BarcoNoExistenteException();
        }

        if (barco.getEstado() == EstadoBarco.POSICIONADO) {
            throw new BarcoYaPosicionadoException();
        }

        return barco;
    }

    public Casilla getCasillaPorCadena(String coordenada) throws CoordenadaInvalidaException {
        Casilla casillaConvertida = null;

        if (coordenada.length() != 2) {
            throw new CoordenadaInvalidaException();
        }

        char letra = coordenada.charAt(0);
        char posibleNumero = coordenada.charAt(1);
        int numero;

        if (!Character.isDigit(posibleNumero)) {
            throw new CoordenadaInvalidaException();
        } else {
            numero = Character.getNumericValue(posibleNumero);
        }

        if (letra < 'A' || letra > 'J' || numero < 1 || numero > 10) {
            throw new CoordenadaInvalidaException();
        }

        casillaConvertida = this.jugadorActivo.getTablero().getCasillas()[letra - 'A'][numero - 1];
        return casillaConvertida;
    }


    private Barco buscarBarcoPorNombre(String nombre) {
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getNombre().equals(nombre)) {
                return barco;
            }
        }
        return null;
    }

    public Direccion getDireccionPorCadena(String direccion) throws DireccionInvalidaException {
        Direccion direccionConvertida = null;

        switch (direccion) {
            case "N":
                direccionConvertida = Direccion.NORTE;
                break;
            case "S":
                direccionConvertida = Direccion.SUR;
                break;
            case "E":
                direccionConvertida = Direccion.ESTE;
                break;
            case "O":
                direccionConvertida = Direccion.OESTE;
                break;
            default:
                throw new DireccionInvalidaException();
        }

        return direccionConvertida;
    }

    public void posicionarBarco(Barco barco, Casilla casillaInicial, Direccion direccion) throws BarcoNoPosicionableException, BarcoFueraDeRangoException {
        Tablero tablero = this.jugadorActivo.getTablero();

        // Utilizar el método del Tablero para asignar las casillas al barco
        Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

        // Configurar las casillas para el barco
        barco.setCasillas(casillasParaElBarco);
        tablero.agregarBarco(barco);
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
}
