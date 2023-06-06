	package Oca1;

		class Jugador {
		    private String nombre;
		    private int posicion;
		    private int casillaExtra;

		    public Jugador(String nombre) {
		        this.nombre = nombre;
		        this.posicion = 0;
		        this.casillaExtra = 0;
		    }

		    public String getNombre() {
		        return nombre;
		    }

		    public int getPosicion() {
		        return posicion;
		    }

		    public void setPosicion(int posicion) {
		        this.posicion = posicion;
		    }

		    public int getCasillaExtra() {
		        return casillaExtra;
		    }

		    public void activarCasillaExtra(int numTurnos) {
		        casillaExtra += numTurnos;
		    }
		}
	}

}
