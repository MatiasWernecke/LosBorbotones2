import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

public class Cuenta {

	private Cliente cliente;
	private double saldo;
	private String alias;
	private List<Movimientos> movimientos;
	java.util.Date fechaActual = new java.util.Date(); // Fecha actual del sistema

	public Cuenta(String alias) {
		this.alias = alias;
		this.saldo = 0;
	}

	public void ingresarEfectivo(BigDecimal monto) {
		if (monto.doubleValue() > 0) {
			this.saldo += monto.doubleValue();
			movimientos.add(new Movimientos(fechaActual, "Ingreso de efectivo",
					this, monto));
		}
	}

	public void descontarEfectivoRetirado(BigDecimal monto) {
		if (saldo >= monto.doubleValue()) {
			this.saldo -= monto.doubleValue();
			movimientos.add(new Movimientos(fechaActual, "Retiro de Efectivo",
					this, monto));
		}
	}

	public void acreditarTransferencia(BigDecimal monto) {
		this.saldo += monto.doubleValue();
		movimientos.add(new Movimientos(fechaActual, "Transferencia Recibida",
				this, monto));
	}

	public void descontarTransferencia(BigDecimal monto) {
		this.saldo -= monto.doubleValue();
		movimientos.add(new Movimientos(fechaActual, "Transferencia Realizada",
				this, monto));
	}

	public void consultarMovimientos() {
		for (Movimientos movimiento : movimientos) {
			System.out.println(movimiento.toString());
		}
	}

	public String getAlias() {
		return this.alias;
	}

	public double getSaldo() {
		return saldo;
	}
}
