package vista;

import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Moneda {

	/**
	 * Tipos de apuesta en los que la moneda puede estar
	 */
	public static final int ERROR = -1;
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
	public static final int CERO = 12;
	private static final float[] premios = {2, 2, 2, 3, 3, 1.5f, 1.5f, 6, 9, 12, 18, 36, 36};
	
	public static final int ANCHO = 70;
	public static final int ALTO = 70;

		
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
	private ArrayList<Integer> valoresAApostar;

	public ArrayList<Integer> getValoresAAPostar() {
		return valoresAApostar;
	}
	
	public float getPremio() {
		return premio;
	}

	public int getTipoDeApuesta() {
		return tipoDeApuesta;
	}

	public void setTipoDeApuesta(int tipoDeApuesta) {
		premio = premios[tipoDeApuesta];
		this.tipoDeApuesta = tipoDeApuesta;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	private float premio;
	private int tipoDeApuesta;
	
	public Moneda() {
		// TODO Auto-generated constructor stub
		posicionX = 500;
		posicionY = 500;
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
		return (int) (posicionY / Mesa.DIMENSIONES_CASILLA.getWidth());
	}
	
	public int getColumnaCasilla() {
		return (int) (posicionX / Mesa.DIMENSIONES_CASILLA.getHeight());
	}
	
	/**
	 * @return La fila de la región en la que se encuentra, relativa a la casilla. Está entre 0 y 3 (exclusivo).
	 */
	public int getFilaRegion() {
		int region;
		int posRelativa = (int) ((posicionY-Mesa.ORIGEN_TABLERO.getWidth()) % Mesa.DIMENSIONES_CASILLA.getWidth());
		if(posRelativa < Mesa.DIMENSIONES_LATERAL_CASILLA.getWidth()) {
			region = 0;
		}else if(posRelativa < 2*Mesa.DIMENSIONES_LATERAL_CASILLA.getWidth()) {
			region = 1;
		}
		else {
			region = 2;
		}
		return region;
	}
	
	public int getColumnaRegion() {
		int region;
		int posRelativa = (int) ((posicionX-Mesa.ORIGEN_TABLERO.getHeight()) % Mesa.DIMENSIONES_CASILLA.getHeight());
		if(posRelativa < Mesa.DIMENSIONES_LATERAL_CASILLA.getHeight()) {
			region = 0;
		}else if(posRelativa < 2*Mesa.DIMENSIONES_LATERAL_CASILLA.getHeight()) {
			region = 1;
		}
		else {
			region = 2;
		}
		return region;
	}

	public Icon getImagen() {
		// TODO
		return new ImageIcon("img/moneda.png");
	}

}
