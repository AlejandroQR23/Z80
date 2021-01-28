import java.util.*;

public class pruebas
{

    static Hashtable< String, String > instructions = new Hashtable<>();
    static Hashtable< String, String> registers = new Hashtable<>();
    static Hashtable< String, String> conditions = new Hashtable<>();
    static String instruction = "CALL NZ, 000";
    static String register;
    static String condition;

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

    public static String getCondition(){
        String result = instruction.replaceAll("( NZ)|( Z)|( NC)|( C)", " cc");

        if ( instruction.contains("NZ") ) {
            condition = "NZ";
        }

        return result;
    }

    public static String changeRegister( String opcode ){
        String opc = opcode.replace("r,", registers.get(register) );
        return opc.replace("r'", registers.get(register) );
    }

    public static String changeCondition( String opcode ){
        String opc = opcode.replace("cc", conditions.get( condition ));
        return opc;
    }



    public static void main(String[] args) {

        pruebas p = new pruebas();
        instructions.put( "CALL cc, 000", "01 cc 100" );
        conditions.put("NZ", "000");

        String gInst = getCondition();
        System.out.println( "\n Intruccion: " + gInst );

        String opcode = instructions.get( gInst );
        System.out.println( "\n Opcode general: " + opcode );

        String opcodeF = changeCondition( opcode );
        System.out.println( "\n Opcode final: " + opcodeF );

        StringBuilder sb = new StringBuilder("aaaa");
        sb = new StringBuilder("a");
        System.out.println( sb );

    }

    //TODO: Aprender a hacer pruebas unitarias

}
