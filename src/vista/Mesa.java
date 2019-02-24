package vista;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel; 
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

import ruleta.Control;

public class Mesa extends JFrame {


	public static final Dimension DIMENSIONES_MESA = new Dimension(716, 374);
	public static final Dimension DIMENSIONES_CASILLA = new Dimension(43, 51);
	public static final Dimension DIMENSIONES_CENTRO_REGION = new Dimension(21, 25);
	public static final Dimension DIMENSIONES_LATERAL_REGION = new Dimension(11, 13);
	public static final Point ORIGEN_TABLERO = new Point(68, 54);
	
	
	private MacroContenedor macroContenedor;
	private OverlayLayout macroEsquema;
	private Container contenedorMonedas;
	protected Control control;
	/**
	 * Conjunto de monedas que están en la mesa
	 */
	private ArrayList<Moneda> monedas;
	
	public Mesa() {
		control = new Control();
		
		monedas = new ArrayList<Moneda>(); 
		monedas.add(new Moneda(200, 200));
		
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
		
		Random aleatorio = new Random();
		return aleatorio.nextInt(36);
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
	
	private void jugarRonda(){
		control.calcularResultado(monedas, rodarRuleta());
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