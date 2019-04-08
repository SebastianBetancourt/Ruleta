package ruletaServidor;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

/**
 * @author sebastian
 *
 */
@SuppressWarnings("serial")
public class ServidorRuleta extends JFrame {
	
	private final static int NUMERO_DE_JUGADORES = 2;
	private static int[] balances = {1200, 1200};
	private static Object[] objetosMoneda = {};
	private ServerSocket servidor;
	private Usuario[] usuarios;
	
	public ServidorRuleta() {
		super("Servidor Ruleta");
		usuarios = new Usuario[NUMERO_DE_JUGADORES];
		for(int i=0;i<NUMERO_DE_JUGADORES; i++)
			usuarios[i] = new Usuario();
	}
	
	public void ejecutarServidor() {
		try {
			servidor = new ServerSocket(123, 2);
			while(true)
			{
				procesarConexion();
			}
		} catch(EOFException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
				usuarios[i].cerrarConexion();
			}
		}
		
	}
	
	private void procesarConexion() throws IOException{
		for(int i=0; i<NUMERO_DE_JUGADORES;i++) {
			usuarios[i].enviarDatos();
		
			try{
				balances[i] = (Integer) usuarios[i].getEntrada().readObject();
				objetosMoneda[i] = usuarios[i].getEntrada().readObject();
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class Usuario{
		Socket conexion;
		ObjectInputStream entrada;
		ObjectOutputStream salida;
		
		public Usuario() {
			try {
				esperarConexion();
				obtenerFlujos();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void esperarConexion() throws IOException {
			conexion = servidor.accept();
			
		}
		
		private void obtenerFlujos() throws IOException{
			salida = new ObjectOutputStream(conexion.getOutputStream());
			salida.flush();
			entrada = new ObjectInputStream(conexion.getInputStream());
		}
		
		public ObjectInputStream getEntrada() {
			return entrada;
		}
		
		private void enviarDatos() {
			try{
				salida.writeObject(balances);
				salida.writeObject(objetosMoneda);
				salida.flush();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		private void cerrarConexion() {
			try{
				salida.close();
				entrada.close();
				conexion.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}