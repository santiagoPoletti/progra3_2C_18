

public class EstructuraFicha {
	private boolean [][] matriz;
	private int filas;
	private int columnas;

	public EstructuraFicha(boolean [][] matriz) {
		super();
		this.matriz = matriz;
		this.filas =  matriz.length;
		this.columnas = matriz[0].length;
	}

	public boolean[][] getMatriz() {
		return matriz;
	}
	
	public int getSize(){
		int result = 0;
		result =  filas*columnas;
		return result;
	}
	
	public boolean esRotable() {
		if (esCuadrada() && esSolida()) {
			return false;
		}
		return true;
	}

	private boolean esSolida() {
		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < columnas; j++) {
				boolean elemento = matriz[i][j];
				if (!elemento) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean esCuadrada() {
		return this.filas==this.columnas;
	}
	
	public boolean esMitadRotable(){
		if (!esCuadrada() && esSolida()) {
			return true;
		}
		
		EstructuraFicha rotada180 = this.rotar90().rotar90();		
		if(this.equals(rotada180)){
			return true;
		}
		return false;
	}
	
	public EstructuraFicha rotar90() {
		boolean[][] rotada90 = new boolean[columnas][filas];
		for (int i = 0; i < filas; i++){
			for (int j = 0; j < columnas; j++){
				rotada90[j][i] = matriz[i][j];
			}
		}
		return new EstructuraFicha(rotateMatrix(matriz));
	}
	
	public boolean[][] rotateMatrix(boolean[][] matrix)
	{

			int n =matrix.length;
			int m = matrix[0].length;
			boolean [][] output = new boolean [m][n];

			for (int i=0; i<n; i++)
				for (int j=0;j<m; j++)
					output [j][n-1-i] = matrix[i][j];
			return output;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < filas; i++) {
			buffer.append("|");
			for (int j = 0; j < columnas; j++) {
				boolean elemento = matriz[i][j];
				buffer.append(elemento ? "X" : " ");
				buffer.append(" ");
				if (j == columnas-1){
					buffer.append("|");
					buffer.append("\n");
				}
			}
		}
		return buffer.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		
		if( obj instanceof EstructuraFicha){
			EstructuraFicha other = (EstructuraFicha) obj;
			if (other.getFilas() == this.getFilas() && other.getColumnas() == this.getColumnas()) {
				boolean result = true;
				boolean[][] otraMatriz = other.getMatriz();
				for (int i = 0; i < filas; i++){
					for (int j = 0; j < columnas; j++){
						result = result && otraMatriz[i][j] == matriz[i][j];
						if (!result){
							return false;
						}
					}
				}				
				return true;
			}
			return false;
		}else {
			return false;
		}
	}

	public int getFilas() {
		return filas;
	}

	public int getColumnas() {
		return columnas;
	}
}
