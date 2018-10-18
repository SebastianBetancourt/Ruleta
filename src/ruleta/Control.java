package ruleta;

import java.util.ArrayList;

import vista.Moneda;

public class Control {
	
	/**
	 * Dinero del jugador
	 */
	private int balance;
	
	public Control() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param monedas
	 * @param numero El número que cayó en la ruleta después de girarla
	 */
	public void calcularResultado(ArrayList<Moneda> monedas, int numero) {
		for(Moneda moneda : monedas) {
			
		}
	}
	
	/**
	 * @param numero El número de la ruleta del que se quiere saber el color
	 * @return true si y sólo si el numero es rojo en la ruleta
	 */
	public boolean esElNumeroRojo(int numero) {
		boolean esRojo;
		if(numero < 11 || (numero > 19 && numero < 29)) {
			esRojo = true;
		} else {
			esRojo = false;
		}
		
		if(numero % 2 == 0) {
			esRojo = !esRojo;
		}
		
		return esRojo;
	}

}