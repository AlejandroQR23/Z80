import java.util.*;

public class pruebas
{

    static Hashtable< String, String > instructions = new Hashtable<>();
    static Hashtable< String, String> registers = new Hashtable<>();
    static String instruction = "LD A, A'";
    static String register;

    public static String getRegister(){
        // se cambia los registros por registro por "r"
        String result = instruction.replaceAll("( A)|( B)|( C)|( D)|( E)|( H)|( L)", " r");
        result = result.replaceAll("( A')|( B')|( C')|( D')|( E')|( H')|( L')", " r'");

        if ( instruction.contains( " A" ) ) {
            register = "A";
        } else if ( instruction.contains( " B" ) ) {
            register = "B";
        }

        return result;
    }

    public static String changeRegister( String opcode ){
        String opc = opcode.replace("r,", registers.get(register) );
        return opc.replace("r'", registers.get(register) );
    }

    public static void main(String[] args) {

        pruebas p = new pruebas();
        instructions.put( "LD r, r'", "01 r, r'" );
        registers.put("A", "111");

        String generalInst = getRegister();
        System.out.println( "\n Intruccion: " + generalInst );

        String opcode = instructions.get( generalInst );
        System.out.println( "\n Opcode general: " + opcode );

        String opcodeF = changeRegister( opcode );
        System.out.println( "\n Opcode final: " + opcodeF );

    }

    //TODO: Aprender a hacer pruebas unitarias

}
