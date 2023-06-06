package Oca1;
import java.util.*;

public class JuegoOca {
    private int numJugadores;
    private HashMap<String, Integer> posiciones;
    private boolean[] turnoExtra;
    private boolean[] jugadorSalvado;
    private boolean[] dobleDado;
    private Tablero tablero;
    private Random random;
    private Scanner scanner;
    private boolean juegoTerminado;

    public JuegoOca(int numJugadores) {
        this.numJugadores = numJugadores;
        posiciones = new HashMap<>();
        turnoExtra = new boolean[numJugadores];
        jugadorSalvado = new boolean[numJugadores];
        dobleDado = new boolean[numJugadores];
        tablero = new Tablero();
        random = new Random();
        scanner = new Scanner(System.in);
        juegoTerminado = false;
    }

    public void iniciarJuego() {
        Tablero tablero = new Tablero();
        tablero.inicializarTablero();

        System.out.println("¡Bienvenido al juego de la Oca!");

        for (int i = 0; i < numJugadores; i++) {
            System.out.print("Ingrese el nombre del jugador " + (i + 1) + ": ");
            String nombre = scanner.nextLine();
            posiciones.put(nombre, 0);
            turnoExtra[i] = false;
            jugadorSalvado[i] = false;
            dobleDado[i] = false;
        }

        int jugadorActual = 0;
        System.out.println("¡El jugador " + obtenerNombreJugador(jugadorActual) + " comienza la partida!");
        while (true) {
            String nombreJugador = obtenerNombreJugador(jugadorActual);
            if (!jugadorSalvado[jugadorActual]) {
                System.out.println("\nTurno del jugador " + nombreJugador + ":");
                jugarTurno(nombreJugador);
                if (verificarFinJuego(nombreJugador)) {
                    return;
                }
            }
            jugadorActual = (jugadorActual + 1) % numJugadores;
        }

    }

    private void jugarTurno(String nombreJugador) {
        System.out.print("Presiona enter para tirar el dado: ");
        scanner.nextLine();

        int numeroDado = random.nextInt(6) + 1;
        System.out.println("El jugador " + nombreJugador + " ha sacado un " + numeroDado + " en el dado mágico.");

        if (dobleDado[obtenerIndiceJugador(nombreJugador)]) {
            int numeroDadoExtra = random.nextInt(6) + 1;
            System.out.println("El jugador " + nombreJugador + " ha sacado un " + numeroDadoExtra + " en el segundo dado mágico.");
            numeroDado += numeroDadoExtra;
            dobleDado[obtenerIndiceJugador(nombreJugador)] = false;
        }

        if (turnoExtra[obtenerIndiceJugador(nombreJugador)]) {
            System.out.println("El jugador " + nombreJugador + " tiene un turno extra. Avanza una casilla más.");
            turnoExtra[obtenerIndiceJugador(nombreJugador)] = false;
            moverFicha(nombreJugador, numeroDado + 1);
        }

        moverFicha(nombreJugador, numeroDado);

        // Check if the player has reached or surpassed the last position
        int posicionActual = posiciones.get(nombreJugador);
        if (posicionActual >= Tablero.NUM_CASILLAS - 1) {
            // Player has reached or surpassed the last position
            System.out.println("¡" + nombreJugador + " ha ganado el juego!");
            juegoTerminado = true;
        }
    }


    private void moverFicha(String nombreJugador, int numeroCasillas) {
    	int nuevaPosicion = posiciones.get(nombreJugador) + numeroCasillas;

    	if (nuevaPosicion >= Tablero.NUM_CASILLAS) {
    	    int sobrante = nuevaPosicion - (Tablero.NUM_CASILLAS);
    	    nuevaPosicion = Tablero.NUM_CASILLAS - sobrante;
    	} else if (nuevaPosicion < 0) {
    	    nuevaPosicion = Math.abs(nuevaPosicion);
    	}

        posiciones.put(nombreJugador, nuevaPosicion);
        System.out.println("El jugador " + nombreJugador + " se ha movido a la casilla " + (nuevaPosicion + 1));

        Casilla casillaActual = tablero.obtenerCasilla(nuevaPosicion);
        if (casillaActual != null) {
            TipoCasilla tipoCasilla = casillaActual.getTipo();
            aplicarReglasCasilla(nombreJugador, tipoCasilla);
        } else {
            System.out.println("La casilla actual es nula. Verifica la configuración del tablero.");
        }
    }
