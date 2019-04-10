package ruleta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import vista.Mesa;
import vista.Moneda;

public class Control {

	private Socket conexion;
	private String host;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Mesa mesa;
	private static int id;
	private static int balance;
	public static int segundosPorJugada;
	public static int segundos;
	
	public Control(String host) {
		balance = -1;
		this.host = host;
		mesa = new Mesa();
		iniciarCliente();
	}
	
	public static int getId() {
		return id;
	}

	
	/*
	 * Gets the Balance
	 * @returns balance
	 */
	public static int getBalance(){
		return balance;
	}
	
	public static void sumarABalance(int valor) {
		balance += valor;
	}
	
	public void iniciarCliente(){
		
		try {
			conexion = new Socket(InetAddress.getByName(host), 12345);
			salida = new ObjectOutputStream(conexion.getOutputStream());
			salida.flush();
			entrada = new ObjectInputStream(conexion.getInputStream());
			id = entrada.readInt();
			segundosPorJugada = entrada.readInt();
			recibirBalances();
			empezarJugada();
		} catch ( Exception e ){
			e.printStackTrace();
		}

	}
	/*
	 * Lee los balances que envía el servidor y los guarda, distinguiendo del balance de este cliente al de los demás. Devuelve el cambio de balance del cliente.
	 */
	private int recibirBalances() throws ClassNotFoundException, IOException {
		//recibir balances y meterlos en mesa
		ArrayList<Integer> balances = (ArrayList<Integer>) entrada.readObject();
		int balanceAnterior = balance;
		balance = balances.get(id);
		mesa.setBalances(balances);
		mesa.actualizarUI();
		return balance - balanceAnterior;
	}
 
	
	/*
	 * Reestablece e inicializa el conteo del tiempo
	 */
	public void empezarJugada() {
		segundos = segundosPorJugada;
		final Timer cuentaAtras = new Timer();
		TimerTask segundoMenos = new TimerTask() {

			@Override
			public void run() {
				segundos--;
				mesa.actualizarUI();
				if (segundos <= 0) {
					enviarMonedas();
					concluirJugada();
					cuentaAtras.cancel();
					cuentaAtras.purge();
				}
			}
		};

		cuentaAtras.scheduleAtFixedRate(segundoMenos, 0, 1000);
	}
	
	private void enviarMonedas() {
		ArrayList<MonedaSerializable> monedasSerializables = new ArrayList<MonedaSerializable>();
		for(Moneda moneda : mesa.monedas) {
			monedasSerializables.add(moneda.convertirASerializable());
		}
		try {
			System.out.println("enviando monedas a servidor");
			salida.writeObject(monedasSerializables);
			System.out.println("enviadas monedas a servidor");
			salida.flush();
		}
		catch ( IOException e ){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void recibirMonedas() {
		ArrayList<MonedaSerializable> monedasSerializables = null;
		try {
			monedasSerializables = (ArrayList<MonedaSerializable>) entrada.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mesa.limpiar();
		for(MonedaSerializable moneda : monedasSerializables) {
			mesa.monedas.add(Moneda.convertirDeSerializable(moneda));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void concluirJugada(){
		try {
			recibirMonedas();
			mesa.actualizarUI();
			int numero = entrada.readInt();
			System.out.println(numero);
			mesa.rodarRuleta();
			mesa.mostrarNumeroGanador(numero, recibirBalances());
			limpiarJugada();
		} catch (Exception e) {
			e.printStackTrace();
		}				
	}
	
	/*
	 * Quita las monedas de la jugada anterior y prepara el estado de el
	 * siguiente turno
	 */
	private void limpiarJugada() {
		mesa.limpiar();
		mesa.actualizarUI();
		empezarJugada();
	}

}
