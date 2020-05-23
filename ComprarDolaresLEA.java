package TP_ATM;

import java.math.BigDecimal;

public class ComprarDolares extends Transacciones {

	private BigDecimal impuestoPAIS;
	private int cotizacionDolar = 69;
	private Cuenta agujeroNegro;
	private CajaDeAhorroEnDolares caja;

	public ComprarDolares(Cuenta cuenta, BigDecimal monto,
			CajaDeAhorroEnDolares caja) {
		super(cuenta, monto);
		this.caja = caja;
		this.impuestoPAIS = new BigDecimal(0.30);
	}

	public void comprarDolares(int cantAComprar) {
		BigDecimal dolaresConvertidosAPesos = new BigDecimal(cantAComprar * cotizacionDolar); 
		
		BigDecimal impuesto = new BigDecimal(dolaresConvertidosAPesos.multiply(impuestoPAIS).intValue());
		
		if (super.cuenta.getSaldo() >= (impuesto.doubleValue() + dolaresConvertidosAPesos.doubleValue())) {
			
			caja.acreditarDolares(dolaresConvertidosAPesos);
			
			aplicarImpuestoPais(dolaresConvertidosAPesos);
		}
	}

	public void aplicarImpuestoPais(BigDecimal dolaresEnPesos) {
		BigDecimal impuesto = new BigDecimal(dolaresEnPesos.multiply(impuestoPAIS).doubleValue());
		caja.restarImpuestoPais(impuesto);
		acreditarEnAgujeroNegroLosImpuestos(agujeroNegro, impuesto);
	}
	
	public void acreditarEnAgujeroNegroLosImpuestos(Cuenta agujeroNegro, BigDecimal impuesto) {
		this.agujeroNegro.cargarDinero(impuesto);
	}
}