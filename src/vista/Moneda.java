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
	private static final float[] premios = { 2, 2, 2, 3, 3, 1.5f, 1.5f, 6, 9,
			12, 18, 36, 36 };
	private static final String[] tipos = {"COLOR",
		"PAR",
		"PASE",
		"DOCENA",
		"COLUMNA",
		"DOSDOCENAS",
		"DOSCOLUMNAS",
		"SEISENA",
		"CUADRO",
		"TRANSVERSAL",
		"CABALLO",
		"PLENO",
		"CERO"};

	public static final int ANCHO = 70;
	public static final int ALTO = 70;

	/**
	 * Posibles valores de las fichas, de menor a mayor
	 */
	public static final int[] VALORES = { 1, 25, 50 };

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
		if(tipoDeApuesta == Moneda.ERROR) {
			//TODO manejar casos de error de apuesta
		}else {
			premio = premios[tipoDeApuesta];
			this.tipoDeApuesta = tipoDeApuesta;
		}
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
				int nuevoX = Moneda.this.getX() + cambioX;
				int nuevoY = Moneda.this.getY() + cambioY;
				if (nuevoX > Mesa.DIMENSIONES_MESA.getWidth() || nuevoX < 0) {
					nuevoX = Moneda.this.getX();
				}
				if (nuevoY > Mesa.DIMENSIONES_MESA.getHeight() || nuevoY < 0) {
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

			}

		});

	}

	public int getValor() {
		return valor;
	}

	public Point getCentro() {
		return new Point((getX() + (ANCHO / 2)), (getY() + (ALTO / 2)));
	}

	/**
	 * @return La fila de la casilla en la que se encuentra
	 */
	public int getFilaCasilla() {
		return (int) ((getCentro().y - Mesa.ORIGEN_TABLERO.y) / Mesa.DIMENSIONES_CASILLA
				.getHeight());
	}

	public int getColumnaCasilla() {
		return (int) ((getCentro().x - Mesa.ORIGEN_TABLERO.x) / Mesa.DIMENSIONES_CASILLA
				.getWidth());
	}

	/**
	 * @return La fila de la región en la que se encuentra, relativa a la
	 *         casilla. Está entre 0 y 3 (exclusivo).
	 */
	public int getFilaRegion() {
		int region;
		int posRelativa = (int) ((getCentro().y - Mesa.ORIGEN_TABLERO
				.y) % Mesa.DIMENSIONES_CASILLA.getHeight());
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

	public int getColumnaRegion() {
		int region;
		int posRelativa = (int) ((getCentro().x - Mesa.ORIGEN_TABLERO
				.x) % Mesa.DIMENSIONES_CASILLA.getWidth());
		
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

	public void determinarTipoApuesta() {
		int filaCasilla = this.getFilaCasilla();
		int columnaCasilla = this.getColumnaCasilla();
		int filaRegion = this.getFilaRegion();
		int columnaRegion = this.getColumnaRegion();

		if(filaCasilla == 4 && columnaCasilla > 4 && columnaCasilla < 9){this.setTipoDeApuesta(Moneda.COLOR);
		}else if(filaCasilla == 4 && ((columnaCasilla > 2 && columnaCasilla < 5) || (columnaCasilla > 8 && columnaCasilla < 11))){this.setTipoDeApuesta(Moneda.PAR);
		}else if(filaCasilla == 4 && ((columnaCasilla > 0 && columnaCasilla < 3) || (columnaCasilla > 10 && columnaCasilla < 13))){this.setTipoDeApuesta(Moneda.PASE);
		}else if(filaCasilla == 3 && ((columnaRegion == 1 && filaRegion != 0) || (columnaCasilla == 1 && columnaRegion == 0 && filaRegion != 0) || (columnaCasilla == 13 && columnaRegion == 2 && filaRegion != 0)) && columnaCasilla >0){this.setTipoDeApuesta(Moneda.DOCENA);
		}else if((columnaCasilla == 13 && filaCasilla < 3 && (((filaCasilla == 0 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2)) || (!(filaRegion == 2 && columnaRegion == 1) || !(filaRegion == 0 && columnaRegion == 1))))){this.setTipoDeApuesta(Moneda.COLUMNA);
		}else if(filaCasilla == 3 && filaRegion != 0 && !(columnaCasilla == 1 && columnaRegion == 0) && !(columnaCasilla == 13 && columnaRegion == 2) && columnaRegion != 1){this.setTipoDeApuesta(Moneda.DOSDOCENAS);
		}else if((columnaCasilla == 13 && filaCasilla < 3 && !(((filaCasilla == 0 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2)) || (!(filaRegion == 2 && columnaRegion == 1) || !(filaRegion == 0 && columnaRegion == 1))))){this.setTipoDeApuesta(Moneda.DOSCOLUMNAS);
		}else if(columnaCasilla > 0 && columnaCasilla < 13 && columnaRegion != 1 && ((filaCasilla == 3 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2)) && !(columnaCasilla == 1 && columnaRegion==1) && !(columnaCasilla == 12 && columnaRegion == 2)){this.setTipoDeApuesta(Moneda.SEISENA);
		}else if(columnaCasilla > 0 && columnaCasilla < 13 && filaCasilla < 3 && (filaRegion != 1 && columnaRegion != 1) && !(columnaCasilla == 1 && columnaRegion == 0) && !(filaCasilla == 0 && filaRegion == 0) && !(filaCasilla == 2 && filaRegion == 2) && !(columnaCasilla == 12 && columnaRegion == 2)){this.setTipoDeApuesta(Moneda.CUADRO);
		}else if(filaCasilla == 4 && filaRegion == 0 && (columnaRegion == 1 || (columnaCasilla == 1 && columnaRegion == 0) || (columnaCasilla == 12 && columnaRegion == 2))||(columnaCasilla == 0 && (((filaCasilla == 0 || filaCasilla == 1) && (filaRegion == 2 && columnaRegion == 2))) || ((filaCasilla == 1 || filaCasilla == 2) && (filaRegion == 0 && columnaRegion == 2)))||(columnaCasilla == 1 && (((filaCasilla == 0 || filaCasilla == 1) && (filaRegion == 2 && columnaRegion == 0))) || ((filaCasilla == 1 || filaCasilla == 2) && (filaRegion == 0 && columnaRegion == 0)))){this.setTipoDeApuesta(Moneda.TRANSVERSAL);
		}else if(columnaCasilla > 0 && columnaCasilla < 13 && filaCasilla < 3 && ((filaRegion == 1 && columnaRegion != 1) || (filaRegion != 1 && columnaRegion == 1)) && !(filaCasilla == 0 && filaRegion == 0) && !(filaCasilla == 2 && filaRegion == 2) && !(columnaCasilla == 12 && columnaRegion == 2)){this.setTipoDeApuesta(Moneda.CABALLO);
		}else if(filaCasilla <3 && columnaCasilla > 0 && columnaCasilla < 13 && filaRegion == 1 && columnaRegion == 1){this.setTipoDeApuesta(Moneda.PLENO);
		}else if(columnaCasilla == 0 && filaCasilla <3 && (columnaRegion < 2 || (filaCasilla == 0 && filaRegion == 0) || (filaCasilla == 2 && filaRegion == 2))){this.setTipoDeApuesta(Moneda.CERO);
		}else {this.setTipoDeApuesta(Moneda.ERROR);}
		
		System.out.println(filaCasilla + "," + filaRegion + ":"
				+ columnaCasilla + "," + columnaRegion + " cords: "
				+ getCentro().x + "," + getCentro().y + " "+tipos[tipoDeApuesta]);

	}

}
