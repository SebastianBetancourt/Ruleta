package vista;

/*
 * Clase: Mesa.java
 * 
 * Responsabilidad:
 * Se encarga de graficar la mesa de la ruleta, aquí se declaran las dimensiones de cada componente gráfico y
 * se organizan en diferentes contenedores. Esta encargado del flujo del juego, de acuerdo al tiempo y las jugadas del usuario.
 * 
 * Colaboraciones:
 * Control.java, Moneda.java
 * 
 * Autores:
 * @author Joan Sebastian Betancourt-(1744202)
 * @author Nicolas Lasso Jaramillo-(1740395)
 * @author Jorge Eduardo Mayor Fernández-(1738661)
 * @since 28-9-2018
 * @version 1.0.18
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;

import ruleta.Control;

@SuppressWarnings("serial")
public class Mesa extends JFrame {

	public static final Dimension DIMENSIONES_CONTENEDOR_RULETA = new Dimension(
			274, 499);
	public static final Dimension DIMENSIONES_CONTENEDOR_RUEDA = new Dimension(
			274, 374);
	public static final Dimension DIMENSIONES_CONTENEDOR_TIEMPO = new Dimension(
			274, 125);
	public static final Dimension DIMENSIONES_RUEDA = new Dimension(216, 216);
	public static final Dimension DIMENSIONES_CONTENEDOR_NUMEROS = new Dimension(
			716, 374);
	public static final Dimension DIMENSIONES_CONTENEDOR_CONTROLES = new Dimension(
			716, 125);
	public static final Dimension DIMENSIONES_CONTENEDOR_MONEDAS = new Dimension(
			716, 499);
	public static final Dimension DIMENSIONES_CASILLA = new Dimension(43, 51);
	public static final Dimension DIMENSIONES_CENTRO_REGION = new Dimension(21,
			25);
	public static final Dimension DIMENSIONES_LATERAL_REGION = new Dimension(
			11, 13);
	public static final Point ORIGEN_TABLERO = new Point(68, 54);

	public static final int[] MONEDAS_POSIBLES = { 1, 25, 50, 100 };

	private static final int SEGUNDOS_POR_JUGADA = 60;

	public static int segundos;
	public static Font fuente;
	
	private EscuchaJugar escuchaAction;
	private EscuchaMoneda escuchaMouse;

	private Container contenedorMacro;
	private BoxLayout esquemaMacro;

	private Container contenedorRuleta;
	private BoxLayout esquemaRuleta;

	private Container contenedorRueda;
	private OverlayLayout esquemaRueda;

	private Rueda rueda;
	private final JLabel aviso = new JLabel();

	private ContenedorConFondo contenedorTiempo;
	private FlowLayout esquemaTiempo;
	private final JLabel tiempo = new JLabel();
	private JButton boton;

	private Container contenedorTablero;
	private OverlayLayout esquemaTablero;

	private Container contenedorMonedas;

	private Container contenedorMesa;
	private BoxLayout esquemaMesaContenedor;

	private ContenedorConFondo contenedorNumeros;

	private ContenedorConFondo contenedorControles;
	private FlowLayout esquemaControles;
	private JLabel balance;

	/*
	 * monedas que no estan en juego, listas para ser arrastradas a la mesa
	 */
	private ArrayList<Moneda> monedasPreparadas;	
	protected Control control;
	/*
	 * Conjunto de monedas que están en la mesa
	 */

	private ArrayList<Moneda> monedas;

	public Mesa() {
		control = new Control();
		escuchaAction  = new EscuchaJugar();
		monedas = new ArrayList<Moneda>();
		
		escuchaMouse = new EscuchaMoneda();

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				iniciarGUI();
			}
		});
	}
	
	/**
	 * @return El número que cayó de la ruleta después de girarla
	 */
	private int generarNumeroAleatorio() {
		Random aleatorio = new Random();
		return aleatorio.nextInt(36);
	}

	private void iniciarGUI() {
		cargarFuente();

		// Instanciar los contenedores

		/*
		 * Contenedor principal
		 */
		contenedorMacro = new Container();
		esquemaMacro = new BoxLayout(contenedorMacro, BoxLayout.LINE_AXIS);
		contenedorMacro.setLayout(esquemaMacro);

		/*
		 * Contenedor principal de la ruleta Manjea un administrador de esquema
		 * BoxLayout
		 */
		contenedorRuleta = new Container();
		esquemaRuleta = new BoxLayout(contenedorRuleta, BoxLayout.PAGE_AXIS);
		contenedorRuleta.setLayout(esquemaRuleta);
		contenedorRuleta.setPreferredSize(DIMENSIONES_CONTENEDOR_RULETA);

		/*
		 * Contenedor rueda Maneja un administrador de esquema OverlayLayout
		 */
		contenedorRueda = new Container();
		esquemaRueda = new OverlayLayout(contenedorRueda);
		contenedorRueda.setLayout(esquemaRueda);
		contenedorRueda.setPreferredSize(DIMENSIONES_CONTENEDOR_RUEDA);

		aviso.setHorizontalAlignment(JLabel.CENTER);
		aviso.setFont(fuente);
		aviso.setOpaque(true);
		aviso.setBackground(new Color(0, 0, 0));
		aviso.setForeground(Color.WHITE);
		aviso.setVisible(false);

		/*
		 * Contenedor Tiempo Maneja un administrador de esquema FlowLayout
		 */
		contenedorTiempo = new ContenedorConFondo("img/madera_inicio.png");
		boton = new JButton();
		esquemaTiempo = new FlowLayout();
		contenedorTiempo.setLayout(esquemaTiempo);
		contenedorTiempo.setPreferredSize(DIMENSIONES_CONTENEDOR_TIEMPO);
		contenedorTiempo.add(tiempo);
		contenedorTiempo.add(boton);

		iniciarRueda();

		// Se componen los contenedores
		contenedorRuleta.add(contenedorRueda);
		contenedorRuleta.add(contenedorTiempo);

		contenedorTablero = new Container();
		esquemaTablero = new OverlayLayout(contenedorTablero);
		contenedorTablero.setLayout(esquemaTablero);

		contenedorMesa = new Container();
		esquemaMesaContenedor = new BoxLayout(contenedorMesa, 1);
		contenedorMesa.setLayout(esquemaMesaContenedor);

		contenedorNumeros = new ContenedorConFondo("img/mesa.png");
		contenedorNumeros.setLayout(null);
		contenedorNumeros.setPreferredSize(DIMENSIONES_CONTENEDOR_NUMEROS);

		contenedorMonedas = new Container();
		contenedorMonedas.setLayout(null);

		contenedorControles = new ContenedorConFondo("img/madera.png");
		esquemaControles = new FlowLayout();
		iniciarControles();
		contenedorControles.setLayout(esquemaControles);
		contenedorControles.setPreferredSize(DIMENSIONES_CONTENEDOR_CONTROLES);

		contenedorMesa.add(contenedorNumeros);
		contenedorMesa.add(contenedorControles);

		contenedorTablero.add(contenedorMonedas);
		contenedorTablero.add(contenedorMesa);

		contenedorMacro.add(contenedorRuleta);
		contenedorMacro.add(contenedorTablero);

		this.setContentPane(contenedorMacro);

		this.setTitle("retruleta");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		actualizarUI();
		empezarJugada();
	}

	/*
	 * Establece y organiza los objetos alrededor de la animación de la rueda
	 */
	private void iniciarRueda() {
		// rueda
		rueda = new Rueda();
		rueda.setPreferredSize(DIMENSIONES_CONTENEDOR_RUEDA);
		contenedorRueda.add(aviso);
		contenedorRueda.add(rueda);
		contenedorRueda.add(new ContenedorConFondo(("img/ruleta.png")));

		// tiempo
		tiempo.setHorizontalAlignment(JLabel.CENTER);
		tiempo.setFont(fuente);
		tiempo.setForeground(Color.WHITE);
		
		boton.setFont(fuente);
		boton.setText("JUGAR!");
		boton.setPreferredSize(new Dimension(180, 50));
		boton.addActionListener(escuchaAction);
	}

	/*
	 * Establece y organiza el panel de controles
	 */
	private void iniciarControles() {
		contenedorControles.add(new JLabel() {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(0,150);
			}
		});
		
		// Balance
		balance = new JLabel("" + control.getBalance());
		balance.setFont(fuente);
		balance.setForeground(Color.WHITE);
		contenedorControles.add(balance);

		monedasPreparadas = new ArrayList<Moneda>();
		
		// botones que generan monedas
		actualizarUI();
	}

	/*
	 * Se encarga de agregar las monedas del Arraylist "monedas" al contenedor
	 * monedas.
	 */
	private void graficarMonedas() {
		contenedorMonedas.removeAll();
		System.out.println("monedas en juego:"+monedas.size());
		for (Moneda moneda : monedas) {
			contenedorMonedas.add(moneda);
		}
		System.out.println("monedas preparadas:"+monedasPreparadas.size());
		for (Moneda moneda : monedasPreparadas) {
			contenedorMonedas.add(moneda);
		}
		contenedorMonedas.repaint();
	}

	/*
	 * Reestablece e inicializa el conteo del tiempo
	 */
	private void empezarJugada() {
		segundos = SEGUNDOS_POR_JUGADA;
		final Timer cuentaAtras = new Timer();
		TimerTask segundoMenos = new TimerTask() {

			@Override
			public void run() {
				segundos--;
				tiempo.setText(segundos + "s");
				if (segundos <= 0) {
					jugar();
					cuentaAtras.cancel();
					cuentaAtras.purge();
				}
			}

		};

		cuentaAtras.scheduleAtFixedRate(segundoMenos, 0, 1000);
	}

	/*
	 * Inicia la rotación de la rueda con una velociad aleatoria en un
	 * intervalo.
	 */
	private void jugar() {
		rueda.iniciarRotacion(Rueda.VEL_INICIAL_MINIMA
				+ ((Rueda.VEL_INICIAL_MAXIMA - Rueda.VEL_INICIAL_MINIMA) * (new Random()
						.nextDouble())));
	}

	/*
	 * Se ejecuta cuando ya se obtiene un numero de la ruleta, ordena que se
	 * calculen las ganacias y se muestren los resultados.
	 */
	private void terminarJugada() {
		int ganador = generarNumeroAleatorio();
		int ganancia = control.calcularResultado(monedas, ganador);
		mostrarNumeroGanador(ganador, ganancia);
		actualizarUI();
	}

	/*
	 * Muestra el numero ganador en pantalla durante tres segundos, seguido del
	 * balance
	 */
	private void mostrarNumeroGanador(int ganador, final int ganancia) {
		System.out.println("Salió el " + ganador);

		Timer delay = new Timer();
		final int delayGanador = 3000;
		final int delayGanancia = 3000;

		aviso.setVisible(true);
		aviso.setText("" + ganador);

		TimerTask mostrarGanacia = new TimerTask() {

			@Override
			public void run() {
				aviso.setText("<html><p style=\"width:100%\">Balance de turno:\n $"
						+ ganancia + "</p></html>");

			}

		};

		TimerTask esconderAviso = new TimerTask() {

			@Override
			public void run() {
				aviso.setVisible(false);
				limpiarJugada();
			}

		};

		delay.schedule(mostrarGanacia, delayGanador);
		delay.schedule(esconderAviso, delayGanador + delayGanancia);

	}

	/*
	 * Quita las monedas de la jugada anterior y prepara el estado de el
	 * siguiente turno
	 */
	private void limpiarJugada() {
		monedas.clear();
		empezarJugada();
		actualizarUI();
	}

	/*
	 * Revisa que monedas puede usar el usuario, dependiendo de si le
	 * alcanza el balance; las prepara y las manda a imprimir.
	 */
	private void añadirMonedas() {

			// poner moneda en mesa
			//Random aleatorio = new Random();
			//Moneda moneda = new Moneda(200 + aleatorio.nextInt(200),100 + aleatorio.nextInt(200), valor);
		monedasPreparadas.clear();
		final Point posInicial = new Point((int)(DIMENSIONES_CONTENEDOR_NUMEROS.getWidth()/2-(Moneda.ANCHO)*2),(int)(DIMENSIONES_CONTENEDOR_NUMEROS.getHeight()+Moneda.ALTO/2));
		for (int i = 0; i<4; i++) {
			if(control.getBalance() >= MONEDAS_POSIBLES[i]){
				Moneda moneda = new Moneda(posInicial.x+(Moneda.ANCHO*i), posInicial.y, MONEDAS_POSIBLES[i]);
				monedasPreparadas.add(moneda);
				moneda.addMouseListener(escuchaMouse);
				moneda.addMouseMotionListener(escuchaMouse);
			}
		}
	}
	
	/*
	 * Elimina la moneda en caso de que una moneda que se haya puesto en el
	 * tablero, sea regresada a la mesa
	 */
	public void eliminarMoneda(Moneda moneda) {
		control.sumarABalance(moneda.getValor());
		monedas.remove(moneda);
		contenedorMonedas.remove(moneda);
		actualizarUI();
	}

	/*
	 * Actualiza el saldo del usuario
	 */
	private void actualizarSaldo() {
		balance.setText("$" + control.getBalance());
	}

	/*
	 * Ordena actualizar el saldo y graficar las monedas
	 */
	private void actualizarUI() {
		actualizarSaldo();
		añadirMonedas();
		graficarMonedas();
	}

	/*
	 * Carga la fuente de Minecraft para ser usada en los JLabel
	 */
	private void cargarFuente() {
		final float tamañoFuente = 36f;
		try {
			fuente = Font.createFont(Font.TRUETYPE_FONT, new File("font.ttf"))
					.deriveFont(tamañoFuente);
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(
					fuente);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Extiende de la clase Container y se modifica con tal de poder agregar un
	 * fondo
	 */
	private class ContenedorConFondo extends Container {
		private ImageIcon imagen;

		public ContenedorConFondo(String ruta) {
			super();
			this.imagen = new ImageIcon(ruta);
		}

		public void paint(Graphics g) {
			g.drawImage(imagen.getImage(), 0, 0, this);
			super.paint(g);
		}
	}

	private class EscuchaJugar implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) {
			segundos = 1;
		}
	}
	
	/*
	 * Clase está encargada de la animación de la rueda
	 */
	private class Rueda extends JPanel {

		static final double VEL_INICIAL_MAXIMA = 3.0;
		static final double VEL_INICIAL_MINIMA = 2.0;
		static final double ACELERACION_POR_DT = -Math.PI / 18000;
		static final int DT = 15; // milisecs

		double rotacion = 0;
		double velocidadDt = 0;

		Timer timer;

		Image rueda;

		/*
		 * Contructor de objetos de tipo rueda
		 */
		public Rueda() {
			super();
			rueda = Toolkit.getDefaultToolkit().getImage("img/rueda.png");
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
		 * 
		 * Se encarga de graficar la imagen de la rueda rotada
		 */
		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.translate(DIMENSIONES_CONTENEDOR_RUEDA.getWidth() / 2,
					DIMENSIONES_CONTENEDOR_RUEDA.getHeight() / 2);
			g2d.rotate(rotacion);
			g2d.drawImage(rueda, (int) (-1 * DIMENSIONES_RUEDA.getWidth() / 2),
					(int) (-1 * DIMENSIONES_RUEDA.getWidth() / 2), this);
			Toolkit.getDefaultToolkit().sync();
		}

		/*
		 * Calcula el nuevo ángulo y la nueva velocidad
		 */
		private void rotarDt() {
			rotacion += velocidadDt;
			this.repaint();
			velocidadDt += ACELERACION_POR_DT;
			if (velocidadDt <= 0) {
				velocidadDt = 0;
				timer.cancel();
				Mesa.this.terminarJugada();
			}
		}

		/*
		 * Programa la rotación de la rueda en intervalos pequeños
		 */
		public void iniciarRotacion(double velocidadInicial) {
			velocidadDt = velocidadInicial * Math.PI / 60;
			timer = new Timer("movimiento");
			TimerTask rotacionDt = new TimerTask() {
				@Override
				public void run() {
					rotarDt();
				}

			};
			timer.scheduleAtFixedRate(rotacionDt, 0, DT);
		}
	}
	
	private class EscuchaMoneda extends MouseAdapter {
		
		Point puntoDeAgarre;
		Point posAntesDeMoverse;
		Moneda moneda;
		
		public void mouseDragged(MouseEvent e) {
			moneda = (Moneda) e.getSource();
			if(Mesa.segundos > 0) {
				
				
				int cambioX = e.getX() - puntoDeAgarre.x;
				int cambioY = e.getY() - puntoDeAgarre.y;
				int nuevoX = moneda.getX() + cambioX;
				int nuevoY = moneda.getY() + cambioY;
				if ((nuevoX > Mesa.DIMENSIONES_CONTENEDOR_MONEDAS.getWidth()) || (nuevoX < 0)) {
					nuevoX = moneda.getX();
				}
				if ((nuevoY > Mesa.DIMENSIONES_CONTENEDOR_MONEDAS.getHeight()) || (nuevoY < 0)) {
					nuevoY = moneda.getY();
				}
				moneda.setLocation(nuevoX, nuevoY);
				moneda.repaint();
			}
		}
		
		public void mousePressed(MouseEvent e) {
			moneda = (Moneda) e.getSource();
			if(moneda.getTipoDeApuesta() == Moneda.PREPARADA && control.getBalance() >= moneda.getValor()) {
				control.sumarABalance(-1 * moneda.getValor());
				monedas.add(moneda);
				monedasPreparadas.remove(moneda);
				Mesa.this.actualizarUI();
				//Mesa.this.añadirMonedas();
			}
			puntoDeAgarre = e.getPoint();
			posAntesDeMoverse = moneda.getLocation();
		}
		 
		public void mouseReleased(MouseEvent e) {
			moneda = (Moneda) e.getSource();
			System.out.println(puntoDeAgarre);
			int estadoAnterior = moneda.getTipoDeApuesta();
			if(moneda.determinarTipoApuesta()) {
				puntoDeAgarre = moneda.getLocation();
			}else if(estadoAnterior == Moneda.PREPARADA || ((Moneda) e.getSource()).getLocation().getY() > Mesa.DIMENSIONES_CONTENEDOR_NUMEROS.getHeight()){
				Mesa.this.eliminarMoneda(((Moneda) e.getSource()));
			}
			else {
				moneda.setLocation(posAntesDeMoverse);
				moneda.repaint();
			}
			moneda.determinarValoresAApostar();
		}		
	}// Fin clase Escucha Mouse

} // Fin clase Mesa