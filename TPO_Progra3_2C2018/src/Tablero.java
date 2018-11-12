import java.*;
import java.util.List;

public class Tablero {
	
	private Boolean[][] tablero;
	private int cantidadFilas;
	private int cantidadColumnas;
	private int cantidadFichas;
	private int rotacionesActuales = 0;
	private int maxRotaciones = 0;
	private Boolean Resuelto = false;
	private List<Ficha> fichas;
	
	public Tablero(int cantidadFilas, int cantidadColumnas, int cantidadFichas, List<Ficha> fichas) {
		super();
		this.cantidadFilas = cantidadFilas;
		this.cantidadColumnas = cantidadColumnas;
		this.cantidadFichas = cantidadFichas;
		this.fichas = fichas;
		for(int i = 0; i<cantidadFilas;i++) {
			for(int j = 0;j<cantidadColumnas;j++) {
				this.tablero[i][j] = false;
			}
		}
		
	}

	public Boolean[][] getTablero() {
		return tablero;
	}

	public void setTablero(Boolean[][] tablero) {
		this.tablero = tablero;
	}

	public int getCantidadFilas() {
		return cantidadFilas;
	}

	public void setCantidadFilas(int cantidadFilas) {
		this.cantidadFilas = cantidadFilas;
	}

	public int getCantidadColumnas() {
		return cantidadColumnas;
	}

	public void setCantidadColumnas(int cantidadColumnas) {
		this.cantidadColumnas = cantidadColumnas;
	}

	public int getCantidadFichas() {
		return cantidadFichas;
	}

	public void setCantidadFichas(int cantidadFichas) {
		this.cantidadFichas = cantidadFichas;
	}

	public int getRotacionesActuales() {
		return rotacionesActuales;
	}

	public void setRotacionesActuales(int rotacionesActuales) {
		this.rotacionesActuales = rotacionesActuales;
	}

	public int getMaxRotaciones() {
		return maxRotaciones;
	}

	public void setMaxRotaciones(int maxRotaciones) {
		this.maxRotaciones = maxRotaciones;
	}

	public Boolean getResuelto() {
		return Resuelto;
	}

	public void setResuelto(Boolean resuelto) {
		Resuelto = resuelto;
	}

	public Boolean estaCompleto() {
		
		boolean espacioOcupado = true;
		for(int i = 0; i<cantidadFilas;i++) {
			for(int j = 0;j<cantidadColumnas;j++) {
				espacioOcupado = espacioOcupado && this.tablero[i][j];
				if(!espacioOcupado) {
					return false;
				}
			}
		}
		return espacioOcupado;
	}
	
}
