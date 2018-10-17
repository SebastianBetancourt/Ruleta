package vista;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

public class Mesa extends JFrame {


	public static final Dimension DIMENSIONES_MESA = new Dimension(1700, 780);
	public static final Dimension DIMENSIONES_CASILLA = new Dimension(109, 109);
	public static final Dimension DIMENSIONES_CENTRO_CASILLA = new Dimension((int) DIMENSIONES_CASILLA.getWidth() / 2, (int) DIMENSIONES_CASILLA.getHeight() / 2);
	public static final Dimension DIMENSIONES_LATERAL_CASILLA = new Dimension((int) (DIMENSIONES_CASILLA.getWidth() + 1) / 4, (int) (DIMENSIONES_CASILLA.getWidth() +1) / 4);
	public static final Dimension ORIGEN_TABLERO = new Dimension(108, 74);
	
	
	private MacroContenedor macroContenedor;
	private OverlayLayout macroEsquema;
	private Container contenedorMonedas;
	/**
	 * Conjunto de monedas que están en la mesa
	 */
	private ArrayList<Moneda> monedas;
	
	public Mesa() {
		monedas = new ArrayList<Moneda>(); 
		monedas.add(new Moneda(500, 500));
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				iniciarGUI();
			}
		});
	}

	/**
	 * @return El número que cayó de la ruleta después de girarla
	 */
	private int rodarRuleta() {
		//TODO
		return 4;
	}
	
	private void iniciarGUI(){
		// Instanciar los macro controladores
		macroContenedor = new MacroContenedor();
		this.setContentPane(macroContenedor);
		macroEsquema = new OverlayLayout(macroContenedor);
		macroContenedor.setLayout(macroEsquema);
		
		contenedorMonedas = new Container();
		macroContenedor.add(contenedorMonedas);
		contenedorMonedas.setLayout(null);
		graficarMonedas();
		
		this.setTitle("la OMS considera el juego patológico como un trastorno mental, como un trastorno del control de los impulsos.");
		this.setResizable(false);
		this.setPreferredSize(DIMENSIONES_MESA);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	private void graficarMonedas() {
		for(Moneda moneda : monedas) {
			contenedorMonedas.add(moneda);
		}
		

		
	}

	private class MacroContenedor extends Container {
		private ImageIcon imagen = new ImageIcon("img/mesa.png");

		@Override
		public void paint(java.awt.Graphics g) {
			g.drawImage(imagen.getImage(), 0, 0, this);
			super.paint(g);
		}

	}
	
}


