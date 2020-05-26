package atm;

import java.math.BigDecimal;

public class CajaDeAhorroEnDolares extends Cuenta {

	private BigDecimal impuestoPais;
	private BigDecimal precioDolar;

	public CajaDeAhorroEnDolares(Cliente cliente, String alias) {
		super(cliente, alias);
		impuestoPais = new BigDecimal(0.30);
		precioDolar = new BigDecimal(68);
	}

	// montoIngresado es en pesos
	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(consultarSaldo().add(aplicarImpuestoPAIS(montoIngresado))
				.divide(precioDolar));

	}


	// montoDescontado es en dolares
	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		if (haySaldo(montoDescontado)) {
			setSaldo(consultarSaldo().subtract(montoDescontado));
		}

	}

	private boolean haySaldo(BigDecimal montoDescontado) {
		return consultarSaldo().compareTo(montoDescontado) != -1;
	}

	private BigDecimal aplicarImpuestoPAIS(BigDecimal montoIngresado) {
		return montoIngresado.subtract(montoIngresado.multiply(impuestoPais));
	}
}
