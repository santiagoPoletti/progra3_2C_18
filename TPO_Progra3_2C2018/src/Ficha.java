

import java.util.ArrayList;
import java.util.List;

public class Ficha {
	private int cantidadFilas;
	private int cantidadColumnas;
	private List<EstructuraFicha> rotaciones ;
	private Integer size;
	
	public Ficha(int cantidadFilas, int cantidadColumnas, String[] matrizEstirada) {
		super();
		this.cantidadFilas = cantidadFilas;
		this.cantidadColumnas = cantidadColumnas;
		
		this.rotaciones = new ArrayList<EstructuraFicha>();
				
		int counter =0;
		boolean [][] matriz=new boolean[cantidadFilas][cantidadColumnas];
		
		for (int i = 0; i < cantidadFilas; i++) {
			for (int j = 0; j < cantidadColumnas; j++) {
				boolean elemento = Integer.valueOf(matrizEstirada[ counter ]) > 0 ? true : false;
				matriz[i][j] = elemento;
				counter++;
			}
		}

		EstructuraFicha estructuraFichaOriginal = new EstructuraFicha(matriz);
		this.size= estructuraFichaOriginal.getSize();
		System.out.println(estructuraFichaOriginal);
		System.out.println("largo ficha: "+ estructuraFichaOriginal.getSize());
		rotaciones.add(estructuraFichaOriginal);
		
		if (estructuraFichaOriginal.esRotable()){
			if(estructuraFichaOriginal.esMitadRotable()){
				//agrego una estructura mas
				System.out.println("agrego una estructura mas" );
				EstructuraFicha rotada90 = estructuraFichaOriginal.rotar90();
				rotaciones.add(rotada90);
				System.out.println(rotada90);
			}else{
				System.out.println("es rotable full - > tengo q agregar 3 mas");
				EstructuraFicha rotada90 = estructuraFichaOriginal.rotar90();
				rotaciones.add(rotada90);
				System.out.println(rotada90);
				EstructuraFicha rotada180 = rotada90.rotar90();
				rotaciones.add(rotada180);
				System.out.println(rotada180);
				EstructuraFicha rotada270 = rotada180.rotar90();
				rotaciones.add(rotada270);
				System.out.println(rotada270);
			}
			
			
		}
		
	}


	public int getCantidadFilas() {
		return cantidadFilas;
	}


	public int getCantidadColumnas() {
		return cantidadColumnas;
	}


	public List<EstructuraFicha> getRotaciones() {
		return rotaciones;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public boolean esRotable(int index) {
		return this.rotaciones.get(index).esRotable();
	}
	
	public int getCantidadRotaciones(int index) {
		return this.rotaciones.size();
	}
	
	public EstructuraFicha getEstructuraFicha(int index) {
		return this.rotaciones.get(index);
	}
}
