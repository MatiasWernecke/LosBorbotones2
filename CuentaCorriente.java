
import java.math.BigDecimal;
import java.util.LinkedList;

public class CuentaCorriente extends Cuenta {

	private BigDecimal descubierto;

	public CuentaCorriente(Cliente cliente, String alias, BigDecimal descubierto) {
		super(cliente, alias);
		this.descubierto = descubierto;
	}

	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(consultarSaldo().add(montoIngresado));
	}

	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		//A.compareTo(B): este metodo retorna -1 si A < B, 0 si A = B, 1 si A > B
		if(haySaldo(montoDescontado)) {
			setSaldo(consultarSaldo().subtract(montoDescontado));
		}
	}

	private boolean haySaldo(BigDecimal montoDescontado) {
		return consultarSaldo().subtract(montoDescontado).compareTo(descubierto) != -1;
	}

}
