package Oca1;

class Tablero {
    public static final int NUM_CASILLAS = 63;
    private Casilla[] casillas;

    public Tablero() {
        casillas = new Casilla[NUM_CASILLAS];
        inicializarTablero();
    }

    public void inicializarTablero() {
        for (int i = 9; i < NUM_CASILLAS; i += 9) {
            casillas[i] = new Casilla(TipoCasilla.OCA);
        }

        casillas[6] = new Casilla(TipoCasilla.PUENTE);
        casillas[18] = new Casilla(TipoCasilla.DADOS_TRAVIESOS);
        casillas[19] = new Casilla(TipoCasilla.HOTEL);
        casillas[31] = new Casilla(TipoCasilla.CARCEL);
        casillas[42] = new Casilla(TipoCasilla.UNICORNIO);
        casillas[52] = new Casilla(TipoCasilla.MUERTE);
        casillas[58] = new Casilla(TipoCasilla.LABERINTO);

        for (int i = 0; i < NUM_CASILLAS; i++) {
            if (casillas[i] == null) {
                casillas[i] = new Casilla(TipoCasilla.VACIA);
            }
        }
    }

    public Casilla obtenerCasilla(int posicion) {
        return casillas[posicion];
    }
}
