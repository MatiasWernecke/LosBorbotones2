package TP_ATM;

import java.math.BigDecimal;

public class Depositar extends Transacciones {

	public Depositar(Cuenta cuenta, BigDecimal monto) {
		super(cuenta, monto);
		// TODO Apéndice de constructor generado automáticamente
	}

	private BigDecimal montoPesos;
	private BigDecimal montoDolares;

	public void depositarPesos(BigDecimal deposito) {
		try {
			montoPesos.add(deposito);
			super.cuenta.ingresarEfectivo(montoPesos);
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void depositarDolares(BigDecimal deposito) {
		try {
			montoDolares.add(deposito);
			super.cuenta.ingresarEfectivo(montoDolares);
		} catch (Exception e) {
			System.out.print(e);

		}
	}
}
