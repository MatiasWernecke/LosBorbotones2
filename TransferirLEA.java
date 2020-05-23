package TP_ATM;

import java.math.BigDecimal;

public class Transferir extends Transacciones implements Reversible {

	public Transferir(Cuenta cuenta, BigDecimal monto) {
		super(cuenta, monto);
	}

	private BigDecimal monto;

	public void transferir(int montoATransferir, Cuenta otro) {
		try {
			if (super.cuenta.getSaldo() > (double) montoATransferir) {
				BigDecimal montoTransferido = new BigDecimal(montoATransferir);
				super.cuenta.descontarTransferencia(montoTransferido);
				otro.acreditarTransferencia(montoTransferido);
				this.monto = montoTransferido;
			}
		} catch (Exception e) {
			System.out.print(e);
		}

	}

	@Override
	public void revertir(Cuenta otro) {
		otro.descontarTransferencia(monto);
		super.cuenta.acreditarTransferencia(monto);
	}
}
