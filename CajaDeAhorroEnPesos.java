
import java.math.BigDecimal;

public class CajaDeAhorroEnPesos extends Cuenta {

	public CajaDeAhorroEnPesos(Cliente cliente, String alias) {
		super(cliente, alias);
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

	public boolean haySaldo(BigDecimal montoDescontado) {
		return super.consultarSaldo().compareTo(montoDescontado) >= 0 ;
	}


}
