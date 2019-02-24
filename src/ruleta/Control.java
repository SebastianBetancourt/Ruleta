package ruleta;

import java.util.ArrayList;

import vista.Moneda;

public class Control {
	
	/**
	 * Dinero del jugador
	 */
	private int balance;
	
	public Control() {
		this.balance = 1500;
	}
	
	/**
	 * @param monedas
	 * @param numero El número que cayó en la ruleta después de girarla
	 */
	public void calcularResultado(ArrayList<Moneda> monedas, int numero) {
		
		for(Moneda moneda : monedas) {
			if(moneda.estaEnLaLista(numero)) {
				balance +=  moneda.getValor()*moneda.getPremio();
				
			}
		}
		monedas.clear();
	}
}