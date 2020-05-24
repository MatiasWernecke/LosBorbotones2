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
		
		leerTarjeta();
	}
	
	public ATM(){
		billetes = new TreeMap<Integer, Integer>();
		cuenta = new LinkedList<Cuenta>();
	}
	
	public static void leerTarjeta(){
		List<Tarjeta> lista = new LinkedList<>();
		lista = leerArchivo();
		boolean tarjetaExiste = false;
		boolean pinExiste = false;

		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		
		System.out.print("Ingresar numero de Tarjeta: ");
        try {
            int numeroDeTarjeta = Integer.parseInt(in.readLine()); 
            int tarjetaPinActual = 0;
            
            for(int i = 0; i < lista.size(); i++){
            	if(numeroDeTarjeta == lista.get(i).getNumDeTarjeta() ){
            		tarjetaExiste = true;
            		tarjetaPinActual = lista.get(i).getPin();
                } 
            }
            
            if(tarjetaExiste){
            	System.out.println("Tarjeta aprobado");
            	
            	System.out.print("Ingresar pin: ");
                try {
                    int pin = Integer.parseInt(in.readLine()); 
                    
                    for(int i = 0; i < lista.size(); i++){
                    	if(pin == tarjetaPinActual){
                    		pinExiste = true;
                        } 
                    }
                    
                    if(pinExiste){
                    	System.out.println("Acceso aprobado");
                    	elegirOpcion();
                    }else {
                    	System.out.println("Pin equivocado");
                    }
                    
        			
                } catch (Exception excepcion) {
                    System.err.println("Pin error");
                    System.exit(0);
                }	
            	
            }
            
			
        } catch (Exception excepcion) {
            System.err.println("Tarjeta error");
            System.exit(0);
        }
	}
	
	public static List leerArchivo(){
		
		List<Tarjeta> listaDeTarjetas = new LinkedList<>();
		
		try {
            FileReader archivo = new FileReader("validacionDeTarjetas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();
            
            while (oneLine != null) {            	
              
            	String[] datos = oneLine.split(",");
            	int numeroDeTarjeta = Integer.parseInt(datos[0]);
            	int pin = Integer.parseInt(datos[1]);
            	String cuit = datos[2];
            	//System.out.println(numeroDeTarjeta); 
            	 
            	
            	listaDeTarjetas.add(new Tarjeta(numeroDeTarjeta,pin));            	

            	oneLine = lector.readLine();
	
            }
            
            if (lector != null) {
                lector.close();
            }
           
        } catch (Exception e) {
            System.err.println("No se encontro archivo");
            System.exit(0);
        }
		
		return listaDeTarjetas;
		
	}
	
	private static void elegirOpcion(){
		
		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		
		System.out.println("\nOpciones \n1- Retirar Efectivo\n2- Comprar Dolares\n3- Vender Dolares\n4- Depositar\n5- Transferir");
		System.out.println("\nEliga una opcion: ");
		
		try {
			int eleccion = Integer.parseInt(in.readLine());
			
			switch(eleccion){
	        
        	case 1 :{
        		System.out.println("Retirar Efectivo");
        		break;
        	}
        	case 2:{
        		System.out.println("Comprar Dolares");
        		break;
        	}
        	case 3:{
        		System.out.println("Vender Dolares");
        		break;
        	}
        	case 4:{
        		System.out.println("Depositar");
        		break;
        	}
        	case 5:{
        		System.out.println("Transferir");
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
