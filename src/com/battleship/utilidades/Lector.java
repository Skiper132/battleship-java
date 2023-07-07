package com.battleship.utilidades;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/* LectorUsuario contiene métodos para leer entradas de datos, trabaja con la clase Validador para validar
 * las entradas.
 */

import com.battleship.modelo.Coordenada;
import com.battleship.excepciones.CoordenadaInvalidaException;

public class Lector {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Lee una entrada de datos del usuario y la retorna.
     * Si la entrada está vacía, solicita al usuario que ingrese una entrada.
     * Si ocurre un error de entrada/salida, muestra un mensaje de error y solicita al usuario que ingrese una entrada.
     * 
     * @return la entrada de datos del usuario.
     */
    public static String cargarEntrada() {
        String entrada = "";
        while (entrada.isEmpty()) { // .isEmpty() retorna true si la cadena está vacía.
            try {
                entrada = br.readLine();
            } catch (IOException e) {
                System.out.println("Error de entrada/salida: " + e.getMessage());
                System.out.println("Por favor, intenta de nuevo.");
            }
        }
        return entrada;
    }

    public static Coordenada cargarCoordenada() {
        Coordenada coordenada = null;
        while (coordenada == null) {
            try {
                coordenada = Validador.convertirCoordenada(cargarEntrada());
            } catch (CoordenadaInvalidaException e) {
                System.out.println("Coordenada inválida, por favor intenta de nuevo.");
            }
        }
        return coordenada;
    }
}