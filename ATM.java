import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ATM {
	
	private static Map billetes;
	private static List<Cuenta> cuenta;
	private static Transacciones transacciones;
	
	public static void main(String[] args){
		
		//Lee tarjeta
		leerTarjeta();
		
		//Ingresa PIN
		ingresarPIN();
		
		//Elige lo que quiere hacer
        hacer();
		
		//Saca ticket
        imprimirTicket();
		
	}
	
	public ATM(){
		billetes = new TreeMap<Integer, Integer>();
		cuenta = new LinkedList<Cuenta>();
	}
	
	private static void leerTarjeta(){
		
        try {
            FileReader archivo = new FileReader("validacionDeTarjetas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();
            while (oneLine != null) {
              
            	String[] datos = oneLine.split(",");
            	String numeroDeTarjeta = datos[0];
            	String pin = datos[1];
            	String cuit = datos[2];
            	
            	System.out.println(numeroDeTarjeta);
            	
            	oneLine = lector.readLine();  
            	
            }
            if (lector != null) {
                lector.close();
            }
        } catch (Exception e) {
            System.err.println("No se encontro archivo");
        }

	}
	
	private static void ingresarPIN(){
		
		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("Ingresar numero de PIN: ");

        try {
        	//Ponerle un limite de 4 digitos
            int numeroDePIN = Integer.parseInt(in.readLine());                
            System.out.println("El pin es: " + numeroDePIN);
        } catch (Exception excepcion) {
            System.err.println("Tiene que ingresar numero de cuatro digitos");
            System.exit(0);
        }
	}
	
	private static void hacer(){
		
		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		
		System.out.println("\nOpciones \nOpcion 1\nOpcion 2");
		System.out.println("\nEliga una opcion: ");
		
		try {
			int eleccion = Integer.parseInt(in.readLine());
			
			switch(eleccion){
	        
        	case 1 :{
        		System.out.println(imprimirTicket());
        		imprimirTicket();
        		break;
        	}
        	case 2:{
        		System.out.println("Elegiste opcion 2");
        		break;
        	}
        	default:
        
        }
		
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}     
		
		
	}
	
	private static String imprimirTicket(){
		return "Fecha - Hora - Cuenta: " + cuenta + " - TipoDeTransaccion: - ImporteInvolucradoEnLa Transaccion - Nuevo Saldo";
	}

}
