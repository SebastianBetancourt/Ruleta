package ruletaServidor;

import javax.swing.JFrame;

public class PrincipalServidor {

	public PrincipalServidor() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServidorRuleta aplicacion = new ServidorRuleta();
		aplicacion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		aplicacion.ejecutarServidor();
	}

}
