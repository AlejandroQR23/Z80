package Files;

import java.util.*;                                                             // Colecciones
import java.io.*;                                                               // Archivos

/**
 * Una clase con los metodos convenientes para leer
 * y escribir sobre archivos de texto
 * con las instrucciones u opcodes de ensamblador
 *
 * @version 0.1 15/01/2021
 * @author Alejandro Quijano
 */
public class FileOptions
{

    String address = "tests/";                                                  // direccion donde se guardan los archivos
    String fileInst;                                                            // nombre del archivo de instrucciones
    String fileOpc;                                                             // nombre del archivo de opcodes

    public FileOptions( String fileName ){
        this.fileInst = this.address + fileName + ".txt";
        this.fileOpc = this.address + "opcode_" + fileName + ".txt";
    }

    /**
     * Lee un archivo de texto con los mnemonicos
     * y almacena cada linea en una lista enlazada
     * @return Una lista enlazada con los mnemonicos
     */
    public LinkedList<String> readFile(){

        try{

            FileReader fr = new FileReader( this.fileInst );
            BufferedReader br = new BufferedReader( fr );
            LinkedList<String> fileList = new LinkedList<>();

            String line;
            while( (line = br.readLine()) != null ){
                fileList.add( line );
            }

            br.close();
            fr.close();

            return fileList;

        } catch( IOException e ){
            System.out.print("\n Error al abrir el archivo");
            return null;
        }

    }

    public void writeFile( LinkedList<String> opcodeList ){
        try{

            File f = new File( this.fileOpc );
            PrintWriter pw = new PrintWriter( new FileWriter( f, true ) );

            for ( String opcode : opcodeList ) {
                pw.println( opcode );
            }

            pw.close();

        } catch( IOException e ){
            System.out.println("Archivo no existente");
        }

    }

    /**
     * Crea un archivo de texto cuyo nombre sera el mismo
     * de otro archivo antes leido con las instrucciones
     * mas el prefijo "opcode_" ya que aqui se almacenaran los
     * opcodes de las instrucciones leidas
     */
    public void createFile(){

        try {

            File file = new File( this.fileOpc );
            if( !file.exists() ){
                file.createNewFile();
            } else {
                System.out.println( "\n El archivo ya existe " );
            }

        } catch ( IOException e ){
            System.out.print("\n Error al crear el archivo");
        }

    }

}
