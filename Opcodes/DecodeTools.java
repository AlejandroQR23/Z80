package Opcodes;

import java.util.*;
import java.io.*;

/**
 * Una clase de apoyo con metodos que
 * funcionan como herramientas para decodificar
 * las instrucciones del Z80
 *
 * @version 0.11 16/01/2021
 * @author Alejandro Quijano
 */
public class DecodeTools
{

    private Hashtable< String, String> registers;                               // tabla con los opcodes de los registros
    private Hashtable< String, String> pairsR;                                  // tabla con los opcodes de los registros pares
    private Hashtable< String, String> bits;                                    // tabla con los opcodes de los bits
    private Hashtable< String, String> conditions;                              // tabla con los opcodes de las condiciones

    private String register;                                                    // registro de la instruccion, si lo tiene

    public DecodeTools(){
        fillTables();
    }

    /**
     * Metodo estatico que cambia los registros de la instruccion
     * por una r o r', asi podran la instruccion estara en su forma general
     */
    public static String getRegister( String instruction ){
        String result = instruction.replaceAll("( A)|( B)|( C)|( D)|( E)|( H)|( L)", " r");
        result = result.replaceAll("( A')|( B')|( C')|( D')|( E')|( H')|( L')", " r'");

        if ( instruction.contains( " A" ) ) {
            register = "A";
        } else if ( instruction.contains( " B" ) ) {
            register = "B";
        }

        return result;
    }

    /**
     * Metodo estatico que cambia los reigstros
     * del opcode general por los opcodes correspondientes
     * al registro de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     */
    public static String changeRegister( String opcode ){
        String opc = opcode.replace("r,", registers.get(register) );
        return opc.replace("r'", registers.get(register) );
    }

    private void fillTables(){

        // Creacion de las tablas
        this.registers = new Hashtable<>();
        this.pairsR = new Hashtable<>();
        this.bits = new Hashtable<>();
        this.conditions = new Hashtable<>();

        // Llenado de la tabla de registros
        this.registers.put("B", "000");
        this.registers.put("C", "001");
        this.registers.put("D", "010");
        this.registers.put("E", "011");
        this.registers.put("H", "100");
        this.registers.put("L", "101");
        this.registers.put("A", "111");

        // Llenado de la tabla de registros pares
        this.pairsR.put("BC", "00");
        this.pairsR.put("DE", "01");
        this.pairsR.put("HL", "10");                                            // HL, IX, IY
        this.pairsR.put("SP", "11");                                            // AF, SP

        // Llenado de la tabla de bits
        this.bits.put("0", "000");
        this.bits.put("1", "001");
        this.bits.put("2", "010");
        this.bits.put("3", "011");
        this.bits.put("4", "100");
        this.bits.put("5", "101");
        this.bits.put("6", "110");
        this.bits.put("7", "111");

        // Llenado de la tabla de condiciones
        this.conditions.put("NZ", "000");
        this.conditions.put("Z",  "001");
        this.conditions.put("NC", "010");
        this.conditions.put("C",  "011");
        this.conditions.put("PO", "100");
        this.conditions.put("PE", "101");
        this.conditions.put("P",  "110");
        this.conditions.put("M",  "111");

    }

}

//TODO: Mover todas la tablas a un solo lado - estoy pensando en opcodes
//TODO: Hacer un metodo que cambie los parametros generales (etiqutas, numeros y eso) para obtener el opcode
