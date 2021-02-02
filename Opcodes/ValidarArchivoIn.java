package Opcodes;

import java.io.*;

import javax.swing.*;

/**
 * Clase que valida que un archivo con extensión .asm tenga código para en
 * ensamblador.
 *
 * @version 0.2 16/01/2021
 * @author Lizeth Durán González
 */
public class ValidarArchivoIn {

    /**
     * Los archivos .asm con código ensamblador deben 
     * tener el header: //cpu z80 ensamblar//
     * 
     * @param cadena
     */
    public static String leerHeader(String cadena) {

        

        System.out.println("Hola");

        return cadena;
  
    }

    public static void obtenerCodigo(String cadena, String nombreArchivo){
        String dataIn = "";
        String[] cadenaDividida; 
        if ( cadena.contains("cpu z80 ensamblar &&")) {
            //System.out.println("data valida");

            cadenaDividida = cadena.split("&&");
            dataIn = cadenaDividida[1];

            System.out.println(dataIn);
            
            try {
                File fichero = new File ("tests/" + nombreArchivo);
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
                bw.write(dataIn);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            //Codigo no valido
            JOptionPane.showMessageDialog(null, "Archivo no valido" );
            
        }

    }

    
}