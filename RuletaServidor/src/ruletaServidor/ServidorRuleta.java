package ruletaServidor;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import ruleta.MonedaSerializable;


import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import vista.Moneda;
import ruleta.MonedaSerializable;

/**
 * @author sebastian
 *
 */
@SuppressWarnings("serial")
public class ServidorRuleta extends JFrame {
	
	private final static int NUMERO_DE_JUGADORES = 2;
	private final static int SEGUNDOS_PARA_APOSTAR = 20;
	private final static int[] BALANCES_INICIALES = {200, 200};
	
	private static ArrayList<Integer> balances;
	private static ArrayList<MonedaSerializable> todasLasMonedas;
	private ServerSocket servidor;
	private Usuario[] usuarios;
	private ExecutorService  ejecutor;
	
	private int numero;
	private static int gananciaDeLaCasa;
	private JTextArea consola;
	
	public ServidorRuleta() {
		super("Servidor Ruleta");
		//establecer consola
		consola = new JTextArea(); 
		consola.setEditable(false);
		JScrollPane scroll = new JScrollPane(consola);
		add(scroll, java.awt.BorderLayout.CENTER);
		
		setSize(500, 300); 
		setVisible( true );
		usuarios = new Usuario[NUMERO_DE_JUGADORES];
		balances = new ArrayList<Integer>(NUMERO_DE_JUGADORES);
		for(int i=0;i<NUMERO_DE_JUGADORES; i++){
			balances.add(BALANCES_INICIALES[i]);
		}

	}
	
	public void ejecutarServidor() {
		try {
			servidor = new ServerSocket(12345, 2);
			for(int i=0;i<NUMERO_DE_JUGADORES; i++){
				usuarios[i] = new Usuario(i);
			}
			while(true)
			{
				jugarUnTurno();
			}
		} catch(EOFException e) {
			consola.append(e.getMessage()+"\n");
		} catch (IOException e) {
			consola.append(e.getMessage()+"\n");
		} finally {
			for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
				usuarios[i].cerrarConexion();
			}
		}
		
	}
	
	private int generarNumeroAleatorio() {
		return new Random().nextInt(36);
	}

	
	private void jugarUnTurno() throws IOException{
		ejecutor = Executors.newFixedThreadPool(NUMERO_DE_JUGADORES);
		//limpia "la mesa" de todas las monedas que había en el anterior turno
		todasLasMonedas = new ArrayList<MonedaSerializable>();
		//manda los balances para que cada jugador pueda empezar a apostar
		for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
			ejecutor.execute(usuarios[i].apostar);
		}
		
		 try {
			 ejecutor.shutdown();
			 ejecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			consola.append(e.getMessage()+"\n");
		}
		 //si todos los jugadores ya terminaron de apostar, rodar ruleta
		 if(ejecutor.isShutdown()) {
			 // enviar todas las monedas de todos los usuarios para que cada usuario las grafique
			for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
				consola.append( "Enviando todas las monedas para ser graficadas al jugador "+i+"\n" );
				usuarios[i].salida.writeObject(todasLasMonedas);
				usuarios[i].salida.flush();
			}
			 // generar el numero ganador
			 numero = generarNumeroAleatorio();
			 consola.append( "ruleta jugada. Salió el "+numero+"\n" );
			 gananciaDeLaCasa = 0;
			 // calcular nuevos balances y enviarlos
			 
			 ejecutor = Executors.newFixedThreadPool(NUMERO_DE_JUGADORES);
			for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
				ejecutor.execute(usuarios[i].calcularNuevoBalance);
			}
			try {
				ejecutor.shutdown();
				ejecutor.awaitTermination(5, TimeUnit.SECONDS);
				if (ejecutor.isTerminated()) {
					consola.append("La casa ganó $"+gananciaDeLaCasa+" este turno\n");
				}
			}catch(Exception e) {
				consola.append(e.getMessage()+"\n");
			}
					
			
		 }
		 
	}
	
	private class Usuario {
		Socket conexion;
		ObjectInputStream entrada;
		ObjectOutputStream salida;
		int id;
		ArrayList<MonedaSerializable> monedas;
		
		public Usuario(int id) {
			this.id = id;
			try {
				esperarConexion();
				obtenerFlujos();
			}catch(Exception e) {
				consola.append("jugador "+id+" : "+e.getMessage()+"\n");
			}

		}
		
		public void esperarConexion() throws IOException {
			consola.append( "esperando conexion del jugador "+id+"\n" );
			conexion = servidor.accept();
			
		}
		
		private void obtenerFlujos() throws IOException{
			consola.append( "estableciendo flujos I/O con el jugador "+id+"\n" );
			salida = new ObjectOutputStream(conexion.getOutputStream());
			salida.flush();
			entrada = new ObjectInputStream(conexion.getInputStream());
			salida.writeInt(id);
			salida.flush();
			salida.writeInt(SEGUNDOS_PARA_APOSTAR);
			salida.flush();
		}
		
		
		private void cerrarConexion() {
			try{
				consola.append( "Cerrando conexion con el jugador "+id+"\n");
				salida.close();
				entrada.close();
				conexion.close();
			}catch(IOException e){
				consola.append("jugador "+id+" : "+e.getMessage()+"\n");
			}
		}

		Runnable apostar = new Runnable() {
			@SuppressWarnings("unchecked")
			public void run() {
				consola.append( "Enviando balances para apuestas del jugador "+id+"\n" );
				try{
					//envia balances iniciales
					salida.writeObject(balances);
					
					salida.flush();
					//recibe las monedas apostadas
					consola.append( "Esperando monedas del jugador "+id+"\n" );
					monedas = ((ArrayList<MonedaSerializable>) entrada.readObject());
					consola.append( "Recibidas"+monedas.size()+" monedas del jugador "+id+"\n" );
					//reune todas las monedas en un solo array
					synchronized(todasLasMonedas){
						todasLasMonedas.addAll(monedas);
						consola.append( "total monedas recolectadas: "+todasLasMonedas.size()+"\n" );						
					}
					
				}catch(Exception e) {
					consola.append("jugador "+id+" : "+e.getMessage()+"\n");
				}
				
			}
		};
		
		Runnable calcularNuevoBalance = new Runnable() {
			public void run() {
				// calcula el balance deacuerdo a la apuesta de cada moneda
				try{
					for(MonedaSerializable moneda : monedas) {
						if(moneda.estaEnLaLista(numero)) {
							// si le apostó al número que salió, le suma el valor de la moneda por el premio
							balances.set(id, (int) (balances.get(id)+moneda.getValor()*(moneda.getPremio()-1)));
							gananciaDeLaCasa -= moneda.getValor()*(moneda.getPremio()-1);
						}else {
							// le cobra el valor de la moneda al balance inicial
							balances.set(id, (int) (balances.get(id)-moneda.getValor()));
							gananciaDeLaCasa += moneda.getValor();
						}
						
					}
					//informar a cada jugador qué numero salió
					salida.writeInt(numero);
					salida.flush();
				}catch(Exception e) {
					consola.append("jugador "+id+" : "+e.getMessage()+"\n");
				}
			}
		};
	}
}