package ATM_TP;

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

		BigDecimal montoEnPesos = new BigDecimal(cantAVender.multiply(
				cotizacionDolarVenta).doubleValue());

		if (haySaldo(cantAVender)) {

			cajaPesos.ingresarEfectivo(montoEnPesos);

			super.getCuenta().descontarEfectivo(cantAVender);

		} else {
			throw new Error("No posee suficiente dinero en su caja de ahorros");
		}
	}

	private boolean haySaldo(BigDecimal montoADescontar) {
		return super.getCuenta().consultarSaldo().compareTo(montoADescontar) >= 0;
	}

	// POR AHORA NO SE SI SE APLICAN IMPUESTOS POR VENDER DOLARES, CREO QUE NO
}
