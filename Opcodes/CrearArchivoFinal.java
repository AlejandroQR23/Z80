package Opcodes;

import java.io.*;
import java.util.*;

import Interfaz.*;

/**
 * Crea el archivo .lst
 *
 * @version 1.0 20/01/2021
 * @author Lizeth Durán González
 */

public class CrearArchivoFinal {
    //mnemonicos
    static LinkedList<String> listAsm = new LinkedList<>();
    //direcccion de memoria. dir - hexa
    static LinkedList<String>listDirHexa;
    //hexadecimal
    static LinkedList<String> listHexa = new LinkedList<>();


    static void crearArchivo(String nameArchivoIn){
        //direccion donde se guardan los archivos
        String address ="../tests/";   
        String name = Interfaz.nombreArchivoIn + ".lst";
        File file = new File(address + name);
        
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            else{
                file.delete();
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Name out: " + name);
        ConvertirAHexadecimal.nombreArchivoOut = name;
    }


    static public void leerArchivo(LinkedList<String> fileList, String extension){
        try{
            String adress = "../tests/";
            String name = adress + Interfaz.nombreArchivoIn + extension;

            FileReader fr = new FileReader( name );
            BufferedReader br = new BufferedReader( fr );
            
            String line;
            while( (line = br.readLine()) != null ){
                //&& (line = br.readLine()) != "" 
                fileList.add( line );
            }

            br.close();
            fr.close();

        } catch( IOException e ){
            System.out.print("\n Error al abrir el archivo");
        }

    }

    static public void init(){
        //Crear Archivo .lst
        crearArchivo(Interfaz.nombreArchivoIn);

        //Leer info de archivo y guardarlas en listas
        leerArchivo(listAsm, ".asm");
        leerArchivo(listHexa, ".lss");

        //calcular direccion de cada instruccion 
        getDirecciones();  

        //Escribir en un archivo .lst 
        escribirArchivo();
        
    }

    /**
     * Del codigo en hexadecimal, se calcula cuantos espacios
     * ocupará cada direccion
     */
    static public void getDirecciones(){
       

        listDirHexa = new LinkedList<>();
        
        listDirHexa.add("0000");
        int contador = 0;
        for (String elemento : listHexa){

            contador = contadorDirecciones(contador, elemento);
            String direccion = completarNotacion(contador);
            listDirHexa.add(direccion);
            
            
        }

        
    }

    /**
     * De acuerdo al numero de bytes de la direccion en 
     * hexadecimal, genera el espacio en memoria donde se tendria que 
     * poner la siguiente instruccion
     * @param dir Ultima direccion
     * @param elemento
     * @return
     */
    public static int contadorDirecciones(int dir, String elemento){
        int contador = 0;
        if (elemento.length() == 2) {
            contador = 1;
        }
        else if(elemento.length() == 4){
            contador = 2;
        }
        else if(elemento.length() == 6){
            contador = 3;
        }

        dir = dir + contador;

        
        return dir;
    }

    /**
     * Completa las direcciones a 4 bytes
     * @param contador
     * @return
     */
    static public String completarNotacion(int contador){
//pasar a hexadecimal
        String auxHexa =  Integer.toString(contador,16).toUpperCase();
        int diferencia = 0;
        if (auxHexa.length() != 4 ) {
            diferencia = 4 - auxHexa.length();
        }

        if (diferencia == 1) {
            auxHexa = "0" + auxHexa;
        }else if(diferencia == 2){
            auxHexa = "00" + auxHexa;
        }else if (diferencia == 3){
            auxHexa = "000" + auxHexa;
        }

        return auxHexa;

    }

    /**
     * Obtiene los datos de cada lista y los escribe en un archivo 
     * de tipo lst
     */
    static void escribirArchivo(){
        try {
            File file = new File("../tests/" + Interfaz.nombreArchivoIn + ".lst");

            FileWriter fw = new FileWriter (file);
            BufferedWriter bw = new BufferedWriter (fw);
            int i = 0;
            for (String string : listAsm) {
                bw.write(listDirHexa.get(i)  + "       " + listHexa.get(i) + "         " + string + "\n");
                i++;
            }


            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        
        init();
        //System.out.println(listAsm);
        //System.out.println(listHexa);
    }
}
