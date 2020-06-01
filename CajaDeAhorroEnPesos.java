
import java.math.BigDecimal;

public class CajaDeAhorroEnPesos extends Cuenta {

	public CajaDeAhorroEnPesos(Cliente cliente, String alias) {
		super(cliente, alias);
	}

	public CajaDeAhorroEnPesos() {
		// TODO ApÃ©ndice de constructor generado automÃ¡ticamente
	}

	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(getSaldo().add(montoIngresado));

	}

	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		if (!haySaldo(montoDescontado)) {
			setSaldo(getSaldo().subtract(montoDescontado));
		}

	}

	public boolean haySaldo(BigDecimal montoDescontado) {
		return getSaldo().compareTo(montoDescontado) < 0 ;
	}

}
