package com.battleship.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.battleship.controlador.ControladorJuego;
import com.battleship.excepciones.BarcoNoExistenteException;
import com.battleship.excepciones.BarcoYaPosicionadoException;
import com.battleship.excepciones.CoordenadaInvalidaException;
import com.battleship.excepciones.DireccionInvalidaException;
import com.battleship.modelo.Casilla;
import com.battleship.modelo.Barco;
import com.battleship.modelo.Direccion;

public class Lector {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static ControladorJuego controlador = ControladorJuego.getInstancia();
    private static Casilla ultimaCasillaCargada;
    private static Direccion ultimaDireccionCargada;
    private static Barco ultimoBarcoCargado;

    public static String cargarEntrada() {
        String entrada = "";
        while (entrada.isEmpty()) {
            try {
                entrada = br.readLine();
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage());
                System.out.println("Por favor, intenta de nuevo.");
            }
        }
        return entrada;
    }

    public static String extraerCasilla(String entrada) {
        return entrada.substring(0, 2);
    }

    public static String extraerDireccion(String entrada) {
        return entrada.substring(3, 4);
    }

    public static void cargarBarco() {
        String entrada = cargarEntrada();
        do {
            try {
                ultimoBarcoCargado = controlador.getBarcoNoPosicionadoPorNombre(entrada);
                break;  // si todo va bien, salimos del bucle
            } catch (BarcoNoExistenteException | BarcoYaPosicionadoException e) {
                System.out.println(e.getMessage() + " Por favor, intenta de nuevo.");
                entrada = cargarEntrada();
            }
        } while (true);
    }
    public static void cargarPosicionamientoBarco() {
        String entrada = cargarEntrada();
        do {
            try {
                ultimaCasillaCargada = controlador.getCasillaPorCadena(extraerCasilla(entrada));
                ultimaDireccionCargada = controlador.getDireccionPorCadena(extraerDireccion(entrada));
                break;  // si todo va bien, salimos del bucle
            } catch (CoordenadaInvalidaException | DireccionInvalidaException | StringIndexOutOfBoundsException e) {
                System.out.println(e.getMessage() + " Por favor, intenta de nuevo.");
                entrada = cargarEntrada();
            }
        } while (true);
    }


    public static Casilla getUltimaCasillaCargada() {
        return ultimaCasillaCargada;
    }

    public static Direccion getUltimaDireccionCargada() {
        return ultimaDireccionCargada;
    }

    public static Barco getUltimoBarcoCargado() {
        return ultimoBarcoCargado;
    }

    public static Casilla cargarCasilla() {
        Casilla casilla = null;
        while (casilla == null) {
            try {
                casilla = controlador.getCasillaPorCadena(cargarEntrada());
            } catch (CoordenadaInvalidaException e) {
                System.out.println(e.getMessage() + " Por favor, intenta de nuevo.");
            }
        }
        return casilla;
    }
}