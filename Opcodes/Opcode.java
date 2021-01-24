package Opcodes;

import java.util.*;                                                             // colecciones
import java.io.*;                                                               // archivos

/**
 * Una clase con los metodos convenientes trabajar
 * con los opcodes y su equivalente en mnemonicos
 *
 * @version 0.15 15/01/2021
 * @author Alejandro Quijano
 */
public class Opcode
{

    private Hashtable< String, String > instructions;                           // tabla de mnemonicos

    public Opcode(){
        File opcodesTable = new File( "Opcodes/Opcodes.dat" );
        if ( !opcodesTable.exists() ) {
            createTable();                                                      // llena la tabla de instrucciones
        } else {
            extractTable();
        }
    }

    /**
     * Un metodo que lee las instrucciones de
     * una lista y usando la tabla de opcodes las
     * transforma en opcodes que almacena en otra lista
     * @param list La lista con instrucciones
     * @return La lista con opcodes
     */
    public LinkedList<String> decodeInst( LinkedList<String> list ){

        LinkedList< String > opcodes = new LinkedList<>();
        for ( String inst : list ) {
            String opcode = this.instructions.get( inst );
            opcodes.add( opcode );
        }

        return opcodes;
    }

    /**
     * Un metodo que lee un archivo de objetos
     * con la tabla de opcodes e instrucciones
     * y la almacena en la tabla del objeto Opcode
     */
    @SuppressWarnings("unchecked") // perdoname diosito
    private void extractTable(){
        try{
            ObjectInputStream table = new ObjectInputStream(new FileInputStream( "Opcodes/Opcodes.dat" ));
            this.instructions = (Hashtable<String, String>) table.readObject();
            table.close();
        } catch( IOException e ){
            System.out.print("\n Error al abrir el archivo");
        } catch( ClassNotFoundException ce ){
            System.out.print("\n Error al leer la clase");
        }
    }

    /**
     * Un metodo que crea un archivo de objetos que contiene a la tabla
     * de instrucciones y opcodes si no se ha creado antes
     */
    private void createTable(){

        this.fillTable();                                                       // Se crea la tabla solo una vez
        try{
            ObjectOutputStream opcodeFile = new ObjectOutputStream(new FileOutputStream( "Opcodes/Opcodes.dat" ));
            opcodeFile.writeObject( this.instructions );
            opcodeFile.close();
        } catch( IOException e ){
            System.out.print("\n Error al abrir el archivo");
        }

    }

    /**
     * Un metodo que crea una hash table donde se ingresan
     * los mnemonicos y opcodes en binario para luego hacer la traduccion.
     * Solo se ejecuta una vez, si se desea añadir mas instrucciones, primero
     * se debe borrar el archivo Opcodes.dat
     */
    private void fillTable(){
        this.instructions = new Hashtable<>();

        // Grupo de aritmetica y control -- 76 543 210
        this.instructions.put( "NOP",  "00 000 000" );
        this.instructions.put( "DAA",  "00 100 111" );
        this.instructions.put( "CPL",  "00 101 111" );
        this.instructions.put( "NEG",  "11 101 101" );
        this.instructions.put( "CCF",  "00 111 111" );
        this.instructions.put( "SCF",  "00 110 111" );
        this.instructions.put( "HALT", "01 110 110" );
        this.instructions.put( "DI",   "11 110 011" );
        this.instructions.put( "EI",   "01 111 110" );

        // Grupo de transferencia e intercambio
        this.instructions.put( "EX DE, HL",   "11 101 011" );
        this.instructions.put( "EX AF, AF'",  "00 001 000" );
        this.instructions.put( "EXX",         "11 011 001" );
        this.instructions.put( "EX (SP), HL", "11 100 011" );
        this.instructions.put( "EX (SP), IX", "11 011 101 \n11 100 011" );
        this.instructions.put( "EX (SP), IY", "11 111 101 \n11 100 011" );
        this.instructions.put( "LDI",         "11 101 101" );

        // Grupo de carga 8-bit
        this.instructions.put( "LD", "01 r, r'" );

        // Grupo de rotacion y cambio
        this.instructions.put( "RLCA", "00 000 111" );
        this.instructions.put( "RLA",  "00 010 111" );
        this.instructions.put( "RRCA", "00 001 111" );
        this.instructions.put( "RRA ",  "00 011 111" );

        // Grupo de entrada y salida

    }

    //TODO: ingresar todos los opcodes
    //TODO: Luego de las instrucciones EX SP todas llevan dos opcodes

}
