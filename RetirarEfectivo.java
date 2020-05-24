package atm;

import java.math.BigDecimal;

public class RetirarEfectivo extends Transacciones {

	public RetirarEfectivo(Cuenta cuenta) {
		super(cuenta);
	}
//saldo debe ser mayor o igual a monto
	public void retirarEfectivo(BigDecimal montoRetirado) {
		try {
			//A.compareTo(B): este metodo retorna -1 si A < B, 0 si A = B, 1 si A > B
			if (haySaldo(montoRetirado)) {
				getCuenta().descontarEfectivo(montoRetirado);
				generarMovimiento();
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}
	private boolean haySaldo(BigDecimal montoRetirado) {
		return getCuenta().consultarSaldo().compareTo(montoRetirado) != -1;
	}
}
