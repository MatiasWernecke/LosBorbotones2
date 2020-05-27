package ATM_TP;

import java.math.BigDecimal;

public class ComprarDolares extends Transaccion {

	private BigDecimal impuestoPAIS  = BigDecimal.valueOf(0.30);;
	private BigDecimal cotizacionDolar = BigDecimal.valueOf(69);
	private Cuenta agujeroNegro;
	private CajaDeAhorroEnDolares cajaDolares;
	private CajaDeAhorroEnPesos cajaPesos;

	public ComprarDolares(Cuenta cuenta, CajaDeAhorroEnDolares cajaDolares,
			CajaDeAhorroEnPesos cajaPesos) {
		super(cuenta);
		this.cajaDolares = cajaDolares;
		this.cajaPesos = cajaPesos;
	}

	/**
	 * Convierte la cantidad de dolares a comprar, en pesos, de ese monto se calcula el impuesto
	 * Si la caja en pesos tiene plata para comprar esa cantidad y pagar el impuesto, la usa 
	 */
	
	public void comprarDolares(BigDecimal cantAComprar) {

		BigDecimal dolaresConvertidosAPesos = new BigDecimal(cantAComprar
				.multiply(cotizacionDolar).doubleValue());

		BigDecimal impuesto = new BigDecimal(dolaresConvertidosAPesos.multiply(
				impuestoPAIS).doubleValue());

		if (cajaPesos.haySaldo(dolaresConvertidosAPesos.add(impuesto))) {

			cajaPesos.descontarEfectivo(dolaresConvertidosAPesos);
			
			cajaDolares.ingresarEfectivo(cantAComprar);

			aplicarImpuestoPais(impuesto);

		} else {
			throw new Error("No posee suficiente dinero en su caja de ahorros");
		}
	}

	/**
	 * cobra el impuesto y se los acredita a la cuenta agujero negro 
	 */
	public void aplicarImpuestoPais(BigDecimal impuesto) {
		cajaPesos.descontarEfectivo(impuesto);
		acreditarImpuestosEnOtraCuenta(agujeroNegro, impuesto);

	}
	
	/**
	 * cada vez que se compre dolares, los impuestos van a ir a esta cuenta
	 */
	public void acreditarImpuestosEnOtraCuenta(Cuenta agujeroNegro, BigDecimal impuesto) {
		this.agujeroNegro.ingresarEfectivo(impuesto);
	}

}