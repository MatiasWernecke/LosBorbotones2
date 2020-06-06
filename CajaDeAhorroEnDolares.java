import java.math.BigDecimal;

public class CajaDeAhorroEnDolares extends Cuenta {

	public CajaDeAhorroEnDolares(Cliente cliente, String alias) {
		super(cliente, alias);
	}
	
	public CajaDeAhorroEnDolares() {
		
	}

	// montoIngresado es en pesos
	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(getSaldo().add(montoIngresado));

	}


	// montoDescontado es en dolares
	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		if (haySaldo(montoDescontado)) {
			setSaldo(getSaldo().subtract(montoDescontado));
		} else
			System.err.println("Saldo insuficiente.");

	}

	private boolean haySaldo(BigDecimal montoADescontar) {
		return getSaldo().compareTo(montoADescontar) >= 0;
	}

}
