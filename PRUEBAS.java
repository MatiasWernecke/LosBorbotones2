
import java.math.BigDecimal;
import org.junit.Test;
import junit.framework.Assert;

public class PRUEBAS {

	Cliente leandro = new Cliente(null, 12345678901L);
	Cliente matias = new Cliente(null, 23456789L);
	Cliente leonardo = new Cliente(null, 34567890L);
	Cliente luciano = new Cliente(null, 45678901L);

	/**
	 * COMPRAR DOLARES
	 */

	// LA CUENTA TIENE SALDO Y COMPRA DOLARES
	@Test
	public void compraDolares() {

		Cuenta cajaMatiDolares = new CajaDeAhorroEnDolares(matias,
				"matiBorbotonesPesos");

		CajaDeAhorroEnPesos cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesDolares");

		ComprarDolares compra = new ComprarDolares(cajaMatiDolares,
				cajaMatiPesos);

		// Tiene saldo cero
		Assert.assertEquals(cajaMatiPesos.getSaldo(), BigDecimal.ZERO);
		Assert.assertEquals(cajaMatiDolares.getSaldo(), BigDecimal.ZERO);

		BigDecimal saldoACargar = new BigDecimal(5000);
		cajaMatiPesos.ingresarEfectivo(saldoACargar);

		BigDecimal cantAComprar = new BigDecimal(10);
		compra.comprarDolares(cantAComprar);

		BigDecimal saldoTotalEnPesos = new BigDecimal(4103);
		Assert.assertEquals(cajaMatiPesos.getSaldo(), saldoTotalEnPesos);

		BigDecimal saldoTotalEnDolares = new BigDecimal(10);
		Assert.assertEquals(cajaMatiDolares.getSaldo(), saldoTotalEnDolares);

	}

	// ERROR CUANDO SE INGRESA UN NUMERO NEGATIVO
	@Test(expected = Error.class)
	public void compraDolaresFallaConNumeroNegativo() {

		Cuenta cajaLeanDolares = new CajaDeAhorroEnDolares(leandro,
				"leanBorbotonesDolares");

		CajaDeAhorroEnPesos cajaLeanPesos = new CajaDeAhorroEnPesos(leandro,
				"leanBorbotonesPesos");

		ComprarDolares compra = new ComprarDolares(cajaLeanPesos, cajaLeanPesos);

		// Tiene saldo cero
		Assert.assertEquals(cajaLeanPesos.getSaldo(), BigDecimal.ZERO);
		Assert.assertEquals(cajaLeanDolares.getSaldo(), BigDecimal.ZERO);

		BigDecimal saldoACargar = new BigDecimal(5000);
		cajaLeanPesos.ingresarEfectivo(saldoACargar);

		BigDecimal cantAComprar = new BigDecimal(-10);
		compra.comprarDolares(cantAComprar);
	}

	// ERROR CUANDO QUERES COMPRAR DOLARES SIN PLATA EN LA CJA DE AHORRO EN
	// PESOS
	@Test(expected = Error.class)
	public void noCompraDolaresSinPlata() {

		Cuenta cajaLeoDolares = new CajaDeAhorroEnDolares(leonardo,
				"leoBorbotonesDolares");

		CajaDeAhorroEnPesos cajaLeoPesos = new CajaDeAhorroEnPesos(leonardo,
				"leoBorbotonesPesos");

		ComprarDolares compra = new ComprarDolares(cajaLeoDolares, cajaLeoPesos);

		// Tiene saldo cero en la caja de ahorro en pesos y la de dolares
		Assert.assertEquals(cajaLeoPesos.getSaldo(), BigDecimal.ZERO);
		Assert.assertEquals(cajaLeoDolares.getSaldo(), BigDecimal.ZERO);

		// Intertamos comprar sin plata en la caja de pesos
		BigDecimal cantAComprar = new BigDecimal(7);
		compra.comprarDolares(cantAComprar);

	}

