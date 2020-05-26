package ATM_TP;

public class Tarjeta {

	private int numeroDeTarjeta;
	private int pin;

	public Tarjeta(int numeroDeTarjeta, int pin) {

		setNumeroDeTarjeta(numeroDeTarjeta);
		setPin(pin);
	}

	public int getNumeroDeTarjeta() {
		return numeroDeTarjeta;
	}

	public void setNumeroDeTarjeta(int numeroDeTarjeta) {
		if (numeroDeTarjetaValido(numeroDeTarjeta)) {
			this.numeroDeTarjeta = numeroDeTarjeta;
		} else
			throw new Error("Numero de tarjeta invalido");

	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		if (contarDigitos(pin) == 4) {
			this.pin = pin;
		} else 
			throw new Error("Pin invalido");
	}

	// Metodos privados
	private int contarDigitos(int num) {
		int contador = 0;
		int numero = num;
		while (numero != 0) {
			numero /= 10;
			contador++;
		}

		return contador;
	}

	private boolean noSeRepitenDigitos(int num) {
		int numMask = 0;
		int numDigits = (int) Math.ceil(Math.log10(num + 1));
		for (int digitIdx = 0; digitIdx < numDigits; digitIdx++) {
			int curDigit = (int) (num / Math.pow(10, digitIdx)) % 10;
			int digitMask = (int) Math.pow(2, curDigit);
			if ((numMask & digitMask) > 0)
				return false;
			numMask = numMask | digitMask;
		}
		return true;
	}

	private boolean numeroDeTarjetaValido(int numeroDeTarjeta) {
		return contarDigitos(numeroDeTarjeta) == 8 && noSeRepitenDigitos(numeroDeTarjeta);
	}
}