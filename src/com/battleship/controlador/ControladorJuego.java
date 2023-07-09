package com.battleship.controlador;

import com.battleship.excepciones.*;
import com.battleship.modelo.*;

import java.util.HashMap;
import java.util.Random;
/**
 * Controlador del juego.
 * Se encarga de crear los jugadores, cambiar el jugador activo, mostrar el tablero, etc.
 * Singleton.
 */
public class ControladorJuego {
    private static ControladorJuego instanciaUnica = null;
    // Los jugadores del juego. El HashMap tiene como clave el nombre del jugador y como valor el objeto Jugador.
    private HashMap<String, Jugador> jugadores;
    // El jugador que está jugando activomente.
    private Jugador jugadorActivo;

    // Constructor privado para que no se pueda instanciar desde afuera.
    private ControladorJuego() {
        this.jugadores = new HashMap<>();
        this.jugadorActivo = null;
    }

    /**
     * Devuelve la instancia única del controlador.
     * @return la instancia única del controlador.
     */
    public static ControladorJuego getInstancia() { // Singleton
        if (instanciaUnica == null) {
            instanciaUnica = new ControladorJuego();
        }
        return instanciaUnica;
    }

    public Jugador getJugadorActivo() {
        return jugadorActivo;
    }

    /**
     * Devuelve el jugador según su nombre.
     *
     * @param nombre el nombre del jugador.
     * @return el jugador.
     */
    public Jugador getJugador(String nombre) {
        return this.jugadores.get(nombre);
    }



    /**
     * Establece el jugador activo según su nombre.
     * 
     * @param nombre el nombre del jugador.
     */
    public void setJugadorActivoPorNombre(String nombre) {
        this.jugadorActivo = this.jugadores.get(nombre);
    }

    /**
     * Devuelve el jugador que no es el jugador activo.
     *
     * @return el jugador que no es el jugador activo.
     */
    public Jugador getEnemigo() {
        for (Jugador jugador : jugadores.values()) { // Recorre todos los jugadores
            if (jugador != jugadorActivo) {
                return jugador;
            }
        }
        return null; // Nunca debería llegar a este punto si siempre hay dos jugadores.
    }

    /**
     * Crea un jugador y lo agrega al HashMap jugadores.
     *
     * @param nombre el nombre del jugador.
     * @throws JugadorExistenteException si ya existe un jugador con ese nombre.
     * @throws LimiteJugadoresException si ya hay dos jugadores.
     */
    public void crearJugador(String nombre) throws JugadorExistenteException, LimiteJugadoresException{
        if(this.jugadores.size() < 2) {
            if(this.jugadores.containsKey(nombre)) {
                throw new JugadorExistenteException(); // Lanza excepción
            } else {
                Jugador jugador = new Jugador(nombre);
                this.jugadores.put(nombre, jugador);
            }
        } else {
            throw new LimiteJugadoresException(); // Lanza excepción si ya hay dos jugadores
        }
    }

    /**
     * Establece el jugador activo al otro jugador.
     */
    public void cambiarJugadorActivo() {
        for (String nombre : jugadores.keySet()) { // Recorre todos los nombres de los jugadores
            if (!jugadorActivo.getNombre().equals(nombre)) {
                jugadorActivo = jugadores.get(nombre);
                break; // Termina el bucle cuando encuentra el otro jugador
            }
        }
    }



    /**
     * Muestra el tablero del jugador activo.
     * @return
     */
    public String mostrarTablero() {
        return generarStringTablero(jugadorActivo, false);
    }

    /**
     * Muestra el tablero del enemigo.
     * @return
     */
    public String mostrarTableroEnemigo() {
        Jugador enemigo = getEnemigo();
        return generarStringTablero(enemigo, true);
    }

