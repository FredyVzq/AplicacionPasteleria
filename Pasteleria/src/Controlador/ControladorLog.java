package Controlador;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;

public class ControladorLog {
	String frase;
	FileWriter archivo=null;
	PrintWriter pw=null;

	public ControladorLog(){
	}
	public void acceso(String nombre){
		try {
			archivo =new FileWriter("C:/PerfLogs/PJxRAM01.log",true);
			pw=new PrintWriter(archivo);
			pw.println("!!ACCESO AL SISTEMA¡¡");
			pw.println("Nuevo acceso al sistema por el usuario:"+nombre);
			pw.println("Dia y fecha:"+(new Date()).toString());
			pw.println("---------------------------------------------------------------------");
			pw.println();


		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(archivo!=null){
					archivo.close();
				}
			}catch(Exception ex2){
				System.out.println("Error muy hardcore");
			}
		}
	}
	public void nuevo(String nombre,String tipoRegistro,String registro){
		try {
			archivo =new FileWriter("C:/PerfLogs/PJxRAM01.log",true);
			pw=new PrintWriter(archivo);
			pw.println("---------------------------------------------------------------------");
			pw.println("Usuario:"+nombre);
			pw.println("Nuevo registro de "+tipoRegistro +":"+registro);
			pw.println("Dia y fecha:"+(new Date()).toString());
			pw.println("---------------------------------------------------------------------");
			pw.println();


		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(archivo!=null){
					archivo.close();
				}
			}catch(Exception ex2){
				System.out.println("Error muy hardcore");
			}
		}
	}
	public void eliminado(String nombre,String tipoRegistro,String registro){
		try {
			archivo =new FileWriter("C:/PerfLogs/PJxRAM01.log",true);
			pw=new PrintWriter(archivo);
			pw.println("---------------------------------------------------------------------");
			pw.println("Usuario:"+nombre);
			pw.println("Eliminado registro de "+tipoRegistro+":"+registro);
			pw.println("Dia y fecha:"+(new Date()).toString());
			pw.println("---------------------------------------------------------------------");
			pw.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(archivo!=null){
					archivo.close();
				}
			}catch(Exception ex2){
				System.out.println("Error muy hardcore");
			}
		}
	}
	public void editado(String nombre,String tipoRegistro,String registro){
		try {
			archivo =new FileWriter("C:/PerfLogs/PJxRAM01.log",true);
			pw=new PrintWriter(archivo);
			pw.println("---------------------------------------------------------------------");
			pw.println("Usuario:"+nombre);
			pw.println("Edito el registro de "+tipoRegistro+":"+registro);
			pw.println("Dia y fecha:"+(new Date()).toString());
			pw.println("---------------------------------------------------------------------");
			pw.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(archivo!=null){
					archivo.close();
				}
			}catch(Exception ex2){
				System.out.println("Error muy hardcore");
			}
		}
	}
}
