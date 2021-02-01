package Opcodes;

import java.util.*;
import java.io.*;

/**
 * Una clase de apoyo con metodos que
 * funcionan como herramientas para decodificar
 * las instrucciones del Z80
 *
 * @version 0.8 25/01/2021
 * @author Alejandro Quijano
 */
public class DecodeTools
{

    private Hashtable< String, String> registers;                               // tabla con los opcodes de los registros
    private Hashtable< String, String> pairsR;                                  // tabla con los opcodes de los registros pares
    private Hashtable< String, String> bits;                                    // tabla con los opcodes de los bits
    private Hashtable< String, String> conditions;                              // tabla con los opcodes de las condiciones

    private String register;                                                    // registro de la instruccion, si lo tiene
    private String altR;                                                        // segundo registro, si lo tiene
    private String pairR;                                                       // registro par, si lo tiene
    private String condition;                                                   // condiciones de la instruccion, si las tiene
    private String bit;                                                         // bit, si lo tiene

    public DecodeTools(){
        fillTables();
    }

    /**
     * Metodo que cambia los registros de la instruccion
     * por una r o r', asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getRegister( String instruction ){
        if ( instruction.contains("JP") | instruction.contains("JR") ) {
            return new StringBuilder(instruction);
        }

        String result = instruction.replaceFirst("( A)|( B)|( C)|( D)|( E)|( H)|( L)", " r");

        if ( result.contains( " A" ) ) {
            this.altR = "A";
        } else if ( result.contains( " B" ) ) {
            this.altR = "B";
        } else if ( result.contains( " C" ) ) {
            this.altR = "C";
        } else if ( result.contains( " D" ) ) {
            this.altR = "D";
        } else if ( result.contains( " E" ) ) {
            this.altR = "E";
        } else if ( result.contains( " H" ) ) {
            this.altR = "H";
        } else if ( result.contains( " L" ) ) {
            this.altR = "L";
        }

        result = result.replaceAll("( A)|( B)|( C)|( D)|( E)|( H)|( L)", " r'");

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

        System.out.print( "\n Registro: " + this.register );

        return new StringBuilder(result);
    }

    /**
     * Metodo que cambia los registros pares de la instruccion
     * por una dd, asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getPairR( String instruction ){
        String result = instruction.replaceAll("( BC)|( DE)|( HL)|( SP)|( AF)|( IX)|( IY)", " dd");

        if ( instruction.contains( "BC" ) ) {
            this.pairR = "BC";
        } else if ( instruction.contains( "DE" ) ) {
            this.pairR = "DE";
        } else if ( instruction.contains( "( HL)|( IX)|( IY)" ) ) {
            this.pairR = "HL";
        } else if ( instruction.contains( "( AF)|( SP)" ) ) {
            this.pairR = "AF";
        }

        return new StringBuilder(result);
    }

    /**
     * Metodo que cambia las condiciones de la instruccion
     * por una cc, asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getCondition( String instruction ){
        String result = instruction.replaceAll("( NZ)(, nn)", " cc, nn");

        if ( instruction.contains("NZ") ) {
            this.condition = "NZ";
        } else if ( instruction.contains("Z") ) {
            this.condition = "Z";
        } else if ( instruction.contains("NC") ) {
            this.condition = "NC";
        } else if ( instruction.contains("C") ) {
            this.condition = "C";
        }

        return new StringBuilder(result);
    }

    /**
     * Metodo que cambia los bits de la instruccion
     * por una b, asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getBit( String instruction ){

        if ( instruction.contains("IM ") ) {
            return new StringBuilder( instruction );
        }

        String result = instruction.replaceFirst("[0-7](,)", "b,");

        if ( instruction.contains("0") ) {
            this.bit = "0";
        } else if( instruction.contains("1") ){
            this.bit = "1";
        } else if ( instruction.contains("2") ) {
            this.bit = "2";
        } else if ( instruction.contains("3") ) {
            this.bit = "3";
        } else if ( instruction.contains("4") ) {
            this.bit = "4";
        } else if ( instruction.contains("5") ) {
            this.bit = "5";
        } else if ( instruction.contains("6") ) {
            this.bit = "6";
        } else if ( instruction.contains("7") ) {
            this.bit = "7";
        }

        return new StringBuilder(result);
    }

    /*
     * Metodo que cambia las etiquetas
     * por una e, asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getEti( String instruction ){
        String result = instruction.replaceAll("(eti)([1-9]*[0-9])*", "e");
        return new StringBuilder(result);
    }

    /*
     * Metodo que cambia los numeros
     * por una n o nn asi la instruccion estara en su forma general
     * @param instruction La instruccion a generalizar
     */
    private StringBuilder getNumber( String instruction ){
        String result = instruction.replaceAll("(2[05][0-5]|2[0-4][0-9]|1?[0-9][0-9])", "n");
        String result2 = instruction.replaceAll("([0-6]?[0-9]{0,2}[0-9][0-9])", "nn");
        return new StringBuilder(result2);
    }

