import java.math.BigDecimal;

public class Transferencia extends Transaccion implements Reversible {

	private Cuenta cuentaAtransferir;
	private BigDecimal monto;
	private Cuenta cuenta;

	public Transferencia(Cuenta cuenta) {
		super(cuenta);
		this.cuenta = cuenta;
		monto = BigDecimal.ZERO;

	}
	
		public void transferencia(BigDecimal montoATransferir,
			Cuenta cuentaATransferir) {
		if (cuentaATransferir instanceof CajaDeAhorroEnPesos || cuentaATransferir instanceof CuentaCorriente) {
			super.setMonto(montoATransferir);
			setMonto(montoATransferir);
			setCuenta(cuentaATransferir);
			this.cuenta.descontarEfectivo(montoATransferir);
			cuentaATransferir.ingresarEfectivo(montoATransferir);
			super.generarMovimiento();

		} else {
			throw new Error("No se puede transferir dolares");
		}
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Cuenta getCuenta() {
		return this.cuentaAtransferir;
	}

	public void setCuenta(Cuenta cuentaAtransferir) {
		this.cuentaAtransferir = cuentaAtransferir;
	}

	
	@Override
	public void reversible() {

		super.getCuenta().ingresarEfectivo(this.monto);
		this.cuentaAtransferir.descontarEfectivo(this.monto);

	}

}
