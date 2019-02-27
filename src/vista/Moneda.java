package vista;

/*
 * Clase: Moneda.java
 * 
 * Responsabilidad:
 * Se encarga de almacenar los objetos de tipo moneda, allí está definida la lógica de cada moneda, ademas de definir su movimiento
 * y sus valores internos.
 * 
 * Colaboraciones:
 * Mesa.java
 * 
 * Autores:
 * @author Joan Sebastian Betancourt-(1744202)
 * @author Nicolas Lasso Jaramillo-(1740395)
 * @author Jorge Eduardo Mayor Fernández-(1738661)
 * @since 28-9-2018
 * @version 1.0.15
 */

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Moneda extends JLabel {

	/**
	 * Tipos de apuesta en los que la moneda puede estar
	 */
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
	public static final int ERROR = 13;
	private static final float[] premios = { 2, 2, 2, 3, 3, 1.5f, 1.5f, 6, 9,
			12, 18, 36, 36, 1 };
	private static final String[] tipos = { "COLOR", "PAR", "PASE", "DOCENA",
			"COLUMNA", "DOSDOCENAS", "DOSCOLUMNAS", "SEISENA", "CUADRO",
			"TRANSVERSAL", "CABALLO", "PLENO", "CERO", "ERROR" };

	public static final int ANCHO = 35;
	public static final int ALTO = 35;

	/**
	 * Posibles valores de las fichas, de menor a mayor
	 */
	

	private Point puntoDeAgarre;
	
	private Point posAntesDeMoverse;
	
	private float premio;
	
	private int tipoDeApuesta;
	
	private final Mesa mesa;
	
	private int valor;
	
	private ArrayList<Integer> valoresAApostar;
	/*
	 * 
	 */
	public Moneda(Mesa mesa, int xInicial, int yInicial, int valor) {
		super();
		this.mesa = mesa;
		this.valor = valor;
		this.valoresAApostar = new ArrayList<Integer>();
		this.setBounds(xInicial, yInicial, ANCHO, ALTO);
		this.setIcon(new ImageIcon("img/monedas/"+valor+".png"));
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent e) {
				if(Mesa.segundos > 0) {
				int cambioX = e.getX() - puntoDeAgarre.x;
				int cambioY = e.getY() - puntoDeAgarre.y;
				int nuevoX = Moneda.this.getX() + cambioX;
				int nuevoY = Moneda.this.getY() + cambioY;
				if ((nuevoX > Mesa.DIMENSIONES_CONTENEDOR_MONEDAS.getWidth()) || (nuevoX < 0)) {
					nuevoX = Moneda.this.getX();
				}
				if ((nuevoY > Mesa.DIMENSIONES_CONTENEDOR_MONEDAS.getHeight()) || (nuevoY < 0)) {
					nuevoY = Moneda.this.getY();
				}
				Moneda.this.setLocation(nuevoX, nuevoY);
				Moneda.this.repaint();
				}
			}

			public void mouseMoved(MouseEvent arg0) {
			}
		});

		this.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				puntoDeAgarre = e.getPoint();
				posAntesDeMoverse = Moneda.this.getLocation();

			}
			
			public void mouseReleased(MouseEvent arg0) {
				System.out.println(puntoDeAgarre);
				if(determinarTipoApuesta()) {
					puntoDeAgarre = Moneda.this.getLocation();
				}else if(Moneda.this.getLocation().getY() > Mesa.DIMENSIONES_CONTENEDOR_NUMEROS.getHeight()){
					Moneda.this.mesa.eliminarMoneda(Moneda.this);
				}
				else {
					Moneda.this.setLocation(posAntesDeMoverse);
					Moneda.this.repaint();
				}
				determinarValoresAApostar();
			}
		});
	}
	
	/*
	 * Gets the Valores a apostar
	 */
	public ArrayList<Integer> getValoresAAPostar() {
		return valoresAApostar;
	}
    
	/*
	 * Gets the premio
	 */
	public float getPremio() {
		return premio;
	}
	
	/*
	 * Sets Tipo de apuesta
	 * 
	 * Setea el tipo de apuesta que tiene la moneda
	 */
	private void setTipoDeApuesta(int tipoDeApuesta) {
		if (tipoDeApuesta == Moneda.ERROR) {
			// TODO manejar casos de error de apuesta
			premio = 1;
			this.tipoDeApuesta = tipoDeApuesta;
		} else {
			premio = premios[tipoDeApuesta];
			this.tipoDeApuesta = tipoDeApuesta;
		}
	}
	
	/*
	 * Gets the Tipo de apuesta
	 */
	public int getTipoDeApuesta() {
		return tipoDeApuesta;
	}
	
	/*
	 * Sets the valor
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}
	
	/*
	 * Gets the valor
	 */
	public int getValor() {
		return valor;
	}

	/*
	 * Gets el centro de la moneda
	 */
	public Point getCentro() {
		return new Point((getX() + (ANCHO / 2)), (getY() + (ALTO / 2)));
	}

	/**
	 * @return La fila de la casilla en la que se encuentra
	 */
	public int getFilaCasilla() {
		int diferencia = getCentro().y - Mesa.ORIGEN_TABLERO.y;
		int fila = -1;
		if (diferencia >= 0) {
			fila = (int) (diferencia / Mesa.DIMENSIONES_CASILLA.getHeight());
		}
		return fila;
	}
	
	/*
	 * @returns la columna de la casilla en la que fue depositada la moneda
	 */
	public int getColumnaCasilla() {
		int diferencia = getCentro().x - Mesa.ORIGEN_TABLERO.x;
		int columna = -1;
		if (diferencia >= 0) {
			columna = (int) (diferencia / Mesa.DIMENSIONES_CASILLA.getWidth());
		}
		return columna;
	}

	/**
	 * @return La fila de la región en la que se encuentra, relativa a la
	 *         casilla. Está entre 0 y 3 (exclusivo).
	 */
	public int getFilaRegion() {
		int region;
		int posRelativa = Math.floorMod(
				(int) (getCentro().y - Mesa.ORIGEN_TABLERO.y),
				(int) Mesa.DIMENSIONES_CASILLA.getHeight());
		if (posRelativa < Mesa.DIMENSIONES_LATERAL_REGION.getHeight()) {
			region = 0;
		} else if (posRelativa < Mesa.DIMENSIONES_CENTRO_REGION.getHeight()
				+ Mesa.DIMENSIONES_LATERAL_REGION.getHeight()) {
			region = 1;
		} else {
			region = 2;
		}
		return region;
	}

	/**
	 * @return La columna de la región en la que se encuentra, relativa a la
	 *         casilla. Está entre 0 y 3 (exclusivo).
	 */
	public int getColumnaRegion() {
		int region;
		int posRelativa = Math.floorMod(
				(int) (getCentro().x - Mesa.ORIGEN_TABLERO.x),
				(int) Mesa.DIMENSIONES_CASILLA.getWidth());

		if (posRelativa < Mesa.DIMENSIONES_LATERAL_REGION.getWidth()) {
			region = 0;
		} else if (posRelativa < Mesa.DIMENSIONES_CENTRO_REGION.getWidth()
				+ Mesa.DIMENSIONES_LATERAL_REGION.getWidth()) {
			region = 1;
		} else {
			region = 2;
		}
		return region;
	}

	/*
	 * Determina el tipo de la apuesta que realiza la moneda, dependiendo de la fila y la
	 * columna en la que se encuentra, ademas de la fila y la columna de la region dentro de
	 * la casilla
	 */
	public boolean determinarTipoApuesta() {
		int filaCasilla = this.getFilaCasilla();
		int columnaCasilla = this.getColumnaCasilla();
		int filaRegion = this.getFilaRegion();
		int columnaRegion = this.getColumnaRegion();

		if (filaCasilla > 2 && (columnaCasilla == 0 || columnaCasilla == 13)) {
			this.setTipoDeApuesta(Moneda.ERROR);
			return false;
		} else if (filaCasilla == 4 && columnaCasilla > 4 && columnaCasilla < 9) {
			this.setTipoDeApuesta(Moneda.COLOR);
		} else if (filaCasilla == 4
				&& ((columnaCasilla > 2 && columnaCasilla < 5) || (columnaCasilla > 8 && columnaCasilla < 11))) {
			this.setTipoDeApuesta(Moneda.PAR);
		} else if (filaCasilla == 4
				&& ((columnaCasilla > 0 && columnaCasilla < 3) || (columnaCasilla > 10 && columnaCasilla < 13))) {
			this.setTipoDeApuesta(Moneda.PASE);
		} else if (filaCasilla == 3 && filaRegion != 0
				&& !((columnaRegion == 2 && (columnaCasilla == 4 || columnaCasilla == 8)) || (columnaRegion == 0 && (columnaCasilla == 5 || columnaCasilla == 9)))
				&& columnaCasilla > 0 && columnaCasilla < 13) {
			this.setTipoDeApuesta(Moneda.DOCENA);
		} else if (columnaCasilla == 13
				&& filaCasilla < 3
				&& !((filaRegion == 2 && filaCasilla != 2) || (filaRegion == 0 && filaCasilla != 0))) {
			this.setTipoDeApuesta(Moneda.COLUMNA);
		} else if (filaCasilla == 3
				&& filaRegion != 0
				&& ((columnaRegion == 2 && (columnaCasilla == 4 || columnaCasilla == 8)) || (columnaRegion == 0 && (columnaCasilla == 5 || columnaCasilla == 9)))) {
			this.setTipoDeApuesta(Moneda.DOSDOCENAS);
		} else if (columnaCasilla == 13
				&& filaCasilla < 3
				&& ((filaRegion == 2 && filaCasilla != 2) || (filaRegion == 0 && filaCasilla != 0))) {
			this.setTipoDeApuesta(Moneda.DOSCOLUMNAS);
		} else if (columnaCasilla > 0
				&& columnaCasilla < 13
				&& columnaRegion != 1
				&& ((filaCasilla == 3 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2))
				&& !(columnaCasilla == 1 && columnaRegion == 0)
				&& !(columnaCasilla == 12 && columnaRegion == 2)) {
			this.setTipoDeApuesta(Moneda.SEISENA);
		} else if (columnaCasilla > 0 && columnaCasilla < 13 && filaCasilla < 3
				&& (filaRegion != 1 && columnaRegion != 1)
				&& !(columnaCasilla == 1 && columnaRegion == 0)
				&& !(filaCasilla == 0 && filaRegion == 0)
				&& !(filaCasilla == 2 && filaRegion == 2)
				&& !(columnaCasilla == 12 && columnaRegion == 2)) {
			this.setTipoDeApuesta(Moneda.CUADRO);
		} else if (
				// caso transversales superiores
				((filaCasilla == 0 && filaRegion == 0 && (columnaRegion == 1
						|| (columnaCasilla == 1 && columnaRegion == 0) || (columnaCasilla == 12 && columnaRegion == 2)))
				// caso transversales inferiores
				|| (filaCasilla == 3 && filaRegion == 0 && (columnaRegion == 1
				|| (columnaCasilla == 1 && columnaRegion == 0) || (columnaCasilla == 12 && columnaRegion == 2)))
				|| (filaCasilla == 2 && filaRegion == 2 && (columnaRegion == 1
						|| (columnaCasilla == 1 && columnaRegion == 0) || (columnaCasilla == 12 && columnaRegion == 2))))
				// transversales con cero
				|| ((columnaCasilla == 0
						&& (((filaCasilla == 0 || filaCasilla == 1) && (filaRegion == 2 && columnaRegion == 2))) || ((filaCasilla == 1 || filaCasilla == 2) && (filaRegion == 0 && columnaRegion == 2))))
				|| ((columnaCasilla == 1
						&& (((filaCasilla == 0 || filaCasilla == 1) && (filaRegion == 2 && columnaRegion == 0))) || ((filaCasilla == 1 || filaCasilla == 2) && (filaRegion == 0 && columnaRegion == 0))))) {
			this.setTipoDeApuesta(Moneda.TRANSVERSAL);
		} else if (((columnaCasilla > 0) || (columnaCasilla == 0 && columnaRegion == 2))
				&& columnaCasilla < 13
				&& filaCasilla < 3
				&& ((filaRegion == 1 && columnaRegion != 1) || (filaRegion != 1 && columnaRegion == 1))
				&& !(filaCasilla == 0 && filaRegion == 0)
				&& !(filaCasilla == 2 && filaRegion == 2)
				&& !(columnaCasilla == 12 && columnaRegion == 2)) {
			this.setTipoDeApuesta(Moneda.CABALLO);
		} else if (filaCasilla < 3 && columnaCasilla > 0 && columnaCasilla < 13
				&& filaRegion == 1 && columnaRegion == 1) {
			this.setTipoDeApuesta(Moneda.PLENO);
		} else if (columnaCasilla == 0
				&& filaCasilla < 3
				&& (columnaRegion < 2 || (filaCasilla == 0 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2))) {
			this.setTipoDeApuesta(Moneda.CERO);
		} else {
			this.setTipoDeApuesta(Moneda.ERROR);
			return false;
		}
		
		return true;

	}

	/**
	 * @param numero
	 *            El número de la ruleta del que se quiere saber el color
	 * @return true si y sólo si el numero es rojo en la ruleta
	 */
	private boolean esElNumeroRojo(int numero) {
		boolean esRojo = (numero < 11 || (numero > 19 && numero < 29));
		
		if (numero % 2 == 0 || numero == 19) {
			esRojo = !esRojo;
		}
		
		return esRojo;
	}

	/*
	 * Determina los valores por los que se apuesta al colocar la moneda
	 * Agrega estos valores a un ArrayList en el valor interno de la moneda
	 */
	private void determinarValoresAApostar() {
		int filaCasilla = this.getFilaCasilla();
		int columnaCasilla = this.getColumnaCasilla();
		int filaRegion = this.getFilaRegion();
		int columnaRegion = this.getColumnaRegion();
		
		int numero = (columnaCasilla * 3 - filaCasilla);
		
		valoresAApostar.clear();
		switch (tipoDeApuesta) {
			case Moneda.COLOR:
				if (columnaCasilla < 7) {
					for (int i = 1; i < 37; i++) {
						if (esElNumeroRojo(i)) {
							valoresAApostar.add(i);
						}
					}
				} else {
					for (int i = 1; i < 37; i++) {
						if (!esElNumeroRojo(i)) {
							valoresAApostar.add(i);
						}
					}
				}
				break;
				
			case Moneda.PAR:
				if (columnaCasilla < 5) {
					for (int i = 2; i < 37; i = i + 2)
						valoresAApostar.add(i);
				} else {
					for (int i = 1; i < 37; i = i + 2)
						valoresAApostar.add(i);
				}
				break;
				
			case Moneda.PASE:
				if (columnaCasilla < 3) {
					for (int i = 1; i < 19; i++)
						valoresAApostar.add(i);
				} else {
					for (int i = 18; i < 37; i++)
						valoresAApostar.add(i);
				}
				break;
				
			case Moneda.DOCENA:
				for (int index = 1; index < 13; index++)
					valoresAApostar.add(12 * ((columnaCasilla - 1) / 4) + index);
				break;
	
			case Moneda.COLUMNA:
				for (int i = 1; i <= 12; i++) {
					valoresAApostar.add(i * 3 - filaCasilla);
				}
				break;
				
			case Moneda.DOSDOCENAS:
				if (columnaCasilla < 6) {
					for (int i = 1; i <= 24; i++) {
						valoresAApostar.add(i);
					}
				} else {
					for (int i = 12; i <= 36; i++) {
						valoresAApostar.add(i);
					}
				}
				break;
				
			case Moneda.DOSCOLUMNAS:
				if (filaCasilla == 0 || (filaCasilla == 1 && filaRegion == 0)) {
					for (int i = 1; i < 13; i++) {
						valoresAApostar.add(i * 3 - 1);
						valoresAApostar.add(i * 3);
					}
				} else {
					for (int i = 1; i < 13; i++) {
						valoresAApostar.add(i * 3 - 2);
						valoresAApostar.add(i * 3 - 1);
					}
				}
				break;
				
			case Moneda.SEISENA:
				
				int n=0;
				
				if(columnaRegion == 0)
					n++;
				
				for (int i = -2; i <= 3; i++) {
					valoresAApostar.add((columnaCasilla - n) * 3 + i);
				}
				
				break;
				
			case Moneda.CUADRO:
				if (columnaRegion == 0 && filaRegion == 0)
					valoresAApostar.addAll(Arrays.asList(new Integer[] {
							numero - 3, numero - 2, numero, numero + 1 }));
				else if (columnaRegion == 0 && filaRegion == 2)
					valoresAApostar.addAll(Arrays.asList(new Integer[] {
							numero - 4, numero - 3, numero - 1, numero }));
				else if (columnaRegion == 2 && filaRegion == 2)
					valoresAApostar.addAll(Arrays.asList(new Integer[] {
							numero - 1, numero, numero + 2, numero + 3 }));
				else
					valoresAApostar.addAll(Arrays.asList(new Integer[] { numero,
							numero + 1, numero + 3, numero + 4 }));
				break;
				
			case Moneda.TRANSVERSAL:
				if (filaCasilla == 3 || (filaCasilla == 2 && filaRegion == 2)) {
					valoresAApostar.addAll(Arrays.asList(new Integer[] { columnaCasilla * 3 - 2,
							columnaCasilla * 3 - 1, columnaCasilla * 3 }));
				} else if (filaCasilla == 0
						|| (filaCasilla == 1 && filaRegion == 0)) {
					valoresAApostar
							.addAll(Arrays.asList(new Integer[] { 0, 2, 3 }));
				} else {
					valoresAApostar
							.addAll(Arrays.asList(new Integer[] { 0, 1, 2 }));
				}
				break;
				
			case Moneda.CABALLO:
				//CABALLOS QUE INVOLUCRAN EL CERO
				if(columnaCasilla == 0)
					valoresAApostar.addAll(Arrays.asList(new Integer[] { 0,
							3 - filaCasilla}));
				else if (columnaCasilla == 1 && columnaRegion == 0)
					valoresAApostar.addAll(Arrays.asList(new Integer[] { 0,
							numero}));
				//CABALLOS NORMALES
				else if (columnaRegion == 0)
					valoresAApostar.addAll(Arrays.asList(new Integer[] {numero - 3, numero }));
				else if (columnaRegion == 2)
					valoresAApostar.addAll(Arrays.asList(new Integer[] { numero,
							numero + 3 }));
				else if (filaRegion == 0)
					valoresAApostar.addAll(Arrays.asList(new Integer[] { numero,
							numero + 1 }));
				else
					valoresAApostar.addAll(Arrays.asList(new Integer[] { numero -1,
							numero}));
				break;
				
			case Moneda.PLENO:
				valoresAApostar.add(numero);
				break;
				
			case Moneda.CERO:
				valoresAApostar.add(0);
				break;
		}
		
		System.out.print(filaCasilla + "," + filaRegion + ":"
				+ columnaCasilla + "," + columnaRegion + " ");
		System.out.print(tipos[tipoDeApuesta]
				+ " = " + tipoDeApuesta + "\n\tvaloresAApostar: \n\t");
		for (int n : valoresAApostar) {
			System.out.print(n + " ");
		}
		System.out.print("\n");
	}
	
	/*
	 * Busca entre los valores por los que apostó la moneda si está el valor
	 * que salió de la ruleta.
	 */
	public boolean estaEnLaLista(int valorObtenido)
	{
		return this.valoresAApostar.contains(valorObtenido);
		}
}