package Oca1;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el número de jugadores: ");
        int numJugadores = scanner.nextInt();
        scanner.nextLine();
        
        JuegoOca juego = new JuegoOca(numJugadores);
        juego.iniciarJuego();
    }
