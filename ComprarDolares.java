
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

		BigDecimal dolaresConvertidosAPesos = new BigDecimal(cantAComprar
				.multiply(cotizacionDolar).doubleValue());

		this.impuesto = new BigDecimal(dolaresConvertidosAPesos.multiply(
				impuestoPAIS).doubleValue());

		if (cajaPesos.haySaldo(dolaresConvertidosAPesos.add(impuesto))) {

			cajaPesos.descontarEfectivo(dolaresConvertidosAPesos);

			super.getCuenta().ingresarEfectivo(cantAComprar);

			aplicarImpuestoPais(impuesto);

			super.setMonto(cantAComprar);
			
			super.generarMovimiento();


		} else {
			throw new Error("No posee suficiente dinero en su caja de ahorros");
		}
	}

	private boolean haySaldo(BigDecimal montoDescontado) {
		return consultarSaldo().subtract(montoDescontado).compareTo(descubierto) >= 0;
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
}
