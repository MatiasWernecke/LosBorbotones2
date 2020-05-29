
import java.math.BigDecimal;

public class CajaDeAhorroEnPesos extends Cuenta {

	public CajaDeAhorroEnPesos(Cliente cliente, String alias) {
		super(cliente, alias);
	}
	
	public CajaDeAhorroEnPesos() {

	}

	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(consultarSaldo().add(montoIngresado));

	}

	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		if (haySaldo(montoDescontado)) {
			setSaldo(consultarSaldo().subtract(montoDescontado));
		}

	}

	private boolean haySaldo(BigDecimal montoADescontar) {
		return consultarSaldo().compareTo(montoADescontar) >= 0;
	}
}
