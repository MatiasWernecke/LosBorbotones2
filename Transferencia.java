package cajero_atm;

import java.math.BigDecimal;

public class Transferencia extends Transacciones implements Reversible {

	private Cuenta cuentaAtransferir;
	private BigDecimal monto;

	public Transferencia(Cuenta cuenta) {
		super(cuenta);
		
		monto = BigDecimal.ZERO;

	}
	
	public void transferencia(BigDecimal montoATransferir,
			Cuenta cuentaATransferir) {

		setMonto(montoATransferir);
		setCuenta(cuentaATransferir);
		super.getCuenta().descontarEfectivo(montoATransferir);
		cuentaATransferir.ingresarEfectivo(montoATransferir);
		super.generarMovimiento();

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
