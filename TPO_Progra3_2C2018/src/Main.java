

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
import java.util.List;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) throws IOException {
		
		String configuracion = obtenerConfiguracion();
		Tablero tablero = parsearTablero(configuracion);
		List<Ficha> fichas = parsearFichas(configuracion);


	}

	private static Tablero parsearTablero(String configuracion) {
		int cantidadFilas = Character.getNumericValue(configuracion.charAt(0));
		int cantidadColumnas = Character.getNumericValue(configuracion.charAt(2));
		int cantidadFichas = Character.getNumericValue(configuracion.charAt(4));
		return null;
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


}
