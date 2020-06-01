
import java.math.BigDecimal;

public class Depositar extends Transaccion {

	private BigDecimal montoPesos;
	private BigDecimal montoDolares;

	public Depositar(Cuenta cuenta) {

		super(cuenta);

		montoPesos = BigDecimal.ZERO;
		montoDolares = BigDecimal.ZERO;

	}

	public void depositarPesos(BigDecimal deposito) {

		if (validarDeposito(deposito)) {

			setMontoPesos(deposito);
			super.getCuenta().setSaldo(
					super.getCuenta().getSaldo().add(deposito));
			System.out.println("se realizo el deposito en pesos correctamente");

		} else {

			throw new Error("no se puede depositar el monto ingresado");

		}

	}

	public void depositarDolares(BigDecimal deposito) {

		if (validarDeposito(deposito)) {

			setMontoDolares(deposito);
			super.getCuenta().setSaldo(
					super.getCuenta().getSaldo().add(deposito));
			System.out
					.println("se realizo el deposito en dolares correctamente");

		} else {

			throw new Error("no se puede depositar el monto ingresado");

		}

	}

	public BigDecimal getMontoPesos() {
		return montoPesos;
	}

	public void setMontoPesos(BigDecimal montoPesos) {
		this.montoPesos = montoPesos;
	}

	public BigDecimal getMontoDolares() {
		return montoDolares;
	}

	public void setMontoDolares(BigDecimal montoDolares) {
		this.montoDolares = montoDolares;
	}

	private boolean validarDeposito(BigDecimal deposito) {

		return deposito.compareTo(BigDecimal.ZERO) > 0
				|| deposito.compareTo(BigDecimal.ZERO) > 0;

	}

}

