package tp_atm;
import java.math.BigDecimal;


/*
 * uso la variable cuenta de la clase padre.
 * leonardo
 * 
 */

public class Transferir extends Transacciones implements Reversible{

	Cuenta cuenta;
	BigDecimal monto;

	public Transferir(Cuenta Cuenta) {

		super(Cuenta);

		monto = BigDecimal.ZERO;

	}

	public void transferir(BigDecimal montoATransferir, Cuenta otro) {
		
		this.monto = montoATransferir;
		this.cuenta = otro;
		
		super.getCuenta().descontarRetiroDeEfectivo(montoATransferir);
		
		otro.ingresarEfectivo(montoATransferir);
		
		movimiento();

	}

	public void movimiento() {

		Movimientos movimiento = new Movimientos("xxx", this.monto,
				super.getCuenta());

		super.getCuenta().agregarmovimento(movimiento);

	}

	@Override
	public void reversible() {
		
		this.cuenta.descontarRetiroDeEfectivo(this.monto);
		super.getCuenta().ingresarEfectivo(this.monto);
		
		
	}

}
