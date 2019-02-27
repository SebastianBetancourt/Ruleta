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
	
	public int getBalance(){
		return balance;
	}
	
	/**
	 * @param monedas
	 * @param numero El número que cayó en la ruleta después de girarla
	 */
	public int calcularResultado(ArrayList<Moneda> monedas, int numero) {
		int ganancia = 0;
		for(Moneda moneda : monedas) {
			if(moneda.estaEnLaLista(numero)) {
				balance +=  moneda.getValor()*moneda.getPremio();
				ganancia +=  moneda.getValor()*(moneda.getPremio()-1); 
			}else {
				ganancia -= moneda.getValor();
			}
		}
		
		return ganancia;
	}
	
	public void sumarABalance(int valor){
		this.balance += valor;
	}
}