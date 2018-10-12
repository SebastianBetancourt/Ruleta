package vista;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Mesa extends JPanel {


	public static final int ANCHO_MESA = 800;
	public static final int ALTO_MESA = 300;
	public static final int ANCHO_CASILLA = 30;
	public static final int ALTO_CASILLA = 30;
	public static final int ANCHO_REGION = ANCHO_CASILLA / 3;
	public static final int ALTO_REGION = ALTO_CASILLA / 3;

	
	/**
	 * Conjunto de monedas que están en la mesa
	 */
	private ArrayList<Moneda> monedas;
	
	public Mesa() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return El número que cayó de la ruleta después de girarla
	 */
	private int rodarRuleta() {
		//TODO
		return 4;
	}
	

	
	
}