	// COMPRA DOLARES CON LA PLATA JUSTA, QUEDANDO EN 0 SU CJA DE AHORROS EN
	// PESOS
	@Test
	public void compraDeDolaresConLaPlataJusta() {

		Cuenta cajaLuchoDolares = new CajaDeAhorroEnDolares(luciano,
				"luchoBorbotonesDolares");

		CajaDeAhorroEnPesos cajaLuchoPesos = new CajaDeAhorroEnPesos(luciano,
				"luchoBorbotonesPesos");

		ComprarDolares compra = new ComprarDolares(cajaLuchoDolares,
				cajaLuchoPesos);

		// Tiene saldo cero
		Assert.assertEquals(cajaLuchoPesos.getSaldo(), BigDecimal.ZERO);
		Assert.assertEquals(cajaLuchoDolares.getSaldo(), BigDecimal.ZERO);

		// vamos a comprar 10 dolares
		BigDecimal cantAComprar = new BigDecimal(10);

		// con el calculo del 30% (197) de 690 (10*69 que es cotiz actual)
		// le cargamos lo justo y necesario para poder comprar
		BigDecimal saldoACargar = new BigDecimal(897);
		cajaLuchoPesos.ingresarEfectivo(saldoACargar);

		compra.comprarDolares(cantAComprar);

		// Verificamos que tiene 0 pesos
		Assert.assertEquals(cajaLuchoPesos.getSaldo(), BigDecimal.ZERO);

		// Verificamos que se acreditaron los dolares
		Assert.assertEquals(cajaLuchoDolares.getSaldo(), cantAComprar);

	}

	/**
	 * VENDER DOLARES
	 */

	// VENDO DOLARES Y RECIBO LA PLATA EN LA CJA DE AHORRO EN PESOS
	@Test
	public void vendoDolaresYReciboPesos() {

		Cuenta cajaMatiDolares = new CajaDeAhorroEnDolares(matias,
				"matiBorbotonesDolares");

		CajaDeAhorroEnPesos cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesPesos");

		// No tienen saldo ninguna de las cajas
		Assert.assertEquals(cajaMatiPesos.getSaldo(), BigDecimal.ZERO);
		Assert.assertEquals(cajaMatiDolares.getSaldo(), BigDecimal.ZERO);

		// Le acreditamos 10 dolares a la caja de dolares
		BigDecimal dolaresACargar = new BigDecimal(10);
		cajaMatiDolares.ingresarEfectivo(dolaresACargar);

		// comprobamos que acredito bien
		Assert.assertEquals(cajaMatiDolares.getSaldo(), dolaresACargar);

		VenderDolares venta = new VenderDolares(cajaMatiDolares, cajaMatiPesos);

		// Venderemos 5 dolares
		BigDecimal cantAVender = new BigDecimal(5);
		venta.venderDolares(cantAVender);

		// Si vendimos 5 dolares y la venta cotiza en 63$, recibiremos 315$
		// pesos (5*63 = 315)
		// comprobamos que recibimos el dinero
		BigDecimal saldoTotalEnPesos = new BigDecimal(315);
		Assert.assertEquals(cajaMatiPesos.getSaldo(), saldoTotalEnPesos);

		// Si vendimos 5 dolares, nos quedan 5 dolares
		Assert.assertEquals(cajaMatiDolares.getSaldo(), cantAVender);

	}

	// ERROR AL VENDER DOLARES QUE NO TENGO
	@Test(expected = Error.class)
	public void noVendeDolaresSiNoTieneEnCajaDeDolares() {

		Cuenta cajaLeanDolares = new CajaDeAhorroEnDolares(leandro,
				"leanBorbotonesPesos");

		CajaDeAhorroEnPesos cajaLeanPesos = new CajaDeAhorroEnPesos(leandro,
				"leanBorbotonesDolares");

		VenderDolares venta = new VenderDolares(cajaLeanDolares, cajaLeanPesos);

		// No tiene saldo
		Assert.assertEquals(cajaLeanDolares.getSaldo(), BigDecimal.ZERO);

		// Venderemos 5 dolares
		BigDecimal cantAVender = new BigDecimal(5);
		venta.venderDolares(cantAVender);
	}

	// ERROR AL INGRESAR UN NUMERO NEGATIVO
	@Test(expected = Error.class)
	public void venderDolaresFallaConNumeroNegativo() {
		Cuenta cajaLeoDolares = new CajaDeAhorroEnDolares(leonardo,
				"leoBorbotonesDolares");

		CajaDeAhorroEnPesos cajaLeoPesos = new CajaDeAhorroEnPesos(leonardo,
				"leoBorbotonesPesos");

		VenderDolares venta = new VenderDolares(cajaLeoDolares, cajaLeoPesos);

		// Le acreditamos 10 dolares a la caja de dolares
		BigDecimal cantDolaresACargar = new BigDecimal(10);
		cajaLeoDolares.ingresarEfectivo(cantDolaresACargar);

		// comprobamos que acredito bien
		Assert.assertEquals(cajaLeoDolares.getSaldo(), cantDolaresACargar);

		// Venderemos 5 dolares
		BigDecimal cantAVender = new BigDecimal(-5);
		venta.venderDolares(cantAVender);
	}

