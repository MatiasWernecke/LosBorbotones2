package atm;

import java.util.LinkedList;

public class Cliente {

	private LinkedList<Tarjeta> tarjetas;
	private int cuit;
	
	public Cliente(int cuit) {
		
		tarjetas = new LinkedList<Tarjeta>();
		//cuit debe ser de 11 digitos
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
		this.cuit = cuit;
	}
	
	
}
