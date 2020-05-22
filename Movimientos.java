import java.math.BigDecimal;
import java.util.Date;

public class Movimientos {

	private Date fecha;
	private String conceptos;
	private Cuenta cuenta;
	private BigDecimal importe;

	public Movimientos(Date fecha, String conceptos, Cuenta cuenta,
			BigDecimal importe) {
		this.cuenta = cuenta;
		this.conceptos = conceptos;
		this.fecha = fecha;
		this.importe = importe;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public String getConceptos() {
		return this.conceptos;
	}

	public String getAliasDeCuenta() {
		return cuenta.getAlias();
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public String toString() {
		return getAliasDeCuenta() + "/nEn conceptos de: " + getConceptos()
				+ "/nFecha: " + getFecha() + "/nPor: " + getImporte() + "$";
	}
}