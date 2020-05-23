package TP_ATM;

import java.math.BigDecimal;

public class Depositar extends Transacciones {

	private CajaDeAhorroEnDolares caja;
	private int cotizDolar = 69;

	public Depositar(Cuenta cuenta, BigDecimal monto, CajaDeAhorroEnDolares caja) {
		super(cuenta, monto);
		this.caja = caja;
	}

	/**
	 * private BigDecimal montoPesos; private BigDecimal montoDolares;
	 */

	public void depositarPesos(int monto) {
		try {
			if (monto > 0) {
				BigDecimal deposito = new BigDecimal(monto);
				super.cuenta.ingresarEfectivo(deposito);
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}

	public void depositarDolares(int monto) {
		try {
			if (monto > 0) {
				BigDecimal depositoEnDolares = new BigDecimal(monto * cotizDolar);
				caja.acreditarDolares(depositoEnDolares);
			}
		} catch (Exception e) {
			System.out.print(e);

		}
	}
}
