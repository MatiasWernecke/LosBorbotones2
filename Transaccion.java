
import java.math.BigDecimal;
import java.util.LinkedList;

public abstract class Transaccion {

	private Cuenta cuenta;
	private BigDecimal monto;

	public Transaccion(Cuenta cuenta) {
		this.cuenta = cuenta;
		this.monto = BigDecimal.ZERO;
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
		Movimiento movimiento = new Movimiento("", this.monto, this.cuenta);
		if(cuenta.getMovimientos() != null){
			if (cuenta.getMovimientos().size() == 10) {
				cuenta.getMovimientos().removeFirst();
			}
		}else{
			cuenta.setMovimientos(new LinkedList<Movimiento>());
		}
		cuenta.getMovimientos().add(movimiento);
	}
}

