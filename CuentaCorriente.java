
import java.math.BigDecimal;
import java.util.LinkedList;

public class CuentaCorriente extends Cuenta {

	private BigDecimal descubierto;

	public CuentaCorriente(Cliente cliente, String alias, BigDecimal descubierto) {
		super(cliente, alias);
		this.descubierto = descubierto;
	}
	public CuentaCorriente() {
		
	}

	@Override
	public void ingresarEfectivo(BigDecimal montoIngresado) {
		setSaldo(getSaldo().add(montoIngresado));
	}

	@Override
	public void descontarEfectivo(BigDecimal montoDescontado) {
		//A.compareTo(B): este metodo retorna -1 si A < B, 0 si A = B, 1 si A > B
		if(haySaldo(montoDescontado)) {
			setSaldo(getSaldo().subtract(montoDescontado));
		}
	}
	
	public BigDecimal getDescubierto() {
		return descubierto;
	}

	public void setDescubierto(BigDecimal descubierto) {
		this.descubierto = descubierto;
	}

	private boolean haySaldo(BigDecimal montoDescontado) {
		return getSaldo().add(descubierto.negate()).compareTo(montoDescontado) > -1;
	}
}