    /**
     * 
     * Este método se utiliza para generar una representación visual del tablero de un jugador en forma de cadena de caracteres.
     * La representación muestra las casillas del tablero junto con los números de columna y las letras de fila correspondientes.
     * Dependiendo del valor del parámetro "esEnemigo", las casillas ocupadas pueden mostrarse o no. 
     *
     * @param jugador el jugador.
     * @param esEnemigo si el jugador es el enemigo.
     * @return el String con el tablero.
     */
    private String generarStringTablero(Jugador jugador, boolean esEnemigo) {
        StringBuilder tableroStr = new StringBuilder();


        Casilla[][] casillas = jugador.getTablero().getCasillas();

        tableroStr.append("  ");
        // Generar números de columnas
        for (int i = 0; i < casillas[0].length; i++) { // 1, 2, 3, ... 9
            tableroStr.append(i + 1).append(" ");
        }
        tableroStr.append("\n");

        // Generar letras de filas y casillas
        for (int i = 0; i < casillas.length; i++) {
            tableroStr.append((char) ('A' + i)).append(" ");

            for (int j = 0; j < casillas[i].length; j++) {
                Casilla casilla = casillas[i][j];
                // Si es el tablero del enemigo, no se muestran las casillas ocupadas
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

    /**
     * Muestra los barcos no posicionados del jugador activo.
     * @return
     */
    public String mostrarBarcosNoPosicionados() {
        StringBuilder barcosNoPosicionados = new StringBuilder();
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getEstado() == EstadoBarco.NO_POSICIONADO) {
                barcosNoPosicionados.append("[").append(barco.getNombre()).append("] Tamaño: ").append(barco.getLongitud()).append(" casillas\n");
            }
        }
        return barcosNoPosicionados.toString();
    }

    /**
     * Muestra los barcos posicionados del jugador activo.
     * @return
     */
    public String mostrarBarcosPosicionados() {
        StringBuilder barcosPosicionados = new StringBuilder();

        barcosPosicionados.append("Nombre\tLongitud\tCoordenadas\n");
        for (Barco barco : this.jugadorActivo.getBarcos().values()) { // Recorre todos los barcos del jugador
            barcosPosicionados.append(barco.getNombre()).append("\t").append(barco.getLongitud()); // Nombre y longitud

            // Generamos las coordenadas
            StringBuilder coordenadas = new StringBuilder();
            for (Casilla casilla : barco.getCasillas()) { // Recorre todas las casillas del barco
                if (coordenadas.length() != 0) {     // Si no es la primera coordenada, añade una coma
                    coordenadas.append(", "); // Añade una coma
                }
                coordenadas.append(casilla.getCoordenada().toString()); // Añade la coordenada
            }

            barcosPosicionados.append("\t").append(coordenadas).append("\n");

        }

        return barcosPosicionados.toString();
    }


    /**
     * Devuelve un barco según su nombre.
     * 
     * @param nombre el nombre del barco.
     * @return el barco.
     */
    public Barco getBarcoPorNombre(String nombre) {
        return buscarBarcoPorNombre(nombre);
    }

    /**
     * Devuelve el barco no posicionado del jugador activo según su nombre.
     * @param nombre el nombre del barco.
     * @return el barco.
     * @throws BarcoNoExistenteException si el barco no existe.
     * @throws BarcoYaPosicionadoException si el barco ya está posicionado.
     */
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

    /**
     * Devuelve una casilla del tablero del jugador activo según su coordenada en forma de cadena de caracteres.
     * @param coordenada la coordenada en forma de cadena de caracteres.
     * @return la casilla.
     * @throws CoordenadaInvalidaException si la coordenada es inválida.
     */
    public Casilla getCasillaPorCadena(String coordenada) throws CoordenadaInvalidaException {
        Casilla casillaConvertida = null;

        if (coordenada.length() != 2) {
            throw new CoordenadaInvalidaException();
        }

        // Dividimos la coordenada en letra y número
        char letra = coordenada.charAt(0);
        char posibleNumero = coordenada.charAt(1);
        int numero;

        // Comprobamos que la letra sea una letra válida y el número un número válido
        if (!Character.isDigit(posibleNumero)) {
            throw new CoordenadaInvalidaException();
        } else {
            numero = Character.getNumericValue(posibleNumero);
        }

        // Comprobamos que esté dentro del tablero
        if (letra < 'A' || letra > 'I' || numero < 1) {
            throw new CoordenadaInvalidaException();
        }

        // Convertimos la coordenada a casilla
        casillaConvertida = this.jugadorActivo.getTablero().getCasillas()[letra - 'A'][numero - 1];
        return casillaConvertida;
    }


    /**
     * Devuelve un barco del jugador activo según su nombre.
     * 
     * @param nombre el nombre del barco.
     * @return el barco.
     */
    private Barco buscarBarcoPorNombre(String nombre) {
        for (Barco barco : this.jugadorActivo.getBarcos().values()) {
            if (barco.getNombre().equals(nombre)) {
                return barco;
            }
        }
        return null; // No se ha encontrado el barco
    }

    /**
     * Devuelve una dirección según su cadena de caracteres.
     *
     * @param direccion la dirección en forma de cadena de caracteres.
     * @return la dirección.
     * @throws DireccionInvalidaException si la dirección es inválida.
     */
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

    /**
     * Este método posiciona un barco en el tablero del jugador activo según la casilla inicial y la dirección.
     *
     * @param barco el barco a posicionar.
     * @param casillaInicial la casilla inicial.
     * @param direccion la dirección.
     * @throws BarcoNoPosicionableException si el barco no se puede posicionar.
     * @throws BarcoFueraDeRangoException si el barco se sale del tablero.
     */
    public void posicionarBarco(Barco barco, Casilla casillaInicial, Direccion direccion) throws BarcoNoPosicionableException, BarcoFueraDeRangoException {
        // Obtener el tablero del jugador
        Tablero tablero = this.jugadorActivo.getTablero();

        // Verificar las casillas en el tablero para ver si se puede posicionar el barco
        Casilla[] casillasParaElBarco = tablero.asignarCasillasParaBarco(barco, casillaInicial.getCoordenada(), direccion.getDesplazamientoFila(), direccion.getDesplazamientoColumna());

        // Configurar las casillas para el barco
        barco.setCasillas(casillasParaElBarco);
        tablero.agregarBarco(barco);
    }

    /**
     * Este método ataca una casilla del tablero del jugador especificado.
     *
     * @param jugador el jugador.
     * @param casilla la casilla.
     * @return el resultado del ataque.
     * @throws CasillaYaAtacadaException si la casilla ya ha sido atacada.
     */
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

    /**
     * Devuelve el mensaje del resultado del ataque. (ej: Has fallado)
     * 
     * @param resultadoAtaque el resultado del ataque.
     * @return el mensaje.
     */
    public String mostrarResultadoAtaque(ResultadoAtaque resultadoAtaque) {
        return resultadoAtaque.getMensaje();
    }
    /**
     * Comprueba si todos los barcos del jugador activo están hundidos, es decir, si ha perdido.
     *
     * @return true si ha perdido, false si no.
     */
    public boolean verificarDerrota() {
        if (jugadorActivo.todosLosBarcosHundidos()) {
            return true;
        }
        return false;
    }

    /**
     * Hunde todos los barcos del jugador activo.
     * 
     * @see Barco#hundir()
     */
    public void autoDestruirBarcos() { 
        // Recorre todos los barcos del jugador activo
        for (Barco barco : jugadorActivo.getBarcos().values()) {
            // Recorre todas las casillas del barco
            for (Casilla casilla : barco.getCasillas()) {
                // Si la casilla ya ha sido atacada, salta a la siguiente iteración
                if (casilla.getEstado() == EstadoCasilla.ATACADA) {
                    continue;
                }
                // Si no, ataca la casilla
                casilla.setEstado(EstadoCasilla.ATACADA);;
                barco.recibirAtaque();
            }
        }
    }

    /**
     * Posiciona todos los barcos del jugador activo aleatoriamente, utilizando el método posicionarBarco()
     * y generando coordenadas y direcciones aleatorias, los seguirá intentando hasta que encuentre una posición válida.
     *
     */
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
