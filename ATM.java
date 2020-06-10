import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ATM {

	private List<Cuenta> listaDeCuentas;
	private Transaccion transacciones;
	private Cuenta cuentaActual;
	private static int[] billetes = { 1000,500,100 };
    private static int[] cantidad = { 12,0,10 };
    private int[] contador = { 0, 0, 0 };
    private static int totalDeBilletes;
	
	
	public ATM() {		
		
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
			// Ingresar tarjeta
			int numeroDeTarjeta = Integer.parseInt(in.readLine());
			Tarjeta tarjeta = null;

			for (int i = 0; i < listaDeTarjetas.size(); i++) {
				// Si el numero ingresado es lo mismo que el numero de la lista
				// de tarjetas.
				// La tarjeta existe.
				if (numeroDeTarjeta == listaDeTarjetas.get(i).getNumeroDeTarjeta()) {
					tarjetaExiste = true;
					tarjeta = listaDeTarjetas.get(i);
					cuitActual = buscarCuitPorTarjeta(numeroDeTarjeta);
					aliasActual = saberSuAliasAtravesDeCuit(cuitActual);
					cuentaActual = listaDeCuentas.get(i);
					break;
				}
			}

			// Si la tarjeta existe: ingresa el pin.
			if (tarjetaExiste) {
				System.out.println("Tarjeta aprobada");
				System.out.print("Ingresar pin: ");

				try {
					int pin = Integer.parseInt(in.readLine());

					for (int i = 0; i < listaDeTarjetas.size(); i++) {
						// Si el numero de pin ingresado es lo mismo que el
						// numero de pin de la lista de tarjetas.
						// El pin existe.
						if (pin == tarjeta.getPin()) {
							pinExiste = true;
							break;
						}
					}

					// Si el pin existe: elige una opcion.
					if (pinExiste) {
						System.out.println("Acceso aprobado");
						System.out.println("Cuit: " + cuitActual);
						System.out.println("Alias: " + aliasActual);
						System.out.println("Cuenta: " + cuentaActual);
						System.out.println("Saldo: " + cuentaActual.getSaldo());
						elejirOpcion();
					} else {
						System.err.println("El pin ingresado no existe. Vuelve a intentarlo.");
						leerTarjeta();
					}

				} catch (Exception excepcion) {
					System.err.println("No ingreso correctamente el pin");
					leerTarjeta();
				}

			} else {
				System.err.println("La tarjeta que ingreso no existe. Vuelve a intentarlo.");
				leerTarjeta();
			}

		} catch (Exception exception) {
			System.err.println("No ingreso correctamente la tarjeta");
			leerTarjeta();
		}
	}

	// La lista de cuenta agrega el cliente.
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

	// Recorre la lista de cuentas y devuelve un cuit (usando como parametro un
	// numero de tarjeta).
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

	// Se le agrega el cliente un numero de tarjeta.
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
					LinkedList<Tarjeta> tarjetasCliente = new LinkedList<>();
					if (cuit.equals(Long.toString(cliente.getCuit()))) {
						Tarjeta tarjeta = new Tarjeta(nroTarjeta, pin);
						tarjetasCliente.add(tarjeta);
						cliente.setTarjetas(tarjetasCliente);
						// System.out.println("Agregue la tarjeta: " +
						// tarjeta.getNumeroDeTarjeta() + " al cliente cuit: " +
						// cliente.getCuit());
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

	// En la lista de cuentas se le agrega un cuit y un cliente.
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

	// Devuelve un string con su alias usando como parametro el numero de cuit.
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

	// Devuelve una lista de tarjetas.
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

	// Devuelve una lista de cuit del cliente.
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

	// Devuelve una lista de alis del cliente.
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

	// Devuelve una lista de cuentas y se le agrega a la cuenta el saldo y su
	// alias.
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
			System.err.println("No se encontro archivo 'cuentas.txt'");
			System.exit(0);
		}
		return listaDeCuentas;
	}

		private void sobreEscribirSaldo() {
		try	{
			FileWriter archivoAEscribir = new FileWriter("C:/Users/Silvia/Desktop/cuentas.txt", true);
			BufferedWriter bf = new BufferedWriter(archivoAEscribir);
			String tipoCuenta = null;
			String alias = null;
			double saldo = 0;
			double descubierto = 0;
			
			 File inputFile = new File("C:/Users/Silvia/Desktop/cuentas.txt");
			 File outputFile = new File("C:/Users/Silvia/Desktop/cuentas.txt");

		    try {
		      BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		      BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

		    String currentLine;

		    while((currentLine = reader.readLine()) != null) {                        
		        if(currentLine.trim().equals(1)){ 
		            continue;
		        }
		        writer.write(currentLine + System.getProperty("line.separator"));
		    }       

		    writer.close();
		    reader.close();

		    } catch (IOException e) {
		        e.printStackTrace();
		    }
			
			for(int i = 0; i <listaDeCuentas.size();i++) {
				if(listaDeCuentas.get(i).equals(cuentaActual)) {
					if(cuentaActual instanceof CajaDeAhorroEnPesos) {
						tipoCuenta = "01";
					} else if(cuentaActual instanceof CuentaCorriente) {
						tipoCuenta = "02";
					} else {
						tipoCuenta = "03";

					}
					alias = cuentaActual.getAlias();
					saldo = cuentaActual.getSaldo().doubleValue();
				} else if(listaDeCuentas.get(i) instanceof CajaDeAhorroEnPesos 
						&& listaDeCuentas.get(i).equals(cuentaActual) == false) {
					
						tipoCuenta = "01";
						
				 } else if(listaDeCuentas.get(i) instanceof CuentaCorriente 
						 && listaDeCuentas.get(i).equals(cuentaActual) == false) {
					 
						tipoCuenta = "02";
				 } else if(listaDeCuentas.get(i) instanceof CajaDeAhorroEnDolares 
						 && listaDeCuentas.get(i).equals(cuentaActual) == false) {
							tipoCuenta = "03";
				 }
				alias = listaDeCuentas.get(i).getAlias();
				saldo = listaDeCuentas.get(i).getSaldo().doubleValue();
				bf.write(tipoCuenta + "," + alias + "," + saldo +  ","+ descubierto );	        
				bf.newLine();
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
}
	
	// Elige la opcion.
	private void elejirOpcion() {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		if (cuentaActual instanceof CajaDeAhorroEnDolares) {
			System.out
					.println("\nOpciones \n1- Comprar Dolares\n2- Vender Dolares\n3- Depositar\n4- Consultar saldo\n5- Consultar movimiento\n6- Salir");
			System.out.println("\nElija una opcion: ");

			try {
				int eleccion = Integer.parseInt(in.readLine());

				switch (eleccion) {
				case 1: {
					System.out.println("Comprar Dolares");
					CajaDeAhorroEnPesos cuentaEnPesos = null;
					for (int i = 0; i < listaDeCuentas.size(); i++) {
						if (listaDeCuentas.get(i) instanceof CajaDeAhorroEnPesos
								&& cuentaActual.getCliente().getCuit() == (listaDeCuentas
										.get(i).getCliente().getCuit())) {
							cuentaEnPesos = (CajaDeAhorroEnPesos) listaDeCuentas
									.get(i);
						}
					}

					ComprarDolares cd = new ComprarDolares(cuentaActual,
							cuentaEnPesos);
					System.out.println("\n¿Cuanto desea comprar?");
					double cantAComprar = Double.parseDouble(in.readLine());
					cd.comprarDolares(BigDecimal.valueOf(cantAComprar));
					System.out.println("\nSueldo cuenta actual: "
							+ cuentaActual.getSaldo());
					System.out.println("Sueldo en caja de ahorro en peso: "
							+ cuentaEnPesos.getSaldo());
					sobreEscribirSaldo();
					System.out.println(imprimirTicket("Comprar Dolares",
							BigDecimal.valueOf(cantAComprar)));
					elejirOpcion();
					break;
				}
				case 2: {
					System.out.println("Vender Dolares");
					Cuenta cuenta = cuentaActual;
					Cuenta cuentaEnPesos = null;
					CajaDeAhorroEnPesos c = null;
					Cliente clienteActual = cuenta.getCliente();

					for (int i = 0; i < listaDeCuentas.size(); i++) {
						if (clienteActual.equals(listaDeCuentas.get(i)
								.getCliente())
								&& listaDeCuentas.get(i) instanceof CajaDeAhorroEnPesos) {
							cuentaEnPesos = listaDeCuentas.get(i);
							c = new CajaDeAhorroEnPesos();
							c.setSaldo(cuentaEnPesos.getSaldo());
							break;
						} else {
							System.err.println("\nNo posee una caja de ahorro en pesos para vender dolares");
							elejirOpcion();
						}
					}

					VenderDolares vd = new VenderDolares(cuentaActual, c);
					System.out.println("\n¿Cuanto desea vender?");
					double cantAComprar = Double.parseDouble(in.readLine());
					vd.venderDolares(BigDecimal.valueOf(cantAComprar));
					System.out.println("\nSueldo cuenta actual: "
							+ cuenta.getSaldo());
					System.out.println("Sueldo en caja de ahorro en pesos: "
							+ c.getSaldo());
					sobreEscribirSaldo();
					System.out.println(imprimirTicket("Vender Dolares",
							BigDecimal.valueOf(cantAComprar)));
					elejirOpcion();
					break;
				}
				case 3: {
					depositar();
					break;
				}
				case 4: {
					System.out.println("Su saldo actual es de: $" + cuentaActual.getSaldo());
					elejirOpcion();
					break;
				}
				case 5: {
					cuentaActual.consultarMovimientos();
					elejirOpcion();
					break;
				}
				case 6: {
					System.out.println("\nAdios");
					System.exit(0);
					break;
				}
				default:
					System.err.println("Seleccione una opcion");
					elejirOpcion();
				}

			} catch (NumberFormatException e) {
				// TODO Bloque catch generado automÃ¡ticamente
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Bloque catch generado automÃ¡ticamente
				e.printStackTrace();
			}
		} else {
			System.out
					.println("\nOpciones \n1- Retirar Efectivo\n2- Depositar\n3- Transferir\n4- Consultar saldo\n5- Consultar movimiento\n6- Salir");
			System.out.println("\nElija una opcion: ");

			try {
				int eleccion = Integer.parseInt(in.readLine());

				switch (eleccion) {
				case 1: {
					retirarEfectivo();
					break;
				}
				case 2: {
					depositar();
					break;
				}
				case 3: {
					System.out.println("Transferir");
					Cuenta cuenta1 = cuentaActual;
					Cuenta cuenta2 = null;
					Transferencia t = new Transferencia(cuenta1);
					System.out.println("\n¿Cuanto desea transferir?");
					double monto = Double.parseDouble(in.readLine());
					System.out.println("\nIngrese alias: ");
					String alias = in.readLine();
					boolean existe = false;
					for (int i = 0; i < listaDeCuentas.size(); i++) {
						if (alias.equals(listaDeCuentas.get(i).getAlias())) {
							cuenta2 = listaDeCuentas.get(i);
							existe = true;
						}
					}
					if (!(cuenta2 instanceof CajaDeAhorroEnDolares)) {

						if (existe) {
							System.out.println("Encontro alias");
						} else {
							System.err.println("No se encontro alias");
							elejirOpcion();
						}

						t.transferencia(BigDecimal.valueOf(monto), cuenta2);
						sobreEscribirSaldo();
						System.out.println(imprimirTicket("Transferir",
								BigDecimal.valueOf(monto)));
						System.out.println("Revertir Transferencia?: \nsi - no");
						String decision = in.readLine();
						if (decision.equals("si")) {
							t.reversible();
							sobreEscribirSaldo();
							System.out.println("\nSueldo de cuenta 1: "
									+ cuenta1.getSaldo());
							System.out.println("Transferencia revertida");
						}
					} else {
						System.err.println("No se puede transferir dolares");
					}
					elejirOpcion();
					break;
				}
				case 4: {
					System.out.println("Su saldo actual es de: $"
							+ cuentaActual.getSaldo());
					elejirOpcion();
					break;
				}
				case 5: {
					cuentaActual.consultarMovimientos();
					elejirOpcion();
					break;
				}
				case 6: {
					System.out.println("\nAdios");
					System.exit(0);
					break;
				}
				default:
					System.err.println("Seleccione una opcion");
					elejirOpcion();
				}

			} catch (NumberFormatException e) {
				// TODO Bloque catch generado automÃ¡ticamente
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Bloque catch generado automÃ¡ticamente
				e.printStackTrace();
			}
		}
	}
	
	private void retirarEfectivo() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			boolean hayBilletes = false;
			if (cuentaActual instanceof CajaDeAhorroEnDolares) {
				throw new Error("No se puede extraer en dolares");
			}

			System.out.println("Retirar Efectivo");

			try {
				System.out.println("\n¿Cuanto desea retirar?");
				int dineroIngresado = Integer.parseInt(in.readLine());
				Cuenta cuenta = cuentaActual;
				Transaccion transaccion = new RetirarEfectivo(cuenta);
				calcTotalBilletes();
				if(sacarDinero(dineroIngresado)){
					((RetirarEfectivo) transaccion).retirarEfectivo(BigDecimal.valueOf(dineroIngresado));
				}
				
				System.out.println(imprimirTicket("Retirar Efectivo", BigDecimal.valueOf(dineroIngresado)));

			} catch (NumberFormatException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (Error e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}

			elejirOpcion();
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		} catch (Error e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	private void depositar() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Depositar");
			System.out.println("\n¿Cuanto desea depositar?");

			try {
				int dinero = Integer.parseInt(in.readLine());
				Cuenta cuenta = cuentaActual;
				Depositar d = new Depositar(cuenta);
				if(cuentaActual instanceof CajaDeAhorroEnDolares) {
					d.depositarDolares(BigDecimal.valueOf(dinero));
				} else {
					d.depositarPesos(BigDecimal.valueOf(dinero));
				}
				sobreEscribirSaldo();
				System.out.println(imprimirTicket("Depositar", BigDecimal.valueOf(dinero)));

			} catch (NumberFormatException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Bloque catch generado automáticamente
				e.printStackTrace();
			}

			elejirOpcion();
		} catch (NumberFormatException e) {
			// TODO Bloque catch generado automáticamente
			e.printStackTrace();
		}
	}

	// Crea una cuenta.
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

	public static void calcTotalBilletes() {
        for (int i = 0; i < billetes.length; i++) {
            totalDeBilletes = totalDeBilletes + billetes[i] * cantidad[i];
        }
    }


	public synchronized boolean sacarDinero(int valor) {
		boolean hayBilletes = false;
		BigDecimal cuenta = cuentaActual.getSaldo();
		int cuentaInt = cuenta.intValue();
		
		if(billetes[0] <= 0 && billetes[1] <= 0 && billetes[2] <= 0){
        	System.err.println("No hay mas billetes en el ATM.");
        }
		
    	if(valor%100==0 && valor <= cuentaInt){
	    	//Si la suma de todos los billetes son mayor que el valor agregado, se hace el metodo.
	        if (valor <= totalDeBilletes) {
	            for (int i = 0; i < billetes.length; i++) {
	            	//Si el valor del billete i es menor que el valor agregado.
	                if (billetes[i] <= valor) {
	                    int contadorDeBilletes = valor / billetes[i];
	                    if (cantidad[i] > 0) {//Si la cantidad es mayor a 0
	                    	contador[i] = contadorDeBilletes >= cantidad[i] ? cantidad[i] : contadorDeBilletes;
	                        cantidad[i] = contadorDeBilletes >= cantidad[i] ? 0 : cantidad[i] - contadorDeBilletes;
	                        //Deduce el totarl de billetes que quedan en el ATM 
	                        totalDeBilletes = totalDeBilletes - (contador[i] * billetes[i]);
	                        // Calcula el valor que necesita para la proxima iteracion
	                        valor = valor - (contador[i] * billetes[i]);
	                        mostrarBilletesQueQuedan();
	                        hayBilletes = true;
	                    }
	                }
	            }
	            //mostrarBilletes();
	           
	            
	
	        } else {
	            System.err.println("El valor ingresado es mayor que la suma de todos los billetes");
	        }
	        
	        
    	}else{
    		System.err.println("El valor ingresado no es valido");
    	}
    	
    	return hayBilletes;

    }

    private void mostrarBilletesQueQuedan() {
        for (int i = 0; i < billetes.length; i++) {
            System.out.println("Billetes de " + billetes[i] + " quedan " + cantidad[i]);
        }

    }

	// Imprime ticket
	public String imprimirTicket(String tipoDeTransaccion, BigDecimal importe) {
		LocalDate fecha = LocalDate.now();
		LocalDateTime tiempo = LocalDateTime.now();
		int hora = tiempo.getHour();
		int minuto = tiempo.getMinute();
		int segundo = tiempo.getSecond();
		return "\nFecha: " + fecha + " - Hora: " + hora + ":" + minuto + ":" + segundo + " - Cuenta: "
				+ cuentaActual.getAlias() + " - TipoDeTransaccion: " + tipoDeTransaccion
				+ " - Importe En La Transaccion: " + importe + " - Nuevo Saldo: $" + cuentaActual.getSaldo();
	}

}
