import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.*;

public class ATM {

    private Map billetes;
    private List<Cuenta> listaDeCuentas;
    private Transaccion transacciones;
    private Cuenta cuentaActual;

    public ATM() {
        billetes = new TreeMap<Integer, Integer>();
    }

    public ATM(Map billetes, List<Cuenta> listaDeCuentas, Transaccion transacciones) {
        this.billetes = billetes;
        this.listaDeCuentas = listaDeCuentas;
        this.transacciones = transacciones;
    }

    public void leerTarjeta() {
        boolean tarjetaExiste = false, pinExiste = false;
        long cuitActual = 0;
        String aliasActual = "";
        List<Tarjeta> listaDeTarjetas = leerArchivoTarjetas();
        this.listaDeCuentas = buscarCuentas();
        this.listaDeCuentas = actualizarCuentas(this.listaDeCuentas);

        List<Cliente> listaDeClientes = listaDeCuitDeClientesEnElArchivo();
        listaDeClientes = actualizarClientes(listaDeClientes);
        actualizarClientesDeCuentas(this.listaDeCuentas, listaDeClientes);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Ingresar numero de Tarjeta: ");

        try {
        	//Ingresar tarjeta
            int numeroDeTarjeta = Integer.parseInt(in.readLine());
            Tarjeta tarjeta = null;

            for (int i = 0; i < listaDeTarjetas.size(); i++) {
            	//Si el numero ingresado es lo mismo que el numero de la lista de tarjetas.
            	//La tarjeta existe.
                if (numeroDeTarjeta == listaDeTarjetas.get(i).getNumeroDeTarjeta()) {
                    tarjetaExiste = true;
                    tarjeta = listaDeTarjetas.get(i);
                    cuitActual = buscarCuitPorTarjeta(numeroDeTarjeta);
                    aliasActual = saberSuAliasAtravesDeCuit(cuitActual);
                    cuentaActual = listaDeCuentas.get(i);
                    break;
                }
            }

            //Si la tarjeta existe: ingresa el pin.
            if (tarjetaExiste) {
                System.out.println("Tarjeta aprobada");
                System.out.print("Ingresar pin: ");

                try {
                    int pin = Integer.parseInt(in.readLine());

                    for (int i = 0; i < listaDeTarjetas.size(); i++) {
                    	//Si el numero de pin ingresado es lo mismo que el numero de pin de la lista de tarjetas.
                    	//El pin existe.
                        if (pin == tarjeta.getPin()) {
                            pinExiste = true;
                            break;
                        }
                    }
                    
                    //Si el pin existe: elige una opcion.
                    if (pinExiste) {
                        System.out.println("Acceso aprobado");
                        System.out.println("Cuit: " + cuitActual);
                        System.out.println("Alias: " + aliasActual);
						System.out.println("Cuenta: " + cuentaActual);
						System.out.println("Saldo: " + cuentaActual.consultarSaldo());
                        elegirOpcion();
                    } else {
                        System.out.println("Pin equivocado");
                    }

                } catch (Exception excepcion) {
                    System.err.println("Pin error");
                    System.exit(0);
                }

            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.err.println("Tarjeta error");
            System.exit(0);
        }
    }

    //La lista de cuenta agrega el cliente.
    private void actualizarClientesDeCuentas(List<Cuenta> listaDeCuentas, List<Cliente> listaDeClientes) {
        for (int i = 0; i < listaDeCuentas.size(); i++) {
            Cuenta cuenta = listaDeCuentas.get(i);
            for (int j = 0; j < listaDeClientes.size(); j++) {
                Cliente cliente = listaDeClientes.get(j);
                if (cuenta.getCliente().getCuit() == cliente.getCuit()) {
                    cuenta.setCliente(cliente);
                }
            }
        }
    }

  //Recorre la lista de cuentas y devuelve un cuit (usando como parametro un numero de tarjeta).
    private long buscarCuitPorTarjeta(int numeroTarjeta) {
        long cuit = 0;
        for (int i = 0; i < this.listaDeCuentas.size(); i++) {
            Cuenta cuenta = listaDeCuentas.get(i);
            for (int j = 0; j < cuenta.getCliente().getTarjetas().size(); j++) {
                Tarjeta tarjeta = cuenta.getCliente().getTarjetas().get(j);
                if (tarjeta.getNumeroDeTarjeta() == numeroTarjeta) {
                    cuit = cuenta.getCliente().getCuit();
                }
            }
        }
        return cuit;
    }

    //Se le agrega el cliente un numero de tarjeta.
    private List<Cliente> actualizarClientes(List<Cliente> clientes) {
        try {
            FileReader archivo = new FileReader("validacionDeTarjetas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {
                String[] datos = oneLine.split(",");
                int nroTarjeta = Integer.parseInt(datos[0]);
                int pin = Integer.parseInt(datos[1]);
                String cuit = datos[2];

                for (int i = 0; i < clientes.size(); i++) {
                    Cliente cliente = clientes.get(i);
                    List<Tarjeta> tarjetasCliente = new LinkedList<>();
                    if (cuit.equals(Long.toString(cliente.getCuit()))) {
                        Tarjeta tarjeta = new Tarjeta(nroTarjeta, pin);
                        tarjetasCliente.add(tarjeta);
                        cliente.setTarjetas(tarjetasCliente);
                        //System.out.println("Agregue la tarjeta: " + tarjeta.getNumeroDeTarjeta() + " al cliente cuit: " + cliente.getCuit());
                    }
                }

                oneLine = lector.readLine();
            }
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No se encontro archivo 'validacionDeTarjetas.txt'");
            System.exit(0);
        }
        return clientes;
    }

    //En la lista de cuentas se le agrega un cuit y un cliente.
    private List<Cuenta> actualizarCuentas(List<Cuenta> listaDeCuentas) {
        try {
            FileReader archivo = new FileReader("clientes.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {
                String[] datos = oneLine.split(",");
                long cuit = Long.parseLong(datos[0]);
                String alias = datos[1];

                for (int i = 0; i < listaDeCuentas.size(); i++) {
                    Cuenta cuenta = listaDeCuentas.get(i);
                    if (cuenta.getAlias().equals(alias)) {
                        Cliente cliente = new Cliente();
                        cliente.setCuit(cuit);
                        cuenta.setCliente(cliente);
                    }
                }

                oneLine = lector.readLine();
            }
            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No se encontro archivo 'validacionDeTarjetas.txt'");
            System.exit(0);
        }
        return listaDeCuentas;
    }

    //Devuelve un string con su alias usando como parametro el numero de cuit.
    private String saberSuAliasAtravesDeCuit(long cuit) {
        String alias = "";
        for (int i = 0; i < this.listaDeCuentas.size(); i++) {
            Cuenta cuenta = this.listaDeCuentas.get(i);
            if (cuenta.getCliente().getCuit() == cuit) {
                alias = listaDeAliasDeClientesEnElArchivo().get(i);
                break;
            }
        }
        return alias;
    }

    
    //Devuelve una lista de tarjetas.
    private List<Tarjeta> leerArchivoTarjetas() {
        List<Tarjeta> listaDeTarjetas = new LinkedList<>();

        try {
            FileReader archivo = new FileReader("validacionDeTarjetas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {
                String[] datos = oneLine.split(",");
                int numeroDeTarjeta = Integer.parseInt(datos[0]);
                int pin = Integer.parseInt(datos[1]);
                listaDeTarjetas.add(new Tarjeta(numeroDeTarjeta, pin));
                oneLine = lector.readLine();
            }

            lector.close();
        } catch (Exception e) {
            System.err.println("No se encontro archivo 'validacionDeTarjetas.txt'");
            System.exit(0);
        }

        return listaDeTarjetas;
    }

    //Devuelve una lista de cuit del cliente.
    private List<Cliente> listaDeCuitDeClientesEnElArchivo() {
        List<Cliente> clientes = new LinkedList<>();

        try {
            FileReader archivo = new FileReader("clientes.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {
                Cliente cliente = new Cliente();
                String[] datos = oneLine.split(",");
                long cuit = Long.valueOf(datos[0]);
                cliente.setCuit(cuit);
                clientes.add(cliente);
                oneLine = lector.readLine();
            }

            lector.close();
        } catch (Exception e) {
            System.err.println("No se encontro archivo 'clientes.txt'");
            System.exit(0);
        }

        return clientes;
    }

  //Devuelve una lista de alis del cliente.
    private List<String> listaDeAliasDeClientesEnElArchivo() {

        List<String> listaDeClientes = new LinkedList<>();
        try {
            FileReader archivo = new FileReader("clientes.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {

                String[] datos = oneLine.split(",");
                String alias = datos[1];

                listaDeClientes.add(alias);
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

    //Devuelve una lista de cuentas y se le agrega a la cuenta el saldo y su alias.
    private List<Cuenta> buscarCuentas() {
        List<Cuenta> listaDeCuentas = new LinkedList<>();
        try {
            FileReader archivo = new FileReader("cuentas.txt");
            BufferedReader lector = new BufferedReader(archivo);
            String oneLine = lector.readLine();

            while (oneLine != null) {
                String[] datos = oneLine.split(",");
                int tipoCuenta = Integer.valueOf(datos[0]);
                String alias = datos[1];
                BigDecimal saldo = BigDecimal.valueOf(Double.valueOf(datos[2]));

                Cuenta cuenta = crearCuenta(tipoCuenta);
                cuenta.setSaldo(saldo);
                cuenta.setAlias(alias);

                listaDeCuentas.add(cuenta);
                oneLine = lector.readLine();
            }

            lector.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("No se encontro archivo 'cuentas.txt'");
            System.exit(0);
        }
        return listaDeCuentas;
    }

    //Elige la opcion.
    private void elegirOpcion() {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nOpciones \n1- Retirar Efectivo\n2- Comprar Dolares\n3- Vender Dolares\n4- Depositar\n5- Transferir\n6- Salir");
        System.out.println("\nEliga una opcion: ");

        try {
            int eleccion = Integer.parseInt(in.readLine());
            
            switch (eleccion) {
                case 1: {
                	System.out.println("Retirar Efectivo");
                    System.out.println("\n¿Cuanto desea retirar?");
                	double monto = Double.parseDouble(in.readLine());
                	Cuenta cuenta = cuentaActual; 
                	Transaccion t = new RetirarEfectivo(cuenta);
                    ((RetirarEfectivo) t).retirarEfectivo(BigDecimal.valueOf(monto));
                    System.out.println("\nSueldo actual: " + t.getCuenta().consultarSaldo());
                    elegirOpcion();
                    break;
                }
                case 2: {
                    System.out.println("Comprar Dolares");
                    Cuenta cuenta = cuentaActual;  
                    Cuenta cuentaEnPesos = null;
                    CajaDeAhorroEnPesos c = null;
                    boolean encontroAlias = false;
                    System.out.println("\nIngrese alias:");
                    String alias = in.readLine();
                    
                    for(int i = 0; i < listaDeCuentas.size();i++){
                    	if(alias.equals(listaDeCuentas.get(i).getAlias())){
                    		
                    		
                    		cuentaEnPesos = listaDeCuentas.get(i);
                    		if(cuentaEnPesos instanceof CajaDeAhorroEnPesos){
                    			
                    			encontroAlias = true;
                    			c = new CajaDeAhorroEnPesos();
                    			c.setSaldo(cuentaEnPesos.getSaldo());
                    		}
                    		
                    		
                    		break;
                    		
                    	}
                    }
                    
                    if(encontroAlias){
                    	System.out.println("Encontro alias");                    	
                    	
                    	
                    } else {
                    	System.out.println("No se encontro alias");
                    }
                  
                    //uva.sandalia.halcon
                    ComprarDolares cd = new ComprarDolares(cuentaActual, c);
                    System.out.println("¿Cuanto desea comprar?");
                    double cantAComprar = Double.parseDouble(in.readLine());
                    cd.comprarDolares(BigDecimal.valueOf(cantAComprar));
                    System.out.println("Sueldo cuenta actual: " + cuenta.consultarSaldo());
                    System.out.println("Sueldo en caja de ahorro en peso: " + c.consultarSaldo());
                    
                    elegirOpcion();
                    break;
                }
                case 3: {
                	 System.out.println("Vender Dolares");
                     Cuenta cuenta = cuentaActual;  
                     Cuenta cuentaEnPesos = null;
                     CajaDeAhorroEnPesos c = null;
                     boolean encontroAlias = false;
                     System.out.println("\nIngrese alias:");
                     String alias = in.readLine();
                     
                     for(int i = 0; i < listaDeCuentas.size();i++){
                     	if(alias.equals(listaDeCuentas.get(i).getAlias())){
                     		
                     		//System.out.println("Encontro alias");
                     		cuentaEnPesos = listaDeCuentas.get(i);
                     		if(cuentaEnPesos instanceof CajaDeAhorroEnPesos){
                     			
                     			encontroAlias = true;
                     			c = new CajaDeAhorroEnPesos();
                     			c.setSaldo(cuentaEnPesos.getSaldo());

                     		}
                     		
                     		break;
                     	}
                     }
                     
                     if(encontroAlias){
                     	System.out.println("Encontro alias");                    	
                     	
                     	
                     } else {
                     	System.out.println("No se encontro alias");
                     }
                   
                     //uva.sandalia.halcon
                     VenderDolares vd = new VenderDolares(cuentaActual, c);
                     System.out.println("\n¿Cuanto desea vender?");
                     double cantAComprar = Double.parseDouble(in.readLine());
                     vd.venderDolares(BigDecimal.valueOf(cantAComprar));
                     System.out.println("\nSueldo cuenta actual: " + cuenta.consultarSaldo());
                     System.out.println("Sueldo en caja de ahorro en peso: " + c.consultarSaldo());
                     
                     elegirOpcion();
                     break;
                }
                case 4: {
                    System.out.println("Depositar");
                    System.out.println("\n¿Cuanto desea depositar?");
                    double monto = Double.parseDouble(in.readLine());
                    Cuenta cuenta = cuentaActual;
                    Depositar d = new Depositar(cuenta);
                    d.depositarPesos(BigDecimal.valueOf(monto));
                    System.out.println("\nSueldo actual: " + cuentaActual.getSaldo());
                    elegirOpcion();
                    break;
                }
                case 5: {
                    System.out.println("Transferir");
                    Cuenta cuenta1 = cuentaActual;
                    Cuenta cuenta2 = null;
                    Transferencia t = new Transferencia(cuenta1);
                    System.out.println("\n¿Cuando desea transferir?");
                    double monto = Double.parseDouble(in.readLine());
                    System.out.println("\nIngrese alias: ");
                    String alias = in.readLine();
                    
                    for(int i = 0; i < listaDeCuentas.size();i++){
                    	if(alias.equals(listaDeCuentas.get(i).getAlias())){
                    		cuenta2 = listaDeCuentas.get(i);
                    	}
                    }
                    
                    t.transferencia(BigDecimal.valueOf(monto), cuenta2);


                    System.out.println("\nSueldo de cuenta 1: " + cuenta1.consultarSaldo());
                    System.out.println("Sueldo de cuenta 2: " + cuenta2.consultarSaldo());
                    elegirOpcion();
                    break;
                } 
                case 6: {
                    System.out.println("\nAdios");
                    System.exit(0);                    
                    break;
                }
                default:
                    System.err.println("Seleccione una opcion");
                    elegirOpcion();
            }

        } catch (NumberFormatException e) {
            // TODO Bloque catch generado automÃ¡ticamente
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Bloque catch generado automÃ¡ticamente
            e.printStackTrace();
        }
    }

    //Crea una cuenta.
    private Cuenta crearCuenta(int tipoCuenta) {
        Cuenta cuenta = null;
        switch (tipoCuenta) {
            case 1:
                cuenta = new CajaDeAhorroEnPesos();
                break;
            case 2:
                cuenta = new CuentaCorriente();
                break;
            case 3:
                cuenta = new CajaDeAhorroEnDolares();
                break;
            default:
                break;
        }
        return cuenta;
    }

    //Imprime ticket
    public String imprimirTicket() {
        return "Fecha - Hora - Cuenta: " + cuentaActual + " - TipoDeTransaccion: - ImporteInvolucradoEnLa Transaccion - Nuevo Saldo";
    }

}