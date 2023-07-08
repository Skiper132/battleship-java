package com.battleship.controlador;

import com.battleship.excepciones.*;
import com.battleship.modelo.*;

import java.util.HashMap;

public class ControladorJuego {
    private static ControladorJuego instanciaUnica = null;
    private HashMap<String, Jugador> jugadores;
    private Jugador jugadorActivo;

    public ControladorJuego() {
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

    public void crearJugador(String nombre) {
        Jugador jugador = new Jugador(nombre);
        this.jugadores.put(nombre, jugador);
    }

    public String generarStringTablero(Jugador jugador) {
        // Inicializa una cadena vacía para almacenar la representación del tablero
        String tableroStr = "";

        // Obtiene las casillas del tablero del jugador
        Casilla[][] casillas = jugador.getTablero().getCasillas();

        // Añade el encabezado de las columnas al tableroStr
        tableroStr += "  ";
        for (int i = 0; i < casillas[0].length; i++) {
            tableroStr += (i + 1) + " ";
        }
        tableroStr += "\n";

        // Itera a través de las filas del tablero
        for (int i = 0; i < casillas.length; i++) {
            // Añade el encabezado de la fila (letra correspondiente) al tableroStr
            tableroStr += (char) ('A' + i) + " ";

            // Itera a través de las columnas de la fila actual
            for (int j = 0; j < casillas[i].length; j++) {
                // Obtiene la casilla correspondiente
                Casilla casilla = casillas[i][j];
                // Añade el símbolo de la casilla al tableroStr
                tableroStr += casilla.getSimbolo() + " ";
            }

            // Añade una nueva línea después de cada fila
            tableroStr += "\n";
        }

        // Devuelve la representación del tablero como una cadena
        return tableroStr;
    }

    public String mostrarTablero() {
        return generarStringTablero(jugadorActivo);
    }

    public boolean todosLosBarcosPosicionados() {
        for (Barco barco : this.jugadorActivo.getTablero().getBarcos()) {
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
        for (Barco barco : this.jugadorActivo.getTablero().getBarcos()) {
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
        int numero = Integer.parseInt(coordenada.substring(1));
        if (letra < 'A' || letra > 'J' || numero < 1 || numero > 10) {
            throw new CoordenadaInvalidaException();
        }

        casillaConvertida = this.jugadorActivo.getTablero().getCasillas()[letra - 'A'][numero - 1];
        return casillaConvertida;
    }

    private Barco buscarBarcoPorNombre(String nombre) {
        for (Barco barco : this.jugadorActivo.getTablero().getBarcos()) {
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

    public boolean posicionarBarco(Barco barco, Casilla casillaInicial, Direccion direccion) {
        Tablero tablero = this.jugadorActivo.getTablero();
        try {
            // Utilizar el método del Tablero para asignar las casillas al barco
            Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

            // Configurar las casillas para el barco
            barco.setCasillas(casillasParaElBarco);
            // Añadir el barco a la lista de barcos del tablero
            tablero.agregarBarco(barco);

            // Si llegamos a este punto, entonces no hubo excepciones y el barco fue posicionado correctamente
            return true;
        } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
            // Manejar las excepciones aquí
            System.out.println(e.getMessage());

            // Si llegamos a este punto, entonces hubo una excepción y el barco no pudo ser posicionado
            return false;
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
}
