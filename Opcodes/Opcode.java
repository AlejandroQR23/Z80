package Opcodes;

import java.util.*;                                                             // colecciones
import java.io.*;                                                               // archivos
import Opcodes.DecodeTools;

/**
 * Una clase con los metodos convenientes trabajar
 * con los opcodes y su equivalente en mnemonicos
 *
 * @version 0.16 15/01/2021
 * @author Alejandro Quijano
 */
public class Opcode
{

    private Hashtable< String, String > instructions;                           // tabla de mnemonicos
    private DecodeTools decoder;                                                // herramienta de decodificacion

    public Opcode(){
        this.decoder = new DecodeTools();                                       // se crea la herramienta de decodificacion
        File opcodesTable = new File( "Opcodes/Objects/Opcodes.dat" );
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
            ObjectInputStream table = new ObjectInputStream(new FileInputStream( "Opcodes/Objects/Opcodes.dat" ));
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
            ObjectOutputStream opcodeFile = new ObjectOutputStream(new FileOutputStream( "Opcodes/Objects/Opcodes.dat" ));
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
        this.instructions.put( "IM 0", "11 101 101 \n01 000 110" );
        this.instructions.put( "IM 1", "11 101 101 \n01 010 110" );
        this.instructions.put( "IM 2", "11 101 101 \n01 011 110" );

        // Grupo de transferencia e intercambio
        this.instructions.put( "EX DE, HL",   "11 101 011" );
        this.instructions.put( "EX AF, AF'",  "00 001 000" );
        this.instructions.put( "EXX",         "11 011 001" );
        this.instructions.put( "EX (SP), HL", "11 100 011" );
        this.instructions.put( "EX (SP), IX", "11 011 101 \n11 100 011" );
        this.instructions.put( "EX (SP), IY", "11 111 101 \n11 100 011" );
        this.instructions.put( "LDI",         "11 101 101 \n10 100 000" );

        // Grupo de carga 8-bit
        this.instructions.put( "LD", "01 r, r'" );

        // Grupo de rotacion y cambio
        this.instructions.put( "RLCA",     "00 000 111" );
        this.instructions.put( "RLA",      "00 010 111" );
        this.instructions.put( "RRCA",     "00 001 111" );
        this.instructions.put( "RRA ",     "00 011 111" );
        this.instructions.put( "RLC (HL)", "11 001 011 \n00 000 110" );
        //this.instructions.put( "RLC (IX+d)","11 011 101 \n11 001 011 \n d \n00 000 110" );
        //this.instructions.put( "RLC (IY+d)","11 111 101 \n11 001 011 \n d \n00 000 110" );
		// this.instructions.put( "RL s",   "00 010 110" );
        // this.instructions.put( "RRC s",  "00 001 110" );
        // this.instructions.put( "RR s",   "00 011 110" );
        // this.instructions.put( "SLA s"   "00 100 110" );
		// this.instructions.put( "SRA s",  "00 101 110" );
        // this.instructions.put( "SRL s",  "00 111 110" );
        this.instructions.put( "RLD",       "11 101 101 \n01 101 111" );
        this.instructions.put( "RRD",       "11 101 101 \n01 100 111" );

        // Grupo de bit y reinicio
        //this.instructions.put( "BIT b, r",     "11 001 011 \n01 b r" );
        //this.instructions.put( "BIT b, (HL)",  "11 001 011 \n01 b 110" );
        //this.instructions.put( "BIT b, (IX+d)","11 011 101 \n11 001 011 \n d \n01 b 110" );
        //this.instructions.put( "BIT b, (IY+d)","11 111 101 \n11 001 011 \n d \n01 b 110" );
		//this.instructions.put( "SET b, r",     "11 001 011 \n11 b r" );
        //this.instructions.put( "SET b, (HL)",  "11 001 011 \n11 b 110" );
		//this.instructions.put( "SET b, (IX+d)","11 011 101 \n11 001 011 \n d \n11 b 110" );
        //this.instructions.put( "SET b, (IY+d)","11 111 101 \n11 001 011 \n d \n11 b 110" );
        //this.instructions.put( "RES b, s",     "10" );

        // Grupo de salto
        // this.instructions.put( "JP nn",     "11 000 011" );
		//this.instructions.put( "JP cc, nn", "11 cc 010" );
        this.instructions.put( "JR e",      "00 011 000" );
        this.instructions.put( "JR C, e",   "00 111 000" );
        this.instructions.put( "JR NC, e",  "00 110 000" );
		this.instructions.put( "JR Z, e",   "00 101 000" );
        this.instructions.put( "JR NZ, e",  "00 100 000" );
        this.instructions.put( "JP (HL)",   "11 101 001" );
        this.instructions.put( "JP (IX)",   "11 011 101 \n11 101 001" );
		this.instructions.put( "JP (IY)",   "11 111 101 \n11 101 001" );
        // this.instructions.put( "DJNZ, e",   "00 010 000" );

        // Grupo de llamada y retorno
        // this.instructions.put( "CALL nn",    "11 001 101" );
        // this.instructions.put( "CALL cc, nn","11 cc 100" );
        this.instructions.put( "RET",        "11 001 001" );
        //this.instructions.put( "RET cc",     "11 cc 000" );
		this.instructions.put( "RETI",       "11 101 101 \n01 001 101" );
        this.instructions.put( "RETN",       "11 101 101 \n01 000 101" );
        //this.instructions.put( "RST p",      "11 t 111" );

    }

    //TODO: ingresar todos los opcodes
    //TODO: Luego de las instrucciones EX SP todas llevan dos opcodes

}
