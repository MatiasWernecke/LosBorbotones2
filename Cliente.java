package atm;

import java.util.LinkedList;

public class Cliente {

	private LinkedList<Tarjeta> tarjetas;
	private int cuit;
	
	public Cliente(int cuit) {
		
		tarjetas = new LinkedList<Tarjeta>();
		setCuit(cuit);
	}
	
	public void agregarTarjetas(Tarjeta tarjeta) {
		
		tarjetas.add(tarjeta);
	}

	public LinkedList<Tarjeta> getTarjetas() {
		return tarjetas;
	}

	public void setTarjetas(LinkedList<Tarjeta> tarjetas) {
		this.tarjetas = tarjetas;
	}

	public int getCuit() {
		return cuit;
	}

	public void setCuit(int cuit) {
		if (contarDigitos(cuit) == 11) {
			this.cuit = cuit;
		} else
			throw new Error("Cuit invalido");
	}
	
	private int contarDigitos(int num) {
		int contador = 0;
		int numero = num;
		while (numero != 0) {
			numero /= 10;
			contador++;
		}

		return contador;
	}
}