	/**
	 * DEPOSITO
	 */

		@Test
	public void testDepositoEnCuentaCorriente() {

		long cuit = 12345678990L;
		Cliente c = new Cliente(cuit);

		BigDecimal descubierto = BigDecimal.valueOf(10000);

		Cuenta cc = new CuentaCorriente(c, "eduardo", descubierto);

		BigDecimal deposito = BigDecimal.valueOf(35000);
		BigDecimal deposito2 = BigDecimal.valueOf(25000);
		Depositar t1 = new Depositar(cc);
		t1.depositarPesos(deposito);
		t1.depositarPesos(deposito2);
		BigDecimal resultado = cc.getSaldo();
		BigDecimal esperado = BigDecimal.valueOf(60000);
		Assert.assertEquals(resultado, esperado);

	}

	@Test
	public void testDepositoEnCajaDeAhorroEnPesos() {

		long cuit = 143656636012L;
		Cliente c = new Cliente(cuit);
		Cuenta cap = new CajaDeAhorroEnPesos(c, "eduardo");
		BigDecimal deposito = BigDecimal.valueOf(100000);
		BigDecimal deposito2 = BigDecimal.valueOf(22000);
		Depositar d = new Depositar(cap);
		d.depositarPesos(deposito);
		d.depositarPesos(deposito2);

		BigDecimal resultado = cap.getSaldo();
		BigDecimal esperado = BigDecimal.valueOf(122000);
		Assert.assertEquals(resultado, esperado);

	}
	
	@Test
	public void depositarEnPesosCorrectamente() {
		Cuenta cajaLuchoPesos = new CajaDeAhorroEnPesos(luciano,
				"luchoBorbotonesPesos");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaLuchoPesos.getSaldo());

		Depositar depo = new Depositar(cajaLuchoPesos);

		BigDecimal deposito = new BigDecimal(2500);

		depo.depositarPesos(deposito);

