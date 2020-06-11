import java.math.BigDecimal;

public class Transferencia extends Transaccion implements Reversible {

	private Cuenta cuentaAtransferir;
	private BigDecimal monto;
	private Cuenta cuenta;
	private boolean transferenciaRealizada = false;


	public Transferencia(Cuenta cuenta) {
		super(cuenta);
		this.cuenta = cuenta;
		monto = BigDecimal.ZERO;

	}
	
	public void transferencia(BigDecimal montoATransferir, Cuenta cuentaATransferir) {
		if (cuentaATransferir instanceof CajaDeAhorroEnPesos 
				|| cuentaATransferir instanceof CuentaCorriente) {
			
			if(haySaldo(montoATransferir)) {
			
			transferenciaRealizada = true;
			
			super.setMonto(montoATransferir);
			
			setMonto(montoATransferir);
			
			setCuenta(cuentaATransferir);
			
			this.cuenta.descontarEfectivo(montoATransferir);
			
			cuentaATransferir.ingresarEfectivo(montoATransferir);
			
			super.generarMovimiento();
			
			} else {
				System.err.println("Saldo insuficiente para transferir");
			}
		
		} else {
			System.err.println("No se puede transferir a una caja de ahorro en dolares");
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
	
	private boolean haySaldo(BigDecimal montoATransferir) {
		return this.cuenta.getSaldo().compareTo(montoATransferir) >= 0;
	}
	
	public boolean getAceptacionTransferencia() {
		return transferenciaRealizada;
	}
}
