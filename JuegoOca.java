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
    private void aplicarReglasCasilla(String nombreJugador, TipoCasilla tipoCasilla) {
        switch (tipoCasilla) {
            case OCA:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Oca. Avanza a la siguiente Oca.");
                scanner.nextLine();
                int siguienteOca = obtenerSiguienteOca(posiciones.get(nombreJugador));
                int numeroCasillas = siguienteOca - posiciones.get(nombreJugador);
                moverFicha(nombreJugador, numeroCasillas);
                break;
            case PUENTE:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Puente. Avanza hasta la casilla 12.");
                scanner.nextLine();
                moverFicha(nombreJugador, 12 - posiciones.get(nombreJugador));
                break;
            case DADOS_TRAVIESOS:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Dados Traviesos. Juega de nuevo.");
                scanner.nextLine();
                turnoExtra[obtenerIndiceJugador(nombreJugador)] = true;
                break;
            case HOTEL:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Hotel. Avanzará una casilla extra durante los tres siguientes turnos.");
                turnoExtra[obtenerIndiceJugador(nombreJugador)] = true;
                scanner.nextLine();
                activarCasillaExtra(nombreJugador, 3);
                break;
            case CARCEL:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Cárcel. Pierde un turno.");
                scanner.nextLine();
                break;
            case COCHE:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Coche. Avanza hasta la casilla 19.");
                scanner.nextLine();
                moverFicha(nombreJugador, 19 - posiciones.get(nombreJugador));
                break;
            case UNICORNIO:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Unicornio. Lanzará dos dados en sus próximos dos turnos.");
                scanner.nextLine();
                dobleDado[obtenerIndiceJugador(nombreJugador)] = true;
                activarCasillaExtra(nombreJugador, 2);
                break;
            case MUERTE:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Muerte. Vuelve a la casilla 1.");
                scanner.nextLine();
                moverFicha(nombreJugador, 1 - posiciones.get(nombreJugador));
                break;
            case LABERINTO:
                System.out.println("El jugador " + nombreJugador + " ha caído en una casilla de Laberinto. Otro jugador puede salvarlo lanzando un número mayor que 4.");
                scanner.nextLine();
                String jugadorSalvador = obtenerJugadorSalvador(nombreJugador);
                if (!jugadorSalvador.isEmpty()) {
                    System.out.println("El jugador " + jugadorSalvador + " ha lanzado un número mayor que 4. El jugador " + nombreJugador + " es salvado y avanza una casilla extra.");
                    jugadorSalvado[obtenerIndiceJugador(nombreJugador)] = true;
                    moverFicha(nombreJugador, 1);
                } else {
                    System.out.println("Ningún jugador puede salvar al jugador " + nombreJugador + ". Deberá esperar al próximo turno para intentarlo de nuevo.");
                }
                break;
            default:
                break;
        }
    }

    private int obtenerSiguienteOca(int posicion) {
        int siguienteOca = -1;
        for (int i = posicion + 1; i < Tablero.NUM_CASILLAS; i++) {
            if (tablero.obtenerCasilla(i).getTipo() == TipoCasilla.OCA) {
                siguienteOca = i;
                break;
            }
        }
        return siguienteOca;
    }

    private String obtenerJugadorSalvador(String jugador) {
        String jugadorSalvador = "";
        for (Map.Entry<String, Integer> entry : posiciones.entrySet()) {
            String nombreJugador = entry.getKey();
            int posicion = entry.getValue();

            if (!nombreJugador.equals(jugador) && !jugadorSalvado[obtenerIndiceJugador(nombreJugador)]) {
                int numeroDado = random.nextInt(6) + 1;
                if (numeroDado > 4) {
                    jugadorSalvador = nombreJugador;
                    break;
                }
            }
        }
        return jugadorSalvador;
    }

    public void activarCasillaExtra(String nombreJugador, int numTurnos) {
        // Verificar si el jugador está en una casilla de hotel
        if (tablero.obtenerCasilla(posiciones.get(nombreJugador)).getTipo() == TipoCasilla.HOTEL) {
            turnoExtra[obtenerIndiceJugador(nombreJugador)] = true;
        }
    }

    private boolean verificarFinJuego(String nombreJugador) {
        if (posiciones.get(nombreJugador) == Tablero.NUM_CASILLAS - 1) {
            System.out.println("\n¡El jugador " + nombreJugador + " ha llegado a la casilla final!");
            System.out.println("¡Felicidades! ¡Has ganado el juego de la Oca!");
            return true;
        }
        return false;
    }
    
    private int obtenerIndiceJugador(String nombreJugador) {
        int indice = -1;
        int i = 0;
        for (Map.Entry<String, Integer> entry : posiciones.entrySet()) {
            if (entry.getKey().equals(nombreJugador)) {
                indice = i;
                break;
            }
            i++;
        }
        return indice;
    }

    
    private String obtenerNombreJugador(int indice) {
        // Obtener el nombre del jugador correspondiente al índice
        String nombre = null;
        int i = 0;
        for (Map.Entry<String, Integer> entry : posiciones.entrySet()) {
            if (i == indice) {
                nombre = entry.getKey();
                break;
            }
            i++;
        }
        return nombre;
    }

    
}
