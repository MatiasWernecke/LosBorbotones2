
import java.math.BigDecimal;

public class VenderDolares extends Transaccion {

	private BigDecimal cotizacionDolarVenta = BigDecimal.valueOf(63);
	private CajaDeAhorroEnPesos cajaPesos;

	/**
	 * Utiliza la cajade ahorro en dolares, y la caja de ahorro en pesos
	 */

	public VenderDolares(Cuenta cuenta, CajaDeAhorroEnPesos cajaPesos) {
		super(cuenta);
		this.cajaPesos = cajaPesos;
	}

	/**
	 * pregunta si tiene dolares, los vende y acredita el monto en pesos en la
	 * caja de pesos
	 */

	public void venderDolares(BigDecimal cantAVender) {

		if (cantAVender.intValueExact() > 0) {
			BigDecimal montoEnPesos = new BigDecimal(cantAVender.multiply(
					cotizacionDolarVenta).doubleValue());

			if (haySaldo(cantAVender)) {

				cajaPesos.ingresarEfectivo(montoEnPesos);

				super.getCuenta().descontarEfectivo(cantAVender);

			} else {
				System.err.println("No posee suficiente dinero en su caja de ahorros");

			}
		} else {
			System.err.println("Cantidad Incorrecta");
		}

	}

	private boolean haySaldo(BigDecimal montoADescontar) {
		return super.getCuenta().getSaldo().compareTo(montoADescontar) >= 0;
	}
}

