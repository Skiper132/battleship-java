package com.battleship.controlador;

import com.battleship.excepciones.*;
import com.battleship.modelo.*;

import java.util.HashMap;
import java.util.Random;

public class ControladorJuego {
    private static ControladorJuego instanciaUnica = null;
    private HashMap<String, Jugador> jugadores;
    private Jugador jugadorActivo;

    // Constructor privado
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

    public Jugador getEnemigo() {
        for (Jugador jugador : jugadores.values()) {
            if (jugador != jugadorActivo) {
                return jugador;
            }
        }
        return null; // Nunca debería llegar a este punto si siempre hay dos jugadores.
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

    public void cambiarJugadorActual() {
        for (String nombre : jugadores.keySet()) { // Recorre todos los nombres de los jugadores
            if (!jugadorActivo.getNombre().equals(nombre)) {
                jugadorActivo = jugadores.get(nombre);
                break;
            }
        }
    }




    public String mostrarTablero() {
        return generarStringTablero(jugadorActivo, false);
    }

    public String mostrarTableroEnemigo() {
        Jugador enemigo = getEnemigo();
        return generarStringTablero(enemigo, true);
    }

    private String generarStringTablero(Jugador jugador, boolean esEnemigo) {
        StringBuilder tableroStr = new StringBuilder();

        Casilla[][] casillas = jugador.getTablero().getCasillas();

        tableroStr.append("  ");
        // Generar números de columnas
        for (int i = 0; i < casillas[0].length; i++) { // 1, 2, 3, ... 9
            tableroStr.append(i + 1).append(" ");
        }
        tableroStr.append("\n");

        for (int i = 0; i < casillas.length; i++) {
            tableroStr.append((char) ('A' + i)).append(" ");

            for (int j = 0; j < casillas[i].length; j++) {
                Casilla casilla = casillas[i][j];
                if (esEnemigo && casilla.getEstado() == EstadoCasilla.OCUPADA) {
                    tableroStr.append("- ");
                } else {
                    tableroStr.append(casilla.getSimbolo()).append(" ");
                }
            }

            tableroStr.append("\n");
        }

        return tableroStr.toString();
    }

    public String mostrarBarcosNoPosicionados() {
        StringBuilder barcosNoPosicionados = new StringBuilder();
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getEstado() == EstadoBarco.NO_POSICIONADO) {
                barcosNoPosicionados.append("[").append(barco.getNombre()).append("] Tamaño: ").append(barco.getLongitud()).append(" casillas\n");
            }
        }
        return barcosNoPosicionados.toString();
    }

    public String mostrarBarcosPosicionados() {
        StringBuilder barcosPosicionados = new StringBuilder();

        barcosPosicionados.append("Nombre\tLongitud\tCoordenadas\n");
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            barcosPosicionados.append(barco.getNombre()).append("\t").append(barco.getLongitud());

            // Generamos las coordenadas
            StringBuilder coordenadas = new StringBuilder();
            for (Casilla casilla : barco.getCasillas()) {
                if (coordenadas.length() != 0) {
                    coordenadas.append(", ");
                }
                coordenadas.append(casilla.getCoordenada().toString());
            }

            barcosPosicionados.append("\t").append(coordenadas).append("\n");

        }

        return barcosPosicionados.toString();
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

        if (letra < 'A' || letra > 'J' || numero < 1) {
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

        // Verificar las casillas en el tablero para ver si se puede posicionar el barco
        Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

        // Configurar las casillas para el barco
        barco.setCasillas(casillasParaElBarco);
        tablero.agregarBarco(barco);
    }


    public ResultadoAtaque atacarCasilla(Jugador jugador, Casilla casilla) throws CasillaYaAtacadaException{
        // Obtener la casilla del tablero del jugador
        Casilla casillaAtacada = jugador.getTablero().getCasilla(casilla.getCoordenada());
        // Obtener el barco de la casilla, si existe
        if (casillaAtacada.getBarco() != null) {
            Barco barco = casillaAtacada.getBarco();
            barco.recibirAtaque();
        }
        return casillaAtacada.atacar();
    }

    public String mostrarResultadoAtaque(ResultadoAtaque resultadoAtaque) {
        return resultadoAtaque.getMensaje();
    }
    public boolean verificarVictoria() {
        if (jugadorActivo.todosLosBarcosHundidos()) {
            return true;
        }
        return false;
    }

    public void autoDestruirBarcos() {
        for (Barco barco : jugadorActivo.getBarcos().values()) {
            for (Casilla casilla : barco.getCasillas()) {
                if (casilla.getEstado() == EstadoCasilla.ATACADA) {
                    continue; // Salta a la siguiente iteración
                }
                casilla.setEstado(EstadoCasilla.ATACADA);;
                barco.recibirAtaque();
            }
        }
    }

    public void posicionarBarcosAleatoriamente() {
        Random rand = new Random();

        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            // Inténtalo hasta que encuentres una posición válida
            while (true) {
                // Genera coordenadas aleatorias para la casilla inicial
                int filaAleatoria = rand.nextInt(9); // Considerando que el tablero es de 10x10
                int columnaAleatoria = rand.nextInt(9);

                // Genera una dirección aleatoria
                Direccion[] direcciones = Direccion.values();
                Direccion direccionAleatoria = direcciones[rand.nextInt(direcciones.length)];

                try {
                    Casilla casillaInicial = this.jugadorActivo.getTablero().getCasillas()[filaAleatoria][columnaAleatoria];
                    posicionarBarco(barco, casillaInicial, direccionAleatoria);
                    break; // Si no se lanzó ninguna excepción, el barco se ha posicionado correctamente. Salir del bucle.
                } catch (BarcoNoPosicionableException | BarcoFueraDeRangoException e) {
                    // Si el barco no pudo ser posicionado en esa casilla o con esa dirección, simplemente intenta otra vez.
                }
            }
        }
    }
}
