package TP_ATM;

import java.math.BigDecimal;
import java.util.List;

public class Cuenta {

	private Cliente cliente;
	private BigDecimal saldo;
	private String alias;
	private List<Movimientos> movimientos;

	public Cuenta(String alias) {
		this.alias = alias;
		this.saldo = BigDecimal.ZERO;
	}

	public void ingresarEfectivo(BigDecimal depositoEnDolares) {
		this.saldo = saldo.add(depositoEnDolares);
		movimientos.add(new Movimientos("Ingreso de efectivo", this,
				depositoEnDolares));
	}

	public void descontarEfectivoRetirado(BigDecimal efectivoRetirado) {
		this.saldo = saldo.subtract(efectivoRetirado);
		movimientos.add(new Movimientos("Retiro de Efectivo", this,
				efectivoRetirado));
	}

	public void acreditarTransferencia(BigDecimal montoTransferido) {
		this.saldo = saldo.add(montoTransferido);
		movimientos.add(new Movimientos("Transferencia Recibida", this, montoTransferido));
	}

	public void descontarTransferencia(BigDecimal montoTransferido) {
		this.saldo = saldo.subtract(montoTransferido);
		movimientos.add(new Movimientos("Transferencia Realizada", this, montoTransferido));
	}

	public void consultarMovimientos() {
	}

	public String getAlias() {
		return this.alias;
	}

	public double getSaldo() {
		return saldo.doubleValue();
	}

	public void cargarDinero(BigDecimal dinero) {
		if(dinero.doubleValue() > 0) {
			this.saldo = saldo.add(dinero);
		}
	}
}
