package vista;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Moneda extends JLabel {

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
	

	public Point posAntesDeMoverse;
	
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
	
	public Moneda(int xInicial, int yInicial) {
		super();
		this.setBounds(xInicial, yInicial, ANCHO, ALTO);
		this.setIcon(new ImageIcon("img/moneda.png"));
		this.addMouseMotionListener(new MouseMotionListener() {
	
			public void mouseDragged(MouseEvent e) {
				int cambioX = e.getX() - posAntesDeMoverse.x;
				int cambioY = e.getY() - posAntesDeMoverse.x;
				int nuevoX = Moneda.this.getX()+cambioX;
				int nuevoY = Moneda.this.getY()+cambioY;
				if(nuevoX > Mesa.DIMENSIONES_MESA.getWidth() || nuevoX < 0) {
					nuevoX = Moneda.this.getX();
				}
				if(nuevoY > Mesa.DIMENSIONES_MESA.getHeight() || nuevoY < 0) {
					nuevoY = Moneda.this.getY();
				}
				Moneda.this.setLocation(nuevoX, nuevoY);
				Moneda.this.repaint();
				
			}

			public void mouseMoved(MouseEvent arg0) {
			}
			
		});

		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e) {
				posAntesDeMoverse = e.getPoint();
				
			}

			public void mouseReleased(MouseEvent arg0) {
				posAntesDeMoverse = Moneda.this.getLocation();
				determinarTipoApuesta();
				System.out.println(tipoDeApuesta);
			}
			
		});
	
	}

	public int getValor() {
		return valor;
	}
	
	public Point getCentro() {
		return new Point((getY()+(ALTO/2)),(getX()+(ANCHO/2)));
	}
	
	/**
	 * @return La fila de la casilla en la que se encuentra
	 */
	public int getFilaCasilla() {
		return (int) ((getCentro().y-Mesa.ORIGEN_TABLERO.getWidth()) / Mesa.DIMENSIONES_CASILLA.getHeight());
	}
	
	public int getColumnaCasilla() {
		return (int) ((getCentro().x-Mesa.ORIGEN_TABLERO.getHeight()) / Mesa.DIMENSIONES_CASILLA.getWidth());
	}	
	
	/**
	 * @return La fila de la región en la que se encuentra, relativa a la casilla. Está entre 0 y 3 (exclusivo).
	 */
	public int getFilaRegion() {
		int region;
		int posRelativa = (int) ((getCentro().y -Mesa.ORIGEN_TABLERO.getWidth()) % Mesa.DIMENSIONES_CASILLA.getWidth());
		if(posRelativa < Mesa.DIMENSIONES_LATERAL_REGION.getWidth()) {
			region = 0;
		}else if(posRelativa < Mesa.DIMENSIONES_CENTRO_REGION.getWidth()+Mesa.DIMENSIONES_LATERAL_REGION.getWidth()) {
			region = 1;
		}
		else {
			region = 2;
		}
		return region;
	}
	
	public int getColumnaRegion() {
		int region;
		int posRelativa = (int) ((getCentro().x-Mesa.ORIGEN_TABLERO.getHeight()) % Mesa.DIMENSIONES_CASILLA.getHeight());
		if(posRelativa < Mesa.DIMENSIONES_LATERAL_REGION.getHeight()) {
			region = 0;
		}else if(posRelativa < Mesa.DIMENSIONES_CENTRO_REGION.getHeight()+Mesa.DIMENSIONES_LATERAL_REGION.getHeight()) {
			region = 1;
		}
		else {
			region = 2;
		}
		return region;
	}
	
	public void determinarTipoApuesta() {
		int filaCasilla = this.getFilaCasilla();
		int colCasilla = this.getColumnaCasilla();
		int filaRegion = this.getFilaRegion();
		int colRegion = this.getColumnaRegion();

	
		System.out.println(filaCasilla + "," + filaRegion + " : " + colCasilla + "," + colRegion+" cords: "+getCentro().x+","+getCentro().y);
		
		if(filaCasilla == 4) {
			if(colCasilla > 4  && colCasilla < 9){
				this.setTipoDeApuesta(this.COLOR);
			} else if(colCasilla == 3 || colCasilla == 4 || colCasilla == 9 || colCasilla == 10) {
				this.setTipoDeApuesta(this.PAR);
			} else if(colCasilla == 1 || colCasilla == 2 || colCasilla == 11 || colCasilla == 12) {
				this.setTipoDeApuesta(this.PASE);
			}
		} 
//---------------------------------------------------------------------------------------------------------------
		else if(filaCasilla == 3) {
			if(filaRegion == 0){
				if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 13) && colRegion == 2)) {
					this.setTipoDeApuesta(this.TRANSVERSAL);
				} else if(colRegion != 1 && colCasilla != 0) {
					this.setTipoDeApuesta(this.SEISENA);
				}
			}else if(((filaCasilla== 8 || filaCasilla == 4) && filaRegion == 2) || ((filaCasilla== 9 || filaCasilla == 5) && filaRegion == 0)) {
				this.setTipoDeApuesta(this.DOSDOCENAS);
			}else if(colCasilla != 0 && colCasilla != 14) {
				this.setTipoDeApuesta(this.DOCENA);
			}
			else {
			this.setTipoDeApuesta(this.DOCENA);
			}
		}else if(filaCasilla == 0 && filaRegion == 0){
				if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
					this.setTipoDeApuesta(this.TRANSVERSAL);
				} else if(colCasilla != 0) {
					this.setTipoDeApuesta(this.SEISENA);
				}
		}else if(filaCasilla == 2 && filaRegion == 2){
			if(colRegion == 1 || ((colCasilla == 1 && colRegion == 0) || (colCasilla == 12) && colRegion == 2)) {
				this.setTipoDeApuesta(this.TRANSVERSAL);
			} else if(colCasilla != 0) {
				this.setTipoDeApuesta(this.SEISENA);
			}
		}else if(colCasilla == 0 && (filaCasilla == 0 || filaCasilla == 1)){
			if((filaRegion == 2 && colRegion == 2) || (filaRegion == 2 && colRegion == 0)){
				this.setTipoDeApuesta(this.TRANSVERSAL);
			}
		}else if(colCasilla == 1 && (filaCasilla == 1 || filaCasilla == 2)){
			if((filaRegion == 0 && colRegion == 0) || (filaRegion == 0 && colRegion == 2)){
				this.setTipoDeApuesta(this.TRANSVERSAL);
			}
		}else if(colCasilla == 13 && filaCasilla < 3){
			if(filaRegion  == 1 || (filaCasilla == 0 && filaRegion == 0)|| (filaCasilla == 2 && filaRegion == 2)) {
				this.setTipoDeApuesta(this.COLUMNA);
			}else {
				this.setTipoDeApuesta(this.DOSCOLUMNAS);
			}
		}else if(colCasilla == 0 && filaCasilla < 3) {
			this.setTipoDeApuesta(this.CERO);
		}else if(colCasilla == 12 && colRegion == 2 && (filaRegion == 2 || filaRegion == 0)) {
			this.setTipoDeApuesta(this.CABALLO);
		}else if((filaRegion == 2 || filaRegion == 0) && (colRegion == 2 || filaRegion == 0)) {
			this.setTipoDeApuesta(this.CUADRO);
		}else if((filaRegion == 0 && colRegion == 1) ||(filaRegion == 2 && colRegion == 1) ||(filaRegion == 1 && colRegion == 0) ||(filaRegion == 1 && colRegion == 2)) {
			this.setTipoDeApuesta(this.CABALLO);
		}else if(filaRegion == 1 && colRegion == 1) {
			this.setTipoDeApuesta(this.PLENO);;
		}else {
			this.setTipoDeApuesta(this.ERROR);
		}
	}

}
