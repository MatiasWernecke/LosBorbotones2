package TP_ATM;

import java.math.BigDecimal;

public class RetirarEfectivo extends Transacciones {

	public RetirarEfectivo(Cuenta cuenta, BigDecimal monto) {
		super(cuenta, monto);
	}

	public void retirarEfectivo(int cantidadARetirar) {
		try {
			if (super.cuenta.getSaldo() > (double) cantidadARetirar
					&& cantidadARetirar <= 5000 && cantidadARetirar > 100) {
				BigDecimal efectivoRetirado = new BigDecimal(cantidadARetirar);
				super.cuenta.descontarEfectivoRetirado(efectivoRetirado);
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
}
