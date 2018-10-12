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
	
	private void determinarTipoApuesta(Moneda moneda) {
		int filaCasilla = moneda.getFilaCasilla();
		int colCasilla = moneda.getColumnaCasilla();
		int filaRegion = moneda.getFilaRegion();
		int colRegion = moneda.getColumnaRegion();
		if(filaCasilla == 4) {
			moneda.setPremio(2);
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
					moneda.setPremio(12);
				} else if(colRegion != 1 && colCasilla != 0) {
					moneda.setTipoDeApuesta(Moneda.SEISENA);
					moneda.setPremio(6);
				}
			}else if(((filaCasilla== 8 || filaCasilla == 4) && filaRegion == 2) || ((filaCasilla== 9 || filaCasilla == 5) && filaRegion == 0)) {
				moneda.setTipoDeApuesta(Moneda.DOSDOCENAS);
				moneda.setPremio((float) 1.5);
			}else if(colCasilla != 0 && colCasilla != 14) {
				moneda.setTipoDeApuesta(Moneda.DOCENA);
				moneda.setPremio(3);
			}
			
			moneda.setTipoDeApuesta(Moneda.DOCENA);
			moneda.setPremio(3);
		}else if(filaCasilla == 0 && filaRegion == 0){
				if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
					moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
					moneda.setPremio(12);
				} else if(colCasilla != 0) {
					moneda.setTipoDeApuesta(Moneda.SEISENA);
					moneda.setPremio(6);
				}
		}else if(filaCasilla == 2 && filaRegion == 2){
			if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
				moneda.setPremio(12);
			} else if(colCasilla != 0) {
				moneda.setTipoDeApuesta(Moneda.SEISENA);
				moneda.setPremio(6);
			}
		}else if(colCasilla == 0 && (filaCasilla == 0 || filaCasilla == 1)){
			if((filaRegion == 2 && colRegion == 2) || (filaRegion == 2 && colRegion == 0)){
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
				moneda.setPremio(12);
			}
		}else if(colCasilla == 1 && (filaCasilla == 1 || filaCasilla == 2)){
			if((filaRegion == 0 && colRegion == 0) || (filaRegion == 0 && colRegion == 2)){
				moneda.setTipoDeApuesta(Moneda.TRANSVERSAL);
				moneda.setPremio(12);
			}
		}else if(colCasilla == 13 && filaCasilla < 3){
			if(filaRegion  == 1 || (filaCasilla == 0 && filaRegion == 0)|| (filaCasilla == 2 && filaRegion == 2)) {
				moneda.setTipoDeApuesta(Moneda.COLUMNA);
				moneda.setPremio(3);
			}else {
				moneda.setTipoDeApuesta(Moneda.DOSCOLUMNAS);
				moneda.setPremio((float) 1.5);
			}
		}else if(colCasilla == 0 && filaCasilla < 3) {
			moneda.setTipoDeApuesta(Moneda.CERO);
			moneda.setPremio(36);
		}else if(colCasilla == 12 && colRegion == 2 && (filaRegion == 2 || filaRegion == 0)) {
			moneda.setTipoDeApuesta(Moneda.CABALLO);
			moneda.setPremio(18);
		}else if((filaRegion == 2 || filaRegion == 0) && (colRegion == 2 || filaRegion == 0)) {
			moneda.setTipoDeApuesta(Moneda.CUADRO);
			moneda.setPremio(9);
		}else if((filaRegion == 0 && colRegion == 1) ||(filaRegion == 2 && colRegion == 1) ||(filaRegion == 1 && colRegion == 0) ||(filaRegion == 1 && colRegion == 2)) {
			moneda.setTipoDeApuesta(Moneda.CABALLO);
			moneda.setPremio(18);
		}else if(filaRegion == 1 && colRegion == 1) {
			moneda.setTipoDeApuesta(Moneda.PLENO);
			moneda.setPremio(36);
		}else {
			moneda.setTipoDeApuesta(Moneda.ERROR);
			moneda.setPremio(1);
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
