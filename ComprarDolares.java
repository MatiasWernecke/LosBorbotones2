
import java.math.BigDecimal;

public class ComprarDolares extends Transaccion {

	private BigDecimal impuestoPAIS = BigDecimal.valueOf(0.30);
	private BigDecimal impuesto;
	private BigDecimal cotizacionDolar = BigDecimal.valueOf(69);
	private CajaDeAhorroEnPesos cajaPesos;

	public ComprarDolares(Cuenta cuenta, CajaDeAhorroEnPesos cajaPesos) {
		super(cuenta);
		this.cajaPesos = cajaPesos;
	}

	/**
	 * Convierte la cantidad de dolares a comprar, en pesos, de ese monto se
	 * calcula el impuesto Si la caja en pesos tiene plata para comprar esa
	 * cantidad y pagar el impuesto, la usa
	 */

	public void comprarDolares(BigDecimal cantAComprar) {

		if (montoValido(cantAComprar)){
			BigDecimal dolaresConvertidosAPesos = new BigDecimal(cantAComprar
					.multiply(cotizacionDolar).intValue());

			this.impuesto = new BigDecimal(dolaresConvertidosAPesos.multiply(
					impuestoPAIS).intValue());

			if (haySaldo(dolaresConvertidosAPesos.add(impuesto))) {

				cajaPesos.descontarEfectivo(dolaresConvertidosAPesos);

				super.getCuenta().ingresarEfectivo(cantAComprar);

				aplicarImpuestoPais(impuesto);

				super.setMonto(cantAComprar);

				super.generarMovimiento();

			} else {
				System.err.println("No posee suficiente dinero en su caja de ahorros");
  			}
		} else {
			System.err.println("Cantidad incorrecta");
		}
	
	}

	private boolean haySaldo(BigDecimal montoADescontar) {
		return cajaPesos.getSaldo().compareTo(montoADescontar) >= 0;
	}
	
	/**
	 * cobra el impuesto
	 */
	public void aplicarImpuestoPais(BigDecimal impuesto) {
		cajaPesos.descontarEfectivo(impuesto);

	}

	public BigDecimal getImpuestoDeLaCompra() {
		return this.impuesto;
	}
	
	private boolean montoValido(BigDecimal cantAComprar) {
		return cantAComprar.compareTo(BigDecimal.ZERO) == 1 ;
	}
}
