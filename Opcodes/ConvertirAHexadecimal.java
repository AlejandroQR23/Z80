package Opcodes;

import java.io.*;
import java.util.*;
import Interfaz.*;
/**
 * Clase que da formato a los códigos ya en binario 
 * para ser convertidos en hexadecimal 
 * y obtener el archivo final
 *
 * @version 0.1 16/01/2021
 * @author Lizeth Durán González
 */
public class ConvertirAHexadecimal {

    public static String nombreArchivoOut = "";

    /**
     * Convierte una cadena en binario a hexadecimal
     * @param codigo en binario
     * @return cadena en hexadecimal
     */
    static String BinAHexadecimal(String codigo){
        String auxHexa = "";
  
        auxHexa = Integer.toString(Integer.parseInt(codigo,2),16).toUpperCase();

        if (auxHexa.length() == 1) {
            auxHexa = "0" + auxHexa; 
        }
        return auxHexa;
    }

    /**
     * Convierte una cadena en decimal a hexadecimal
     * @param codigo en binario
     * @return cadena en hexadecimal
     */
    static String decAHexadecimal(String codigo){
        int aux = Integer.parseInt(codigo);

      
        String auxHexa = "";
        
        
        auxHexa = auxHexa + Integer.toString(Integer.parseInt(codigo,10),16).toUpperCase();


        /*if (aux >= 256 && aux <= 271) {
            auxHexa = "0" + auxHexa;
        }*/

        if (auxHexa.length() == 1 || auxHexa.length() == 3) {
            auxHexa = "0" + auxHexa;
        }
        
        System.out.println("Aqui ahora es: " + auxHexa); 
        
        if (aux >= 256 && aux <= 65535) {
            String dividir1 = auxHexa.substring(0,2);
            String dividir2 = auxHexa.substring(2,4);

            auxHexa = dividir2 + dividir1;
        }
    
        
        return auxHexa;
    }

    /**
     * Caracteres como d, n, nn indican que además
     * del código de operación hay valores adicionales.
     * 
     * Estos son identificados y tratados para obtener el
     * código final en hexadecimal.
     * @param dataIn Códigos en binario
     */
    public static void formatear(LinkedList<String> dataIn){
        //Crea archivo de salida .lst
        File file = crearArchivo(Interfaz.nombreArchivoIn);

        //System.out.println( dataIn);
        for (String string : dataIn) {
            
            String codigo = string;
            String valor = "";
            String[] cadenaDividida = {};
            String dataFinal= "";
            String yaEnHexa = "";

            if (string.contains("\nw")) {
                cadenaDividida = string.split("\nw");   
                codigo = cadenaDividida[0];
                valor = cadenaDividida[1];
                System.out.println("Que valor es: " + valor);
                valor = decAHexadecimal(valor);
            }
            else if(string.contains("w")){
                cadenaDividida = string.split("w");
                codigo = cadenaDividida[0];
                valor = cadenaDividida[1];
                System.out.println("Que valor es: " + valor);
                valor = decAHexadecimal(valor);
            }
            else{
                valor = "////";
            }
            
           
            
            if (!string.contains("\n")) {
               
                yaEnHexa = BinAHexadecimal(codigo);
            }
            else if (string.contains("\n")) {

                cadenaDividida = codigo.split("\n");

                for (int i = 0; i < cadenaDividida.length; i++) {
                    
                    yaEnHexa = yaEnHexa + BinAHexadecimal(cadenaDividida[i]);
                }
            } 
            
            if (!valor.equals("////")) {
                dataFinal = yaEnHexa + valor;
            }
            else{
                dataFinal = yaEnHexa;
            }

            System.out.println(dataFinal);
            writeFile(dataFinal,file );
        }


    }

    /**
     * Crea archivo .ls
     * que contiene el código en hexadecimal
     */
    static File crearArchivo(String nameArchivoIn){
        //direccion donde se guardan los archivos
        String address ="../tests/";   
        String name = Interfaz.nombreArchivoIn + ".lss";
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

        return file;
    }


    static public void writeFile(String dataIn, File file){
        
        try {
  
            FileWriter fw = new FileWriter (file.getAbsoluteFile(), true);
            BufferedWriter bw = new BufferedWriter (fw);
            bw.write( dataIn + "\n");
            bw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        
        LinkedList<String> prueba1 = new LinkedList<>();

        prueba1.add("00111110d10");
        prueba1.add("01000111");
        prueba1.add("00111111d05");
        prueba1.add("10000000");

        formatear(prueba1);

        //decAHexadecimal("15");

    }
    
}