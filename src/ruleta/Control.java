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
			determinarTipoApuesta(moneda);
		}
	}

	private void determinarNumerosApostados(Moneda moneda) {
		switch(moneda.getTipoDeApuesta()){
		case Moneda.COLOR: break;
		case Moneda.PAR: break;
		case Moneda.PASE: break;
		case Moneda.DOCENA: break;
		case Moneda.COLUMNA: break;
		case Moneda.DOSDOCENAS: break;
		case Moneda.DOSCOLUMNAS: break;
		case Moneda.SEISENA: break;
		case Moneda.CUADRO: break;
		case Moneda.TRANSVERSAL: break;
		case Moneda.CABALLO: break;
		case Moneda.PLENO: break;
		case Moneda.CERO: break;
		}
	}
	
	private void determinarTipoApuesta(Moneda moneda) {
		int filaCasilla = moneda.getFilaCasilla();
		int colCasilla = moneda.getColumnaCasilla();
		int filaRegion = moneda.getFilaRegion();
		int colRegion = moneda.getColumnaRegion();
		if(filaCasilla == 4) {
			if(colCasilla > 4  && colCasilla < 9){
				moneda.setTipoDeApuesta(Moneda.COLOR);
			} else if(colCasilla == 3 || colCasilla == 4 || colCasilla == 9 || colCasilla == 10) {
				moneda.setTipoDeApuesta(Moneda.PAR);
			} else if(colCasilla == 1 || colCasilla == 2 || colCasilla == 11 || colCasilla == 12) {
				moneda.setTipoDeApuesta(Moneda.PASE);
			}
		} else if(filaCasilla == 3) {
			if(filaRegion == 0){
				if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 13) && colRegion == 2)) {
					moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
				} else if(colRegion != 1 && colCasilla != 0) {
					moneda.setTipoDeApuesta(Moneda.SEISENA);
				}
			}else if(((filaCasilla== 8 || filaCasilla == 4) && filaRegion == 2) || ((filaCasilla== 9 || filaCasilla == 5) && filaRegion == 0)) {
				moneda.setTipoDeApuesta(Moneda.DOSDOCENAS);
			}else if(colCasilla != 0 && colCasilla != 14) {
				moneda.setTipoDeApuesta(Moneda.DOCENA);
			}
			
			moneda.setTipoDeApuesta(Moneda.DOCENA);
		}else if(filaCasilla == 0 && filaRegion == 0){
				if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
					moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
				} else if(colCasilla != 0) {
					moneda.setTipoDeApuesta(Moneda.SEISENA);
				}
		}else if(filaCasilla == 2 && filaRegion == 2){
			if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
			} else if(colCasilla != 0) {
				moneda.setTipoDeApuesta(Moneda.SEISENA);
			}
		}else if(colCasilla == 0 && (filaCasilla == 0 || filaCasilla == 1)){
			if((filaRegion == 2 && colRegion == 2) || (filaRegion == 2 && colRegion == 0)){
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
			}
		}else if(colCasilla == 1 && (filaCasilla == 1 || filaCasilla == 2)){
			if((filaRegion == 0 && colRegion == 0) || (filaRegion == 0 && colRegion == 2)){
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
			}
		}else if(colCasilla == 13 && filaCasilla < 3){
			if(filaRegion  == 1 || (filaCasilla == 0 && filaRegion == 0)|| (filaCasilla == 2 && filaRegion == 2)) {
				moneda.setTipoDeApuesta(Moneda.COLUMNA);
			}else {
				moneda.setTipoDeApuesta(Moneda.DOSCOLUMNAS);
			}
		}else if(colCasilla == 0 && filaCasilla < 3) {
			moneda.setTipoDeApuesta(Moneda.CERO);
		}else if(colCasilla == 12 && colRegion == 2 && (filaRegion == 2 || filaRegion == 0)) {
			moneda.setTipoDeApuesta(Moneda.CABALLO);
		}else if((filaRegion == 2 || filaRegion == 0) && (colRegion == 2 || filaRegion == 0)) {
			moneda.setTipoDeApuesta(Moneda.CUADRO);
		}else if((filaRegion == 0 && colRegion == 1) ||(filaRegion == 2 && colRegion == 1) ||(filaRegion == 1 && colRegion == 0) ||(filaRegion == 1 && colRegion == 2)) {
			moneda.setTipoDeApuesta(Moneda.CABALLO);
		}else if(filaRegion == 1 && colRegion == 1) {
			moneda.setTipoDeApuesta(Moneda.PLENO);
		}else {
			moneda.setTipoDeApuesta(Moneda.ERROR);
		}
	}
	
	/**
	 * @param numero El número de la ruleta del que se quiere saber el color
	 * @return true si y sólo si el numero es rojo en la ruleta
	 */
	private boolean esElNumeroRojo(int numero) {
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