    /**
     * Metodo que cambia los reigstros
     * del opcode general por los opcodes correspondientes
     * al registro de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     * @return El opcode con el registro cambiado, si lo hay
     */
    private StringBuilder changeRegister( String opcode ){
        if ( this.register != null ) {
            String opc = opcode.replaceFirst("(r,)|(r)", registers.get(register) );
            if ( this.altR != null ) {
                String opc2 = opc.replaceAll("(r')", registers.get(altR) );
                return new StringBuilder( opc2 );
            } else {
                return new StringBuilder( opc );
            }
        } else if ( this.pairR != null ){
            return new StringBuilder( opcode.replaceAll("dd", pairsR.get(pairR) ) );
        } else {
            return new StringBuilder( opcode );
        }

    }

    /**
     * Metodo que cambia las condiciones
     * del opcode general por los opcodes correspondientes
     * a la condicion de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     * @return El opcode con la condicion cambiada, si la hay
     */
    private StringBuilder changeCondition( String opcode ){
        if ( this.condition != null ) {
            String opc = opcode.replace("cc", conditions.get( condition ));
            return new StringBuilder( opc );
        } else {
            return new StringBuilder( opcode );
        }

    }

    /**
     * Metodo que cambia los bits
     * del opcode general por los opcodes correspondientes
     * al bit de la instruccion dada
     * @param opcode El opcode con el registro cambiado por su opcode
     * @return El opcode con el bit cambiado, si lo hay
     */
    private StringBuilder changeBit( String opcode ){
        if ( this.bit != null ) {
            String opc = opcode.replace("b", bits.get( bit ));
            return new StringBuilder( opc );
        } else {
            return new StringBuilder( opcode );
        }
    }

    /**
    * Metodo que convierte una isntruccion dada
    * en su forma general para poder buscarla en tablas
    * @param inst La instruccion a generalizar
    * @return La instruccion generalizada
    */
    public String getInst( String inst ){

        StringBuilder gInst = new StringBuilder( inst );                        // cadena mutable con la instruccion

        gInst = getEti( gInst.toString() );
        gInst = getBit( gInst.toString() );
        gInst = getPairR( gInst.toString() );
        gInst = getRegister( gInst.toString() );                                // Se verifican los registros individuales
        gInst = getNumber( gInst.toString() );
        gInst = getCondition( gInst.toString() );

        return gInst.toString();

    }

    /**
    * Metodo que convierte un opcode general
    * en su forma final en binario
    * @param inst El opcode general
    * @return El opcode final en binario
    */
    public String getOpc( String gOpc ){

        StringBuilder opc = new StringBuilder( gOpc );

        opc = changeBit( opc.toString() );
        opc = changeRegister( opc.toString() );
        opc = changeCondition( opc.toString() );

        return opc.toString();
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
