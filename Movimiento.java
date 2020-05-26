package atm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Movimiento {

	private LocalDate fecha;
	private String conceptos;
	private BigDecimal importe;
	private Cuenta cuenta;

	public Movimiento(String conceptos, BigDecimal importe, Cuenta cuenta) {
		
		fecha = LocalDate.now();
		//conceptos puede tener hasta 30 caracteres
		this.conceptos = conceptos;
		this.importe = importe;
		this.cuenta = cuenta;

	}

	public LocalDate getFecha() {
		return fecha;
	}

	public String getConceptos() {
		return conceptos;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public String getAliasDeLaCuenta() {
		return cuenta.getAlias();
	}

	public String toString() {

		return this.fecha + "," + " " + this.conceptos + "," + " " + this.cuenta.getAlias() + "," + " " + this.importe;
	}
}