		// Despues de depositar 2500 la caja de ahorro en pesos debe tener 2500
		Assert.assertEquals(deposito, cajaLuchoPesos.getSaldo());

	}

	@Test(expected = Error.class)
	public void depositarEnPesosFallaConNumeroNegativo() {
		Cuenta cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesPesos");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaMatiPesos.getSaldo());

		Depositar depo = new Depositar(cajaMatiPesos);

		BigDecimal deposito = new BigDecimal(-2500);

		depo.depositarDolares(deposito);

	}

	@Test(expected = Error.class)
	public void depositarEnPesosFallaSiNoIngresamosMonto() {
		Cuenta cajaLeanPesos = new CajaDeAhorroEnPesos(leandro,
				"leanBorbotonesPesos");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaLeanPesos.getSaldo());

		Depositar depo = new Depositar(cajaLeanPesos);

		BigDecimal deposito = new BigDecimal(0);

		depo.depositarDolares(deposito);

	}

	@Test
	public void depositarEnCajaDolares() {
		Cuenta cajaLeoDolares = new CajaDeAhorroEnDolares(leonardo,
				"leoBorbotonesDolares");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaLeoDolares.getSaldo());

		Depositar depo = new Depositar(cajaLeoDolares);

		BigDecimal deposito = new BigDecimal(500);

		depo.depositarDolares(deposito);

		// Despues de depositar 500 la caja de ahorro en pesos debe tener 500
		Assert.assertEquals(deposito, cajaLeoDolares.getSaldo());

	}

	@Test(expected = Error.class)
	public void depositarEnDolaresFallaSiNoIngresamosMonto() {
		Cuenta cajaLuchoDolares = new CajaDeAhorroEnDolares(luciano,
				"luchoBorbotonesDolares");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaLuchoDolares.getSaldo());

		Depositar depo = new Depositar(cajaLuchoDolares);

		BigDecimal deposito = new BigDecimal(-700);

		depo.depositarDolares(deposito);

	}

	@Test(expected = Error.class)
	public void depositarEnDolaresFallaSiIngresamosNumeroNegativo() {
		Cuenta cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesPesos");

		// Corroboramos que no tiene plata
		Assert.assertEquals(BigDecimal.ZERO, cajaMatiPesos.getSaldo());

		Depositar depo = new Depositar(cajaMatiPesos);

		BigDecimal deposito = new BigDecimal(-10);

		depo.depositarDolares(deposito);

	}

	/**
	 * TRANSFERENCIA
	 */

	@Test
	public void trasnferirDesdeCajaDeAhorroACuentaCorriente() {
		Cuenta cajaLeanPesos = new CajaDeAhorroEnPesos(leandro,
				"leanBorbotonesPesos");
		BigDecimal descubierto = BigDecimal.valueOf(1000);
		Cuenta cajaLeoCorriente = new CuentaCorriente(leonardo,
				"leoBorbotonesCorriente", descubierto);

		// Como no tiene plata una cuenta le,cargamos plata
		Assert.assertEquals(BigDecimal.ZERO, cajaLeanPesos.getSaldo());
		BigDecimal plata = BigDecimal.valueOf(3000);
		cajaLeanPesos.ingresarEfectivo(plata);

		// Corroboramos que tiene plata
		Assert.assertEquals(plata, cajaLeanPesos.getSaldo());

		Transferencia tran = new Transferencia(cajaLeanPesos);

		// Le transferimos plata
		BigDecimal monto = BigDecimal.valueOf(1600);
		tran.transferencia(monto, cajaLeoCorriente);

		// Aseguramos que le transfirio
		BigDecimal saldoActualLean = BigDecimal.valueOf(1400);
		Assert.assertEquals(saldoActualLean, cajaLeanPesos.getSaldo());
		Assert.assertEquals(monto, cajaLeoCorriente.getSaldo());
	}

	@Test
	public void trasnferirDesdeCuentaCorrienteACajaDeAhorro() {
		BigDecimal descubierto = BigDecimal.valueOf(1000);
		Cuenta cuentaLuchoCorriente = new CuentaCorriente(luciano,
				"luchoBorbotonesCorriente", descubierto);

		Cuenta cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesPesos");

		// Como no tiene plata una cuenta le,cargamos plata
		Assert.assertEquals(BigDecimal.ZERO, cuentaLuchoCorriente.getSaldo());
		BigDecimal plata = BigDecimal.valueOf(4000);
		cuentaLuchoCorriente.ingresarEfectivo(plata);

		Transferencia tran = new Transferencia(cuentaLuchoCorriente);

		// Le transferimos plata
		BigDecimal montoATransferir = BigDecimal.valueOf(2300);
		tran.transferencia(montoATransferir, cajaMatiPesos);

		// Aseguramos que le transfirio
		BigDecimal saldoActualMati = BigDecimal.valueOf(1700);
		Assert.assertEquals(saldoActualMati, cuentaLuchoCorriente.getSaldo());
		Assert.assertEquals(montoATransferir, cajaMatiPesos.getSaldo());
	}

	@Test
	public void trasnferirFallaSiNoHayPlata() {
		BigDecimal descubierto = BigDecimal.valueOf(1000);

		Cuenta cuentaLeanCorriente = new CuentaCorriente(leandro,
				"leanBorbotonesCorriente", descubierto);

		Cuenta cajaLeoPesos = new CajaDeAhorroEnPesos(leonardo,
				"leoBorbotonesPesos");

		// Como no tiene plata una cuenta le,cargamos plata
		Assert.assertEquals(BigDecimal.ZERO, cuentaLeanCorriente.getSaldo());
		BigDecimal plata = BigDecimal.valueOf(4000);
		cuentaLeanCorriente.ingresarEfectivo(plata);

		Transferencia tran = new Transferencia(cuentaLeanCorriente);

		// Le transferimos plata
		BigDecimal montoATransferir = BigDecimal.valueOf(2300);
		tran.transferencia(montoATransferir, cajaLeoPesos);

		// Aseguramos que le transfirio
		BigDecimal saldoActualMati = BigDecimal.valueOf(1700);
		Assert.assertEquals(saldoActualMati, cuentaLeanCorriente.getSaldo());
		Assert.assertEquals(montoATransferir, cajaLeoPesos.getSaldo());

	}

	@Test(expected = Error.class)
	public void errorAlTransferirDesdeCajaDePesosACajaDeDolares() {

		Cuenta cajaLuchoDolares = new CajaDeAhorroEnDolares(luciano,
				"luchoBorbotonesDolares");

		Cuenta cajaMatiPesos = new CajaDeAhorroEnPesos(matias,
				"matiBorbotonesPesos");

		// Como no tiene plata una cuenta le,cargamos plata
		Assert.assertEquals(BigDecimal.ZERO, cajaMatiPesos.getSaldo());

		BigDecimal plata = BigDecimal.valueOf(4000);

		cajaMatiPesos.ingresarEfectivo(plata);

		Transferencia tran = new Transferencia(cajaMatiPesos);

		// Le transferimos plata
		BigDecimal montoATransferir = BigDecimal.valueOf(2300);
		tran.transferencia(montoATransferir, cajaLuchoDolares);
	}

	/**
	 * RETIRAR EFECTIVO
	 */

	@Test
	public void retirarEfectivoDesdeLaCajaDePesos() {

		Cuenta cajaLeanPesos = new CajaDeAhorroEnPesos(leandro,
				"leanBorbotonesDolares");

		// Tiene saldo cero
		Assert.assertEquals(cajaLeanPesos.getSaldo(), BigDecimal.ZERO);

		BigDecimal saldoACargar = new BigDecimal(5000);

		Depositar depo = new Depositar(cajaLeanPesos);

		cajaLeanPesos.ingresarEfectivo(saldoACargar);

		// Verificamos que le cargamos plata

		Assert.assertEquals(cajaLeanPesos.getSaldo(), saldoACargar);

		RetirarEfectivo retirar = new RetirarEfectivo(cajaLeanPesos);

		BigDecimal montoARetirar = new BigDecimal(3000);

		retirar.retirarEfectivo(montoARetirar);

		// Verificamos que le extrajo correctamente

		Assert.assertEquals(cajaLeanPesos.getSaldo(),
				saldoACargar.subtract(montoARetirar));

	}

	@Test
	public void retirarEfectivoDesdeLaCuentaCorrienteSinPlata() {

		BigDecimal descubierto = new BigDecimal(1000);

		Cuenta cuentaLeoCorriente = new CuentaCorriente(leonardo,
				"leoBorbotonesDolares", descubierto);

		// Tiene saldo cero pero extraemos por que tiene descubierto de 1000
		Assert.assertEquals(cuentaLeoCorriente.getSaldo(), BigDecimal.ZERO);

		RetirarEfectivo retirar = new RetirarEfectivo(cuentaLeoCorriente);

		BigDecimal montoARetirar = new BigDecimal(500);

		retirar.retirarEfectivo(montoARetirar);

		// Saldo final de la cuenta corriente en pesos
		BigDecimal saldoFinalCtaCorriente = new BigDecimal(-500);

		// Verificamos que le extrajo correctamente por el descubierto

		Assert.assertEquals(cuentaLeoCorriente.getSaldo(),
				saldoFinalCtaCorriente);

	}

	@Test
	public void retiraEfectivoDesdeLaCuentaCorriente() {

		BigDecimal descubierto = new BigDecimal(1000);

		Cuenta cuentaLuchitoCorriente = new CuentaCorriente(luciano,
				"luchoBorbotonesCorriente", descubierto);

		// Tiene saldo cero y un descubierto de 1000
		Assert.assertEquals(cuentaLuchitoCorriente.getSaldo(), BigDecimal.ZERO);

		BigDecimal saldoACargar = new BigDecimal(5000);

		Depositar depo = new Depositar(cuentaLuchitoCorriente);

		depo.depositarPesos(saldoACargar);

		// Vemos que deposito bien el dinero
		Assert.assertEquals(cuentaLuchitoCorriente.getSaldo(), saldoACargar);

		RetirarEfectivo retirar = new RetirarEfectivo(cuentaLuchitoCorriente);

		BigDecimal montoARetirar = new BigDecimal(3000);

		retirar.retirarEfectivo(montoARetirar);

		// Verificamos que retiro efectivo
		Assert.assertEquals(cuentaLuchitoCorriente.getSaldo(),
				saldoACargar.subtract(montoARetirar));

	}

	@Test(expected = Error.class)
	public void errorAlExtraerDesdeCajaDeDolares() {
		Cuenta cajaMatiDolares = new CajaDeAhorroEnDolares(matias,
				"matiBorbotonesDolares");
		
		//cargamos plata
		BigDecimal saldoACargar = new BigDecimal(2000);

		Depositar depo = new Depositar(cajaMatiDolares);

		depo.depositarDolares(saldoACargar);

		// Vemos que deposito bien el dinero
		Assert.assertEquals(cajaMatiDolares.getSaldo(), saldoACargar);

		RetirarEfectivo retirar = new RetirarEfectivo(cajaMatiDolares);

		BigDecimal montoARetirar = new BigDecimal(3000);

		retirar.retirarEfectivo(montoARetirar);

		// Intentamos Retirar y dara error
		
	}
}
