package atm;

import java.math.BigDecimal;

public abstract class Transaccion {
	private Cuenta cuenta;
	private BigDecimal monto;

	public Transaccion(Cuenta cuenta) {
		this.setCuenta(cuenta);
		setMonto(BigDecimal.ZERO);
	}

	public Cuenta getCuenta() {
		return cuenta;
	}

	public void setCuenta(Cuenta cuenta) {
		this.cuenta = cuenta;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public void generarMovimiento() {
		Movimiento movimiento = new Movimiento("", monto, cuenta);
		if (cuenta.getMovimientos().size() == 10) {
			cuenta.getMovimientos().removeFirst();
		}
		cuenta.getMovimientos().add(movimiento);
	}
}
