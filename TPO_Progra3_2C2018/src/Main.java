

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String configuracion = obtenerConfiguracion();
		Tablero tablero = parsearTablero(configuracion);
		boolean resultado = false;
		resultado = rompeCabezasTetris(tablero,0,0,-1);
		if(resultado) {
			System.out.println("Se pudo resolver");
		}
		else {
			System.out.println("No se pudo resolver");
		}
		


	}

	private static Tablero parsearTablero(String configuracion) {
		int cantidadFilas = Character.getNumericValue(configuracion.charAt(0));
		int cantidadColumnas = Character.getNumericValue(configuracion.charAt(2));
		int cantidadFichas = Character.getNumericValue(configuracion.charAt(4));
		List<Ficha> fichas = parsearFichas(configuracion);
		fichas.sort((Ficha f1, Ficha f2) -> f2.getSize().compareTo(f1.getSize() ));
		Ficha[] fichasArray  = fichas.stream().toArray(Ficha[]::new);
		return new Tablero(cantidadFilas, cantidadColumnas, cantidadFichas,fichasArray);
	}

	private static List<Ficha> parsearFichas(String configuracion) {
		configuracion = configuracion.substring(6).replaceAll("[()]", "");
		String[] fichaStrArray = configuracion.split(" ");
		List<Ficha> fichas = new ArrayList<Ficha>();
		for (int i = 0; i < fichaStrArray.length; i++) {
			String fichaStr = fichaStrArray[i];
												
			String[] fichaPartes = fichaStr.split("-");
			String[] dimensionesFicha = fichaPartes[0].split(",");
			String[] matrizEstirada = fichaPartes[1].split(",");

			System.out.println(fichaStr);
			Ficha ficha = new Ficha(Integer.valueOf(dimensionesFicha[0]),Integer.valueOf(dimensionesFicha[1]), matrizEstirada);
			fichas.add(ficha);
		}
		return fichas;
	}

	private static String obtenerConfiguracion() throws IOException {
		String strpath = "src/input.txt";
		CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.IGNORE);
		File file = new File(strpath);
		Path path = Paths.get(file.toURI());
		List<String> lines;
		try (Reader r = Channels.newReader(FileChannel.open(path), dec, -1);
				BufferedReader br = new BufferedReader(r)) {
			lines = br.lines().collect(Collectors.toList());
		}
		return lines.get(0);
	}

	private static boolean rompeCabezasTetris(Tablero tablero,int fichaActual,int cantRotacionActual, int minimoRotaciones) {
		
		boolean haySolucion = false;
		System.out.println("Ejecuci�n recursiva con: fichaActual =" + fichaActual + " cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
		if(tablero.estaCompleto()) {
			if(minimoRotaciones == -1 || cantRotacionActual <= minimoRotaciones) {
				minimoRotaciones = cantRotacionActual;
				if(minimoRotaciones == 0) {
					System.out.println("cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
					return true;
				}
			}
		}
		else {
			while(!haySolucion && (minimoRotaciones == -1 || cantRotacionActual < minimoRotaciones) && fichaActual < tablero.getCantidadFichas()) {
				System.out.println(tablero.toString());
				System.out.println("cantRotacionActual:" + cantRotacionActual);
				for (int i = 0; i < tablero.getCantidadFilas(); i++) {
					for (int j = 0; j < tablero.getCantidadColumnas(); j++) {
						System.out.println("Probando Ficha en Tablero: Fila ="+i+ " Columna = "+j);
						Ficha fichaAct = tablero.getFicha(fichaActual);
						List<EstructuraFicha> fichasRotadas = fichaAct.getRotaciones();
						if(fichaAct.esRotable()) {
							int rotaciones = 0;
							for (Iterator<EstructuraFicha> iterator = fichasRotadas.iterator(); iterator.hasNext();) {
								EstructuraFicha ef = (EstructuraFicha) iterator.next();
								System.out.println(ef.toString());
								if(tablero.puedoColocarFicha(ef, i, j)) {
									tablero.colocarFicha(ef, i, j);
									haySolucion = Main.rompeCabezasTetris(tablero, fichaActual + 1, cantRotacionActual + rotaciones, minimoRotaciones);
									if(!haySolucion) {
										tablero.removerFicha(ef, i, j);
									}
									else {
										System.out.println("Se pudo colocar la ficha :");
										System.out.println(ef.toString());
										System.out.println("En Fila " + i + " Columna " + j);
									}
									rotaciones++;
								} 
							}
						}
						else {
							EstructuraFicha ef = fichasRotadas.get(0);
							System.out.println(ef.toString());
							if(tablero.puedoColocarFicha(ef, i, j)) {
								tablero.colocarFicha(ef, i, j);
								haySolucion = Main.rompeCabezasTetris(tablero, fichaActual + 1, cantRotacionActual, minimoRotaciones);
								if(!haySolucion) {
									tablero.removerFicha(ef, i, j);
								}
								else {
									System.out.println("Se pudo colocar la ficha :");
									System.out.println(ef.toString());
									System.out.println("En Fila " + i + " Columna " + j);
								}
							}
						}
					}
				}
			}
		}
		System.out.println("cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
		System.out.println(tablero.toString());
		return haySolucion;
	}

}
