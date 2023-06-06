package Oca1;

public class Casilla {

	private TipoCasilla tipo;

    public Casilla(TipoCasilla tipo) {
        this.tipo = tipo;
    }

    public TipoCasilla getTipo() {
        return tipo;
    }
}

enum TipoCasilla {
    NORMAL,
    OCA,
    PUENTE,
    DADOS_TRAVIESOS,
    HOTEL,
    CARCEL,
    COCHE,
    UNICORNIO,
    MUERTE,
    LABERINTO, VACIA
   }

