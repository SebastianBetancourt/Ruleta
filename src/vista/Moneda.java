package vista;

public class Moneda {

	public static final int COLOR = 0;
	public static final int PAR = 1;
	public static final int PASE = 2;
	public static final int DOCENA = 3;
	public static final int COLUMNA = 4;
	public static final int DOSDOCENAS = 5;
	public static final int DOSCOLUMNAS = 6;
	public static final int SEISENA = 7;
	public static final int CUADRO = 8;
	public static final int TRANSVERSAL = 9;
	public static final int CABALLO = 10;
	public static final int PLENO = 11;

		
	/**
	 * Posibles valores de las fichas, de menor a mayor
	 */
	public static final int[] VALORES = {1, 25, 50};
	
	/**
	 * Componente X de la posición del centro de la moneda en la mesa 
	 */
	private int posicionX;
	/**
	 * Componente Y de la posición del centro de la moneda en la mesa
	 */
	private int posicionY;
	/**
	 * Indicador del valor de la moneda, de acuerdo a VALORES
	 */
	private int valor;
	
	public float getPremio() {
		return premio;
	}

	public void setPremio(float premio) {
		this.premio = premio;
	}

	public int getTipoDeApuesta() {
		return tipoDeApuesta;
	}

	public void setTipoDeApuesta(int tipoDeApuesta) {
		this.tipoDeApuesta = tipoDeApuesta;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	private float premio;
	private int tipoDeApuesta;
	
	public Moneda() {
		// TODO Auto-generated constructor stub
	}

	public int getPosicionX() {
		return posicionX;
	}

	public int getPosicionY() {
		return posicionY;
	}

	public int getValor() {
		return valor;
	}
	
	/**
	 * @return La fila de la casilla en la que se encuentra
	 */
	public int getFilaCasilla() {
		return posicionY / Mesa.ANCHO_CASILLA;
	}
	
	public int getColumnaCasilla() {
		return posicionX / Mesa.ALTO_CASILLA;
	}
	
	/**
	 * @return La fila de la región en la que se encuentra, relativa a la casilla. Está entre 0 y 3.
	 */
	public int getFilaRegion() {
		return (posicionY % Mesa.ANCHO_CASILLA) / Mesa.ANCHO_REGION;
	}
	
	public int getColumnaRegion() {
		return (posicionX % Mesa.ALTO_CASILLA) / Mesa.ALTO_REGION;
	}

}
