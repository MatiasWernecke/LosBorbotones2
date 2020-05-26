package ATM_TP;
import java.util.LinkedList;

import ATM_TP.Tarjeta;

public class Cliente {

	private LinkedList<Tarjeta> tarjetas;
	private long cuit;
	
	public Cliente(long cuit) {
		if(cuit < 99999999999L || cuit < 11111111111L) {
			setCuit(cuit);
			tarjetas = new LinkedList<Tarjeta>();
			
		}
		
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

	public long getCuit() {
		return cuit;
	}

	public void setCuit(long cuit) {
		if (contarDigitos(cuit) != 11) {
			this.cuit = cuit;
		} else
			throw new Error("Cuit invalido");
	}
	
	private int contarDigitos(long num) {
		int contador = 0;
		long numero = num;
		while (numero != 0) {
			numero /= 10;
			contador++;
		}

		return contador;
	}
}