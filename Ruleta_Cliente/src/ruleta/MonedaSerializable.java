package ruleta;

import java.io.Serializable;
import java.util.ArrayList;

public class MonedaSerializable implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int propietario;
	float premio;
	int tipoDeApuesta;
	public int valor;
	ArrayList<Integer> valoresAApostar;
	public int x;
	public int y;
			
	public MonedaSerializable(int propietario,	float premio, int tipoDeApuesta, int valor,	ArrayList<Integer> valoresAApostar, int x, int y) {
		this.propietario = propietario;
		this.premio = premio;
		this.tipoDeApuesta = tipoDeApuesta;
		this.valor = valor;
		this.valoresAApostar = valoresAApostar;
		this.x = x;
		this.y = y;
		
	}
	
	public int getValor() {
		return valor;
	}
	
	public float getPremio() {
		return premio;
	}
	
	/*
	 * Busca entre los valores por los que apostó la moneda si está el valor
	 * que salió de la ruleta.
	 */
	public boolean estaEnLaLista(int valorObtenido) {
		return this.valoresAApostar.contains(valorObtenido);
	}
}// Fin clase MonedaSerializable
