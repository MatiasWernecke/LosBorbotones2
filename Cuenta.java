package atm;

import java.math.BigDecimal;
import java.util.LinkedList;

public abstract class Cuenta {

	private Cliente cliente;
	private BigDecimal saldo;
	private String alias;
	private LinkedList<Movimiento> movimientos;

	public Cuenta(Cliente cliente, String alias) {
		this.cliente = cliente;
		saldo = BigDecimal.ZERO;
		setAlias(alias);
		movimientos = new LinkedList<Movimiento>();
	}

	public abstract void ingresarEfectivo(BigDecimal montoIngresado);

	public abstract void descontarEfectivo(BigDecimal montoDescontado);

	public BigDecimal consultarSaldo() {
		return saldo;
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		try {
			if (contarCaracteres(reemplazarEspacios(alias)) <= 20) {
				this.alias = alias;
			}
		} catch (Exception e) {

		}
	}

	public LinkedList<Movimiento> getMovimientos() {
		return movimientos;
	}

	public void consultarMovimientos() {
		for (Movimiento x : movimientos) {
			System.out.println(x.toString());
		}
	}

	private int contarCaracteres(String string) {
		int contador = 0;

		for (int i = 0; i < string.length(); i++) {
			contador++;
		}
		return contador;
	}

	private String reemplazarEspacios(String string) {
		String nuevoString = string;
		for (int i = 0; i < nuevoString.length(); i++) {
			if (nuevoString.charAt(i) == ' ') {
				nuevoString.replace(' ', '.');
			}
		}

		return nuevoString;
	}

}
