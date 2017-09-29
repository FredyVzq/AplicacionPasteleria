package Controlador;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControladorErrores {
	private DateFormat formatodefecha;
	private Date fecha;
	
	public ControladorErrores(){ 
		formatodefecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); //A esto se le llama upcasting cuando queremos que una clase hija se comporte como una clase padre. Dateformat es clase padre. simpledateformat es clase hija. lo queremos para acceder a los metodos de dateformat
		fecha = new Date();//Se crea instancia de tipo/clase date
	}
	
	public void imprimirBitacora(String mensaje, String clase){
		FileWriter fw = null; //Clase donde se indica en que ruta se crea el archivo para generarlo.
		BufferedWriter bw = null;  //Cuando afectas archivo se abre un buffer. es para indicar si el archivo es de lectura, escritura, etc. es para manipular el archivo
		//Con la instancia Buffered se escribe sobre el archivo o edita o lee.
		
		try{
			File archivo = new File("C:\\log.txt"); //se crea objeto de tipo file
			fw = new FileWriter(archivo, true);	//permiso para escribir en el archivo
			bw = new BufferedWriter(fw); //para comenzar a manipular el archivo
			bw.write("BITACORA");
			bw.newLine();
			bw.write("Fecha: " + formatodefecha.format(fecha));
			bw.newLine();
			bw.write("Error en: " + clase);
			bw.newLine();
			bw.write("Descripcion: " + mensaje);
			bw.newLine();
			bw.write("-------------------------------------------------------------");
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
