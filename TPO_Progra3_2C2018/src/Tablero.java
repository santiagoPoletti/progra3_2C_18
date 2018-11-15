

public class Tablero {
	
	private boolean[][] tablero;
	private int cantidadFilas;
	private int cantidadColumnas;
	private int cantidadFichas;
	private int rotacionesActuales = 0;
	private int maxRotaciones = 0;
	private Boolean Resuelto = false;
	private Ficha[] fichas;
	
	public Tablero(int cantidadFilas, int cantidadColumnas, int cantidadFichas, Ficha[] fichas) {
		super();
		this.cantidadFilas = cantidadFilas;
		this.cantidadColumnas = cantidadColumnas;
		this.cantidadFichas = cantidadFichas;
		this.fichas = fichas;
		this.tablero = new boolean[cantidadFilas][cantidadColumnas];
		for(int i = 0; i<cantidadFilas;i++) {
			for(int j = 0;j<cantidadColumnas;j++) {
				this.tablero[i][j] = false;
			}
		}
		
	}

	public boolean[][] getTablero() {
		return tablero;
	}

	public void setTablero(boolean[][] tablero) {
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
	
	public Ficha[] getFichas(){
		return this.fichas;
	}
	
	public Ficha getFicha(int index){
		Ficha ficha = this.fichas[index];
		return ficha;
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
	
	public boolean puedoColocarFicha(EstructuraFicha ficha,int filaActual,int columnaActual ) {
		
		boolean resultado = false;
		if((this.cantidadFilas >= filaActual + ficha.getFilas()) && (this.cantidadColumnas >= columnaActual + ficha.getColumnas())) {
			for(int i = 0; i < ficha.getFilas();i++) {
				for(int j = 0; j < ficha.getColumnas();j++) {
					if(this.tablero[i + filaActual][j + columnaActual]) {
						if(!ficha.getValue(i,j)) {
							resultado = true;
						}
						else {
							resultado = false;
						}
					}
					else {
						resultado = true;
					}
					if(!resultado) {
						return false;
					}
				}
			}
		}
		return resultado;
	}
	
	public void colocarFicha(EstructuraFicha ficha,int filaActual,int columnaActual ) {
		
		if(puedoColocarFicha(ficha, filaActual, columnaActual)) {
			for(int i = 0; i < ficha.getFilas();i++) {
				for(int j = 0; j < ficha.getColumnas();j++) {
					this.tablero[i + filaActual][j + columnaActual] = this.tablero[i + filaActual][j + columnaActual] || ficha.getValue(i,j);
				}
			}
		}
		System.out.println(this.tablero.toString());
	}
	
	
	public void removerFicha(EstructuraFicha ficha,int filaActual,int columnaActual ) {
		
		for(int i = 0; i < ficha.getFilas();i++) {
			for(int j = 0; j < ficha.getColumnas();j++) {
				if(this.tablero[i + filaActual][j + columnaActual] && ficha.getValue(i,j)) {
					this.tablero[i + filaActual][j + columnaActual] = false;
				}
			}
		}
		System.out.println(this.tablero.toString());
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Tablero es:\n"); 
		for (int i = 0; i < this.cantidadFilas; i++) {
			buffer.append("|");
			for (int j = 0; j < this.cantidadColumnas; j++) {
				boolean elemento = this.tablero[i][j];
				buffer.append(elemento ? "X" : " ");
				buffer.append(" ");
				if (j == this.cantidadColumnas - 1){
					buffer.append("|");
					buffer.append("\n");
				}
			}
		}
		return buffer.toString();
	}
	
}
