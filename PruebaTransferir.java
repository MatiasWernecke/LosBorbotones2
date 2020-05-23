package tp_atm;
import java.math.BigDecimal;

class PruebaTransferir {

	public static void main(String[] args) throws RandomException {

		// cliente uno
		Cliente cliente = new Cliente("12345678912");
		String alias = "gato.perro.raton";

		// cliente 2

		Cliente cliente2 = new Cliente("12566788799");
		String alias2 = "casa.estacion.leon";

		BigDecimal montoPesos = BigDecimal.valueOf(50000);

		// cliente 1
		Cuenta cuenta = new Cuenta(cliente, alias);
		Depositar deposito = new Depositar(cuenta);

		// cliente 2

		Cuenta cuenta2 = new Cuenta(cliente2, alias2);
		Depositar deposito2 = new Depositar(cuenta2);

		// cliente 1
		deposito.depositarPesos(montoPesos);
		System.out.println("la cuenta del cliente 1 tiene :"
				+ cuenta.consultarSaldo());

		// cliente 2

		deposito2.depositarPesos(montoPesos);
		deposito2.depositarPesos(montoPesos);
		System.out.println("la cuenta del cliente 2 tiene :"
				+ cuenta2.consultarSaldo());
		System.out.println("despues de la transferencia de 12000");

		// cliente 2 le pasa plata al clienta 1

		BigDecimal montoATransferir = BigDecimal.valueOf(12000);
		Transferir transferir = new Transferir(cuenta2);
		transferir.transferir(montoATransferir, cuenta);
		System.out.println("el saldo del cliente 1 es :"
				+ cuenta.consultarSaldo());
		System.out.println("el saldo del cliente 2 es: "
				+ cuenta2.consultarSaldo());
		cuenta2.mostrarMovimientos();
		
		System.out.println("se aplica reversible");
		transferir.reversible();
		System.out.println("el saldo del cliente 1 es :"
				+ cuenta.consultarSaldo());
		System.out.println("el saldo del cliente 2 es: "
				+ cuenta2.consultarSaldo());
		

	}

}
