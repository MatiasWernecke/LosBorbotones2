
import java.math.BigDecimal;

public class Transferencia extends Transaccion implements Reversible {

	private Cuenta cuentaAtransferir;
	private BigDecimal monto;

	public Transferencia(Cuenta cuenta) {
		super(cuenta);
		
		monto = BigDecimal.ZERO;

	}
	
	public void transferencia(BigDecimal montoATransferir,Cuenta cuentaATransferir) {
		if(haySaldo(montoATransferir)){
		
		setMonto(montoATransferir);
		
		setCuenta(cuentaATransferir);
		
		cuentaEmisor.descontarEfectivo(montoATransferir);
		
		cuentaATransferir.ingresarEfectivo(montoATransferir);

		} else {
			throw new Error("No posee dinero para realizar esta transferencia");
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

	private boolean haySaldo(BigDecimal montoADescontar) {
		return super.getCuenta().consultarSaldo().compareTo(montoADescontar) >= 0;
	}
	
	@Override
	public void reversible() {

		super.getCuenta().ingresarEfectivo(this.monto);
		this.cuentaAtransferir.descontarEfectivo(this.monto);

	}

}
