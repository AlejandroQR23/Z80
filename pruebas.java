import java.util.*;

public class pruebas
{

    public static void main(String[] args) {

        Hashtable< String, String > instructions = new Hashtable<>();
        LinkedList< String > opcodes = new LinkedList<>();

        instructions.put( "LD", "01 r, r'" );
        String opcode = instructions.get( "LD" ).replace( "r,", "001" );
        String opcode2 = opcode.replace( "r'", "000" );

        opcodes.add( opcode2 );

        System.out.println( "\n Intruccion: " + opcodes.peek() );
        
    }

    //TODO: Aprender a hacer pruebas unitarias

}
