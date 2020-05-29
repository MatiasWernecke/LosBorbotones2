
public class App {

	public static void main(String [] args) {
		ATM atm = new ATM();
		atm.leerTarjeta();
		System.out.print(atm.imprimirTicket());
	}
}
