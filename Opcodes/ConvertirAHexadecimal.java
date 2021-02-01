package Opcodes;

import java.util.*;

/**
 * Clase que da formato a los códigos ya en binario 
 * para ser convertidos en hexadecimal 
 * y obtener el archivo final
 *
 * @version 0.1 16/01/2021
 * @author Lizeth Durán González
 */
public class ConvertirAHexadecimal {


    /**
     * Convierte una cadena en binario a hexadecimal
     * @param codigo en binario
     * @return cadena en hexadecimal
     */
    static String aHexadecimal(String codigo){
        String auxHexa = "";
  
        auxHexa = Integer.toString(Integer.parseInt(codigo,2),16).toUpperCase();
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
    static void formatear(LinkedList<String> dataIn){


        for (String string : dataIn) {
            
            String codigo = string;
            String valor = "";
            String[] cadenaDividida;
            String dataFinal= "";
            String yaEnHexa = "";

            if (string.contains("d")) {
                cadenaDividida = string.split("d");
                codigo = cadenaDividida[0];
                valor = cadenaDividida[1];
            }
            else{
                valor = "////";
            }
            
            if (!string.contains("\n")) {
               
                yaEnHexa = aHexadecimal(codigo);
            }
            else if (string.contains("\n")) {

                cadenaDividida = codigo.split("\n");

                for (int i = 0; i < cadenaDividida.length; i++) {
                    
                    yaEnHexa = yaEnHexa + aHexadecimal(cadenaDividida[i]);
                }
            } 
            
            if (!valor.equals("////")) {
                dataFinal = yaEnHexa + valor;
            }
            else{
                dataFinal = yaEnHexa;
            }

            System.out.println(dataFinal);;
        }
    }

    public static void main(String[] args) {
        
        LinkedList<String> prueba1 = new LinkedList<>();

        prueba1.add("01000000");
        prueba1.add("11001011\n11001011d05");
        prueba1.add("01110110");
        prueba1.add("1001111\n1001111");
        prueba1.add("00100111");
        prueba1.add("01110110");

        formatear(prueba1);

    }
    
}