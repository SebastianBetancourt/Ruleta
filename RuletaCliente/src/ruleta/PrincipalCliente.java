package ruleta;

/*
 * Clase: Principal.java
 * 
 * Responsabilidad:
 * Se encarga de inicializar un objeto de tipo mesa que iniciará el juego.
 * 
 * Colaboraciones:
 * Mesa.java
 * 
 * Autores:
 * @author Joan Sebastian Betancourt-(1744202)
 * @author Nicolas Lasso Jaramillo-(1740395)
 * @author Jorge Eduardo Mayor Fernández-(1738661)
 * @since 28-9-2018
 * @version 1.0
 */



public class PrincipalCliente {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ( args.length == 0 ){
			new Control("127.0.0.1"); // localhost
		}
		else {
			new Control(args[0]); // usa args
		}
	}

	
}
