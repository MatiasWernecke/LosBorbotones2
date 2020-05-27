
import java.math.BigDecimal;

public class CajaDeAhorroEnDolares extends Cuenta {

	public CajaDeAhorroEnDolares(Cliente cliente, String alias) {
		super(cliente, alias);
	}

	// montoIngresado es en pesos
	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(consultarSaldo().add(montoIngresado));

	}


	// montoDescontado es en dolares
	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		if (haySaldo(montoDescontado)) {
			setSaldo(consultarSaldo().subtract(montoDescontado));
		} else
			System.out.println("Saldo insuficiente.");

	}

	private boolean haySaldo(BigDecimal montoDescontado) {
		return consultarSaldo().compareTo(montoDescontado) < 0;
	}

}
