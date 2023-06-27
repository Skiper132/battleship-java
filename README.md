# Bienvenido a BattleShip en Java

Este proyecto implementa una versión del popular juego Batalla Naval (BattleShip) utilizando Java. El diseño del juego sigue el patrón Modelo-Vista-Controlador (MVC), y está estructurado en varios paquetes para separar las responsabilidades de cada parte del código.

## Empezando

Para comenzar a trabajar en este proyecto, necesitarás un entorno de desarrollo Java, como IntelliJ IDEA o Eclipse. Te recomendamos usar IntelliJ IDEA, ya que es el IDE que se ha utilizado para el desarrollo de este proyecto.

## Estructura de carpetas

El espacio de trabajo contiene varias carpetas de código fuente y paquetes, donde:

- `src`: la carpeta para mantener las fuentes
   - `com.battleship`: Contiene los paquetes `modelo`, `vista` y `controlador` de la aplicación BattleShip.
      - `modelo`: Contiene las clases de entidades del juego, como `Barco`, `Casilla`, `Tablero` y `Jugador`.
      - `vista`: Aquí se añadirán las clases relacionadas con la interfaz de usuario del juego.
      - `controlador`: Aquí se añadirán las clases responsables de coordinar la lógica del juego.
      - `excepciones`: Contiene clases de excepciones personalizadas para el juego.
      - `util`: Contiene clases de utilidad para el juego.
- `lib`: la carpeta para mantener las dependencias

## Progreso Actual

El desarrollo actual del juego de Batalla Naval (BattleShip) sigue el patrón de diseño Modelo-Vista-Controlador (MVC), y se ha logrado avanzar sustancialmente en la construcción del modelo del juego. A continuación, se detalla este progreso:

- Excepciones Personalizadas: Hemos definido excepciones específicas del juego en el paquete com.battleship.excepciones. Estas son: BarcoNoPosicionableException, BarcoYaPosicionadoException y CasillaYaAtacadaException.

- Modelado de Barcos, Casillas y Jugadores: Hemos creado clases para representar las principales entidades del juego. Por ejemplo, la clase Barco en com.battleship.modelo puede posicionarse en un conjunto de Casilla, y los Jugador tienen asociados un conjunto de Barco y dos Tablero, uno propio y uno del enemigo.

- Estructura del Tablero: Cada Tablero está compuesto por un array 2D de Casilla. Cada Casilla tiene una Coordenada asociada y un estado que puede ser VACIA, OCUPADA o ATACADA.