import java.io.*;

public class TPO_main {

	public static void main(String fileName) throws Exception, IOException {
		// TODO Auto-generated method stub
		
		FileReader input = new FileReader(fileName);
		BufferedReader bufRead = new BufferedReader(input);
		String myLine = null;

		while ( (myLine = bufRead.readLine()) != null)
		{    
		    int cantidadFilas = Integer.parseInt(myLine.substring(0));
		    int cantidadColumnas = Integer.parseInt(myLine.substring(2));
		    int cantidadFichas = Integer.parseInt(myLine.substring(4));
		    Tablero tablero = new Tablero(cantidadFilas,cantidadColumnas,cantidadFichas);
		    String[] ficha = myLine.split(")");
		    
		}

	}

}
