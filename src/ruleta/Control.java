package ruleta;

/*
 * Clase: Control.java
 * 
 * Responsabilidad:
 * Se encarga de controlar la lógica y el balance del usuario al realizar una apuesta.
 * 
 * Colaboraciones:
 * Ninguna
 * 
 * Autores:
 * @author Joan Sebastian Betancourt-(1744202)
 * @author Nicolas Lasso Jaramillo-(1740395)
 * @author Jorge Eduardo Mayor Fernández-(1738661)
 * @since 28-9-2018
 * @version 1.0.11
 */

import java.util.ArrayList;

import vista.Moneda;

public class Control {
	
	private static final int BALANCE_INICIAL = 200;
	/**
	 * Dinero del jugador
	 */
	private int balance;
	
	/*
	 * Constructor de Control
	 */
	public Control() {
		this.balance = BALANCE_INICIAL;
	}
	
	/*
	 * Gets the Balance
	 * @returns balance
	 */
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
	
	/*
	 * Actualiza el valor del balance
	 */
	public void sumarABalance(int valor){
		this.balance += valor;
	}
}