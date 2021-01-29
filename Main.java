
import Files.FileOptions;
import Opcodes.Opcode;

import java.util.*; // Colecciones


public class Main
{

    public static void main(String[] args) {

        FileOptions f = new FileOptions( "test1" );
        LinkedList<String> list = f.readFile();                                 // se leen las instrucciones del archivo de texto

        f.createFile();                                                         // crea el archivo donde iran los opcodes

        Opcode opc = new Opcode();
        LinkedList<String> opcodes = opc.decodeInst( list );

        f.writeFile( opcodes );                                                 // llena el archivo nuevo con opcodes

    }

}
