package TP_ATM;

import java.math.BigDecimal;

public class Transferir extends Transacciones implements Reversible {

	public Transferir(Cuenta cuenta, BigDecimal monto) {
		super(cuenta, monto);
		// TODO Apéndice de constructor generado automáticamente
	}

	private Cuenta cuenta;
	private BigDecimal monto;

	public void transferir(BigDecimal montoTransferido, Cuenta otro) {
		try {
			if (super.cuenta.getSaldo() > monto.doubleValue()) {
				super.cuenta.descontarTransferencia(montoTransferido);
				otro.acreditarTransferencia(montoTransferido);
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}

	@Override
	public void revertir() {

	}
}
