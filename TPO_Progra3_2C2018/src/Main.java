

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
		tablero.setRotacionesActuales(0);
		tablero.setMinRotaciones(-1);
		resultado = rompeCabezasTetris(tablero,0);
		System.out.println("El tablero se completó con "+tablero.getMinRotaciones()+" rotaciones");
		


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
		String strpath = "src/Ej2.txt";
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

	private static boolean rompeCabezasTetris(Tablero tablero,int fichaActual) {
		
		boolean haySolucion = false;
		int cantRotacionActual = tablero.getRotacionesActuales();
		int minimoRotaciones = tablero.getMinRotaciones();
		System.out.println("Ejecucion recursiva con: fichaActual =" + fichaActual + " cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
		System.out.println(tablero.toString());
		if(tablero.estaCompleto()) {
			System.out.println("Se completó tablero: fichaActual =" + fichaActual + " cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
			if(minimoRotaciones == -1 || cantRotacionActual < minimoRotaciones) {
				tablero.setMinRotaciones(cantRotacionActual);
				minimoRotaciones = cantRotacionActual;
				System.out.println("cantidadRotacionActual =" + cantRotacionActual + " minimoRotaciones =" + minimoRotaciones);
				haySolucion = true;
			}
		return haySolucion;
		}
		else {
			if((minimoRotaciones == -1 || cantRotacionActual < minimoRotaciones) && fichaActual < tablero.getCantidadFichas()) {
				System.out.println(tablero.toString());
				for (int i = 0; i < tablero.getCantidadFilas(); i++) {
					for (int j = 0; j < tablero.getCantidadColumnas(); j++) {
						System.out.println("Probando Ficha "+fichaActual+" en Tablero: Fila ="+i+ " Columna = "+j);
						System.out.println(tablero.toString());
						Ficha fichaAct = tablero.getFicha(fichaActual);
						List<EstructuraFicha> fichasRotadas = fichaAct.getRotaciones();
						if(fichaAct.esRotable()) {
							int rotaciones = -1;
							for (Iterator<EstructuraFicha> iterator = fichasRotadas.iterator(); iterator.hasNext();) {
								EstructuraFicha ef = (EstructuraFicha) iterator.next();
								rotaciones++;
								System.out.println(ef.toString());
								if(tablero.puedoColocarFicha(ef, i, j)) {
									tablero.colocarFicha(ef, i, j);
									System.out.println(tablero.toString());
									tablero.setRotacionesActuales(cantRotacionActual + rotaciones);
									haySolucion = Main.rompeCabezasTetris(tablero, fichaActual + 1);
									tablero.removerFicha(ef, i, j);
									System.out.println(tablero.toString());
								} 
							}
						}
						else {
							EstructuraFicha ef = fichasRotadas.get(0);
							System.out.println(ef.toString());
							if(tablero.puedoColocarFicha(ef, i, j)) {
								tablero.colocarFicha(ef, i, j);
								System.out.println(tablero.toString());
								tablero.setRotacionesActuales(cantRotacionActual);
								tablero.setMinRotaciones(minimoRotaciones);
								haySolucion = Main.rompeCabezasTetris(tablero, fichaActual + 1);
								tablero.removerFicha(ef, i, j);
								System.out.println(tablero.toString());
							}
						}
					}
				}
			}
		}
		System.out.println("cantidadRotacionActual =" + tablero.getRotacionesActuales() + " minimoRotaciones =" + tablero.getMinRotaciones());
		System.out.println(tablero.toString());
		return haySolucion;
	}

}
