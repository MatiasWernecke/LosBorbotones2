import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ATM {
	
	private static Map billetes;
	private static List<Cuenta> listaDeCuentas;
	//private static List<Cliente> listaDeClientes;
	private static Transacciones transacciones;
	
	public static void main(String[] args){
		
		//leerTarjeta();
		
		System.out.println(listaDeClientes());
		
		
	}
	
	public ATM(){
		billetes = new TreeMap<Integer, Integer>();
		
	}
	
	public static void leerTarjeta(){
		List<Tarjeta> listaDeTarjetas = listaDeTarjetas();
		

		boolean tarjetaExiste = false;
		boolean pinExiste = false;
		boolean cuitExiste = false;
		String cuitActual = "";

		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		
		System.out.print("Ingresar numero de Tarjeta: ");

        try {
            int numeroDeTarjeta = Integer.parseInt(in.readLine());
            int tarjetaPinActual = 0;
           
            
            for(int i = 0; i < listaDeTarjetas.size(); i++){
            	if(numeroDeTarjeta == listaDeTarjetas.get(i).getNumeroDeTarjeta()){
            		tarjetaExiste = true;
            		tarjetaPinActual = listaDeTarjetas.get(i).getPin();
            		cuitActual = buscarCuitATravesDeTarjeta(numeroDeTarjeta);
            		break;
                } 
            	
            }
        
            if(tarjetaExiste){
            	System.out.println("Tarjeta aprobado");
            	System.out.print("Ingresar pin: ");
            	
                try {
                    int pin = Integer.parseInt(in.readLine()); 
                    
                    for(int i = 0; i < listaDeTarjetas.size(); i++){
                    	if(pin == tarjetaPinActual){
                    		pinExiste = true;
                        } 
                    }

                    if(pinExiste){
                    	System.out.println("Acceso aprobado");
                    	System.out.println("Cuit: " + cuitActual);
                    	System.out.println("Cuenta: " + cuitActual);
                    	//elegirOpcion();
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
	
	private static List<Tarjeta> listaDeTarjetas(){
		
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
            System.err.println("No se encontro archivo 'validacionDeTarjetas.txt'");
            System.exit(0);
        }
		
		return listaDeTarjetas;
		
	}
	
	private static String buscarCuitATravesDeTarjeta(int tarjeta){
		
		String numeroDeCuit = "";
		
		for(int i = 0; i < listaDeTarjetas().size(); i++){
			if(tarjeta == listaDeTarjetas().get(i).getNumeroDeTarjeta()){
				numeroDeCuit = listaDeCuitDeTarjetas().get(i);
			}
		}
		
		return numeroDeCuit;
		
	}
	
	
	
	private static List<String> listaDeCuitDeTarjetas(){
		
		List<String> listaDeCuit = new LinkedList<>();
		
		try {
            FileReader archivo = new FileReader("validacionDeTarjetas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();
            
            while (oneLine != null) {            	
              
            	String[] datos = oneLine.split(",");
            	String cuit = datos[2];
            	//System.out.println(numeroDeTarjeta); 
            	listaDeCuit.add(cuit);            	
            	oneLine = lector.readLine();
	
            }
            
            if (lector != null) {
                lector.close();
            }
           
        } catch (Exception e) {
            System.err.println("No se encontro archivo validacionDeTarjetas.txt");
            System.exit(0);
        }
		
		return listaDeCuit;
		
	}
	
	private static List<Cliente> listaDeClientes(){
		
		List<Cliente> listaDeClientes = new LinkedList<>();

		try {
            FileReader archivo = new FileReader("clientes.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();
            
            while (oneLine != null) {            	
              
            	String[] datos = oneLine.split(",");
            	int cuit = Integer.parseInt(datos[0]);
            	String alias = datos[1];
            	System.out.println(cuit); 
            	 
            	//Cliente(List<Tarjeta> tarjeta, int cuit)
            	//Cliente clientes = new Cliente(new LinkedList<>(), cuit);
            	//listaDeClientes.add(new Cliente(listaDeTarjetas(),cuit));            	

            	oneLine = lector.readLine();
	
            }
            
            if (lector != null) {
                lector.close();
            }
           
        } catch (Exception e) {
            System.err.println("No se encontro archivo 'clientes.txt'");
            System.exit(0);
        }
		
		return listaDeClientes;
		
	}
	
	private static List<Cuenta> listaDeCuentas(){
		
		List<Cuenta> listaDeCuentas = new LinkedList<>();
		int valor = 120;
		
		try {
            FileReader archivo = new FileReader("cuentas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();
            
            while (oneLine != null) {            	
              
            	String[] datos = oneLine.split(",");
            	int tipo = Integer.parseInt(datos[0]);
            	String alias = datos[1];
            	int saldo = Integer.parseInt(datos[2]);
            	int descubierto = Integer.parseInt(datos[3]);
            	//System.out.println(numeroDeTarjeta); 
            	 
            	Cliente cliente = new Cliente(listaDeTarjetas(),1234);
            	Cuenta cuentaCorriente = new CuentaCorriente(cliente, alias, BigDecimal.valueOf(valor));
            	//Cuenta cajaAhorroEnPeso = new CajaAhorroEnPeso(null, alias, null);
            	//Cuenta cajaAhorroEnDolares = new CajaAhorroEnDolares(null, alias, null);
            	listaDeCuentas.add(cuentaCorriente);            	

            	oneLine = lector.readLine();
	
            }
            
            if (lector != null) {
                lector.close();
            }
           
        } catch (Exception e) {
            System.err.println("No se encontro archivo 'cuentas.txt'");
            System.exit(0);
        }
		
		return listaDeCuentas;
		
	}
	
	private static void elegirOpcion(){
		
		int valor = 0;
		BigDecimal monto =  BigDecimal.valueOf(valor);
		
		
		BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in));
		
		System.out.println("\nOpciones \n1- Retirar Efectivo\n2- Comprar Dolares\n3- Vender Dolares\n4- Depositar\n5- Transferir");
		System.out.println("\nEliga una opcion: ");
		
		try {
			int eleccion = Integer.parseInt(in.readLine());
			
			
			switch(eleccion){
	        
        	case 1 :{
        		transacciones = new RetirarEfectivo(new CuentaCorriente(new Cliente(new LinkedList<>(), 1234),"Luciano", monto));
        		//System.out.println("Cuenta: " + cuenta.get(0));
        		System.out.println("Retirar Efectivo:");
        		int efectivo = Integer.parseInt(in.readLine());
        		
        		if(transacciones instanceof RetirarEfectivo){
        			((RetirarEfectivo) transacciones).retirarEfectivo(BigDecimal.valueOf(efectivo));
        		}
        		System.out.println("Retiro:" + efectivo);
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
        		System.err.println("Seleccione una opcion");
        		elegirOpcion();
        }
		
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}     
		
		
	}
	
	public static String imprimirTicket(){
		return "Fecha - Hora - Cuenta: - TipoDeTransaccion: - ImporteInvolucradoEnLa Transaccion - Nuevo Saldo";
	}

}
