import java.math.BigDecimal;

public class RetirarEfectivo extends Transaccion {
	
	private boolean extraccionRealizada = false;
	
	public RetirarEfectivo(Cuenta cuenta) {
		super(cuenta);
	}

	// saldo debe ser mayor o igual a monto
	public void retirarEfectivo(BigDecimal montoRetirado) {
		// A.compareTo(B): este metodo retorna -1 si A < B, 0 si A = B, 1 si
		// A > B
		if (haySaldo(montoRetirado)) {
			extraccionRealizada = true;
			super.setMonto(montoRetirado.negate());
			super.getCuenta().descontarEfectivo(montoRetirado);
			super.generarMovimiento();
			System.out.println("la extraccion se realizo con exito");

		} else {
			System.err.println("Saldo Insuficiente");
		}

	}

	private boolean haySaldo(BigDecimal montoRetirado) {
		if (super.getCuenta() instanceof CuentaCorriente) {
			BigDecimal descubierto = ((CuentaCorriente) super.getCuenta()).getDescubierto();
			return getCuenta().getSaldo().add(descubierto.negate()).compareTo(montoRetirado) >= -1;		
			} else {
			return getCuenta().getSaldo().compareTo(montoRetirado) >= 0;
		}
	}
	
	public boolean getAceptacionDeExtraccion() {
		return extraccionRealizada;
	}
}
