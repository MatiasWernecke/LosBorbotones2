package atm;

public class Tarjeta {

	private int numeroDeTarjeta;
	private int pin;

	public Tarjeta(int numeroDeTarjeta, int pin) {

		// numeroDeTarjeta debe tener 8 digitos sin repetirse
		setNumeroDeTarjeta(numeroDeTarjeta);
		// pin es un numero de 4 digitos
		setPin(pin);
	}

	public int getNumeroDeTarjeta() {
		return numeroDeTarjeta;
	}

	public void setNumeroDeTarjeta(int numeroDeTarjeta) {
		this.numeroDeTarjeta = numeroDeTarjeta;
	}

	public int getPin() {
		return pin;
	}

	public void setPin(int pin) {
		this.pin = pin;
	}

}
