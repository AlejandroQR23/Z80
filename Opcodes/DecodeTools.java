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
    private String pairR;
    private String condition;                                                   // condiciones de la instruccion, si las tiene

    public DecodeTools(){
        fillTables();
    }

    /**
     * Metodo que cambia los registros de la instruccion
     * por una r o r', asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private String getRegister( String instruction ){
        String result = instruction.replaceAll("( A)|( B)|( C)|( D)|( E)|( H)|( L)", " r");
        result = result.replaceAll("( A')|( B')|( C')|( D')|( E')|( H')|( L')", " r'");

        if ( instruction.contains( " A" ) ) {
            this.register = "A";
        } else if ( instruction.contains( " B" ) ) {
            this.register = "B";
        } else if ( instruction.contains( " C" ) ) {
            this.register = "C";
        } else if ( instruction.contains( " D" ) ) {
            this.register = "D";
        } else if ( instruction.contains( " E" ) ) {
            this.register = "E";
        } else if ( instruction.contains( " H" ) ) {
            this.register = "H";
        } else if ( instruction.contains( " L" ) ) {
            this.register = "L";
        }

        return result;
    }

    /**
     * Metodo que cambia los registros pares de la instruccion
     * por una dd, asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private String getPairR( String instruction ){
        String result = instruction.replaceAll("(BC)|(DE)|(HL)|(SP)|(AF)|(IX)|(IY)", "dd");

        if ( instruction.contains( "BC" ) ) {
            this.pairR = "BC";
        } else if ( instruction.contains( "DE" ) ) {
            this.pairR = "DE";
        } else if ( instruction.contains( "HL" ) ) {
            this.pairR = "HL";
        } else if ( instruction.contains( "AF" ) ) {
            this.pairR = "AF";
        }

        return result;
    }

    /**
     * Metodo que cambia las condiciones de la instruccion
     * por una cc, asi la instruccion estara en su forma general
     */
    private String getCondition( String instruction ){
        String result = instruction.replaceAll("( NZ)|( Z)|( NC)|( C)", " cc");

        if ( instruction.contains("NZ") ) {
            this.condition = "NZ";
        } else if ( instruction.contains("Z") ) {
            this.condition = "Z";
        } else if ( instruction.contains("NC") ) {
            this.condition = "NC";
        } else if ( instruction.contains("C") ) {
            this.condition = "C";
        }

        return result;
    }

    /**
     * Metodo que cambia los reigstros
     * del opcode general por los opcodes correspondientes
     * al registro de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     * @return El opcode con el registro cambiado, si lo hay
     */
    public String changeRegister( String opcode ){
        if ( this.register != null ) {
            String opc = opcode.replace("r,", registers.get(register) );
            return opc.replace("r'", registers.get(register) );
        } else if ( this.pairR != null ){
            return opcode.replace("dd,", pairsR.get(pairR) );
        } else {
            return opcode;
        }

    }

    /**
     * Metodo que cambia las condiciones
     * del opcode general por los opcodes correspondientes
     * a la condicion de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     * @return El opcode con la condicion cambiada, si la hay
     */
    public String changeCondition( String opcode ){
        if ( this.condition != null ) {
            String opc = opcode.replace("cc", conditions.get( condition ));
            return opc;
        } else {
            return opcode;
        }

    }

    public String getInst( String inst ){

        StringBuilder gInst = new StringBuilder( inst );                        // cadena mutable con la instruccion

        if ( this.register != null ) {
            gInst = getRegister( inst );                                        // Se verifican los registros individuales
        } else if ( this.pairR != null ) {
            gInst = getPairR( gInst.toString() );                               // Se verifican los registros pares
        }

        return gInst.toString();

    }

    public String getOpc( String inst ){
        return null;
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
//TODO: Los metodos para generalizar reigstros se pueden optimizar para que no hagan nada si no hay registros en la instruccion
//TODO: Cambiar los valores de retorno para que regreese un StringBuilder
