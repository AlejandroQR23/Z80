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

    public Opcode(){
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

            DecodeTools decoder = new DecodeTools();                            // herramienta de decodificacion

            // Se obtienen los registros
            System.out.print( "\n Intruccion: " + inst );
            String gInst = decoder.getInst( inst );
            System.out.print( "\n Intruccion G: " + gInst );
            String gOpc = this.instructions.get( gInst );
            System.out.println( "\n Opcode G " + gOpc );
            String opcode = decoder.changeBit( gOpc );
            String opcode2 = decoder.changeRegister( opcode );
            String opcode3 = decoder.changeCondition( opcode2 );
            System.out.println( "\n Opcode " + opcode3 );

            opcodes.add( opcode3 );

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

        // Grupo de carga 8-bit
        this.instructions.put( "LD r, r'", "01 r, r'" );
        this.instructions.put( "LD r, n",  "00 r 110 \n n" );
        this.instructions.put( "LD r, (HL)",  "01 r 110" );
        //this.instructions.put( "LD r, (IX+d)",  "01 011 101 \n 01 r 110 \n d" );
        //this.instructions.put( "LD r, (IY+d)",  "11 111 101 \n 01 r 110 \n d" );
        this.instructions.put( "LD (HL), r",  "01 110 r" );
        //this.instructions.put( "LD (IX+d), r",  "11 011 101 \n01 110 r \n d" );
        //this.instructions.put( "LD (IY+d), r",  "11 111 101 \n01 110 r \n d" );
        this.instructions.put( "LD (HL), n",  "00 110 110 \n n" );
        //this.instructions.put( "LD (IX+d), n",  "11 011 101 \n 00 110 110 \n d \n n" );
        this.instructions.put( "LD r, (BC)",  "00 001 010" );
        this.instructions.put( "LD r, (DE)",  "00 011 010" );
        this.instructions.put( "LD r, (nn)",  "00 111 010 \n n \n n" );
        this.instructions.put( "LD (BC), r",  "00 000 010" );                   // se sabe por tablas que r solo puede ser A
        this.instructions.put( "LD (DE), r",  "00 010 010" );
        this.instructions.put( "LD (nn), r",  "00 110 010 \n n \n n" );
        this.instructions.put( "LD r, I",  "11 101 101 \n01 010 111" );
        this.instructions.put( "LD r, R",  "11 101 101 \n01 011 111" );
        this.instructions.put( "LD I, r",  "11 101 101 \n01 000 111" );
        this.instructions.put( "LD R, r",  "11 101 101 \n01 001 111" );

        //Grupo de carga de 16 bits
        this.instructions.put( "LD dd, nn",  "00 dd0 001 \n n \n n" );
        this.instructions.put( "LD IX, nn",  "11 011 101 \n 00 100 001 \n n \n n" );
        this.instructions.put( "LD IY, nn",  "11 111 101 \n 00 100 001 \n n \n n" );
        this.instructions.put( "LD HL, (nn)",  "00 101 010 \n n \n n" );
        this.instructions.put( "LD dd, (nn)",  "11 101 101 \n 01 dd1 011 \n n \n n" );
        this.instructions.put( "LD IX, (nn)",  "11 011 101 \n 00 101 010 \n n \n n" );
        this.instructions.put( "LD IY, (nn)",  "11 111 101 \n 00 101 010 \n n \n n" );
        this.instructions.put( "LD (nn), HL",  "00 100 010 \n n \n n" );
        this.instructions.put( "LD (nn), dd",  "11 101 101 \n 01 dd0 011 \n n \n n" );
        this.instructions.put( "LD (nn), IX",  "11 011 101 \n 00 100 010 \n n \n n" );
        this.instructions.put( "LD (nn), IY",  "11 111 101 \n 00 100 010 \n n \n n" );
        this.instructions.put( "LD SP, HL", "11 111 001" );
        this.instructions.put( "LD SP, IX", "11 011 101 \n11 111 001" );
        this.instructions.put( "LD SP, IY", "11 111 101 \n11 111 001" );
        this.instructions.put( "PUSH dd",   "11 dd0 101" );
        this.instructions.put( "PUSH IX",   "11 011 101 \n11 100 101" );
        this.instructions.put( "PUSH IY",   "11 011 101 \n11 111 001" );
        this.instructions.put( "POP dd",    "11 dd0 001" );
        this.instructions.put( "POP IX",    "11 011 101 \n11 100 001" );
        this.instructions.put( "POP IY",    "11 111 101 \n11 100 001" );

        // Grupo de transferencia e intercambio
        this.instructions.put( "EX DE, HL",   "11 101 011" );
        this.instructions.put( "EX AF, AF'",  "00 001 000" );
        this.instructions.put( "EXX",         "11 011 001" );
        this.instructions.put( "EX (SP), HL", "11 100 011" );
        this.instructions.put( "EX (SP), IX", "11 011 101 \n11 100 011" );
        this.instructions.put( "EX (SP), IY", "11 111 101 \n11 100 011" );
        this.instructions.put( "LDI",         "11 101 101 \n10 100 000" );
        this.instructions.put( "LDIR",        "11 101 101 \n10 110 000");
        this.instructions.put( "LDD",         "11 101 101 \n10 101 000");
        this.instructions.put( "LDDR",        "11 101 101 \n10 111 000");
        this.instructions.put( "CPI",         "11 101 101 \n10 100 001");
        this.instructions.put( "CPIR",        "11 101 101 \n10 110 001");
        this.instructions.put( "CPD",         "11 101 101 \n10 101 001");
        this.instructions.put( "CPDR",        "11 101 101 \n10 111 001");

        // Grupo aritmetico y logico de 8 bits
        this.instructions.put( "ADD A, r",       "10 000 r");
        this.instructions.put( "ADD n",       "11 000 110 n");
        this.instructions.put( "ADD (HL)",    "10 000 110");
        // this.instructions.put( "ADD (IX+d)",  "11 011 101 \n10 000 110 d");
        // this.instructions.put( "ADD (IY+d)",  "11 111 101 \n10 000 110 d ");
        this.instructions.put( "ADC n",       "11 001 110 n");
        this.instructions.put( "ADC r",       "10 001 r");
        this.instructions.put( "ADC (dd)",    "10 010 110");
        //this.instructions.put( "ADC (IX+d)",  "11 011 101\n10 001 110 d");
        //this.instructions.put( "ADC (IY+d)",  "11 111 101\n10 001 110 d");
        this.instructions.put( "SUB n",       "11 010 110 n");
        this.instructions.put( "SUB r",       "10 010 r");
        this.instructions.put( "SUB (dd)",    "10 010 110");
        //this.instructions.put( "SUB (IX+d)",  "11 011 101 \n10 010 110 d");
        //this.instructions.put( "SUB (IY+d)",  "11 111 101 \n10 010 110 d");
        this.instructions.put( "SUB n",       "11 011 110 n");
        this.instructions.put( "SBC r",       "10 011 r");
        this.instructions.put( "SBC (HL)",    "10 011 110");
        //this.instructions.put( "SBC (IX+d)",  "11 011 101 \n10 011 110 d");
        //this.instructions.put( "SBC (IY+d)",  "11 111 101 \n10 011 110 d");
        this.instructions.put( "SBC n",       "11 011 110 n");
        this.instructions.put( "AND r",       "10 100 r");
        this.instructions.put( "AND (HL)",    "10 100 110");
        //this.instructions.put( "AND (IX+d)",  "11 011 101 10 \n100 110 d");
        //this.instructions.put( "AND (IY+d)",  "11 111 101 10 \n100 110 d");
        this.instructions.put( "AND n",       "11 100 110 n");
        this.instructions.put( "OR r",       "10 110 r");
        this.instructions.put( "OR (HL)",    "10 110 110");
        //this.instructions.put( "OR (IX+d)",  "11 011 101 10 \n110 110 d");
        //this.instructions.put( "OR (IY+d)",  "11 111 101 10 \n110 110 d");
        this.instructions.put( "OR n",       "11 110 110 n");
        this.instructions.put( "XOR r",       "10 101 r");
        this.instructions.put( "XOR (HL)",    "10 101 110");
        //this.instructions.put( "XOR (IX+d)",  "11 011 101 10 \n101 110 d");
        //this.instructions.put( "XOR (IY+d)",  "11 111 101 10 \n101 110 d");
        this.instructions.put( "XOR n",       "11 101 110 n");
        this.instructions.put( "CP r",        "10 111 r");
        this.instructions.put( "CP (HL)",    "10 111 110");
        //this.instructions.put( "CP (IX+d)",  "11 011 101 10 \n111 110 d");
        //this.instructions.put( "CP (IY+d)",  "11 111 101 10 \n111 110 d");
        this.instructions.put( "CP n",       "11 111 110 n");
        this.instructions.put( "INC r",         "00 r 100");
        this.instructions.put( "INC (HL)",      "00 110 100");
        // this.instructions.put( "INC (IX+d)",    "11 011 101 \n00 110 100 d");
        //this.instructions.put( "INC (IY+d)",    "11 111 101 \n00 110 100 d");
        this.instructions.put( "DEC r",       "00 r 101");
        this.instructions.put( "DEC (HL)",    "00 110 101");
        //this.instructions.put( "DEC (IX+d)",  "11 011 101 \n00 110 101 d");
        //this.instructions.put( "DEC (IY+d)",  "11 111 101 \n00 110 101 d");

        // Grupo de rotacion y cambio
        this.instructions.put( "RLCA",     "00 000 111" );
        this.instructions.put( "RLA",      "00 010 111" );
        this.instructions.put( "RRCA",     "00 001 111" );
        this.instructions.put( "RRA ",     "00 011 111" );
        this.instructions.put( "RLC r",    "11 001 011 \n00 000 r" );
        this.instructions.put( "RLC (HL)", "11 001 011 \n00 000 110" );
        //this.instructions.put( "RLC (IX+d)","11 011 101 \n11 001 011 \n d \n00 000 110" );
        //this.instructions.put( "RLC (IY+d)","11 111 101 \n11 001 011 \n d \n00 000 110" );
		// this.instructions.put( "RL s",   "00 010 110" );
        // this.instructions.put( "RRC s",  "00 001 110" );
        // this.instructions.put( "RR s",   "00 011 110" );
        this.instructions.put( "SLA r",     "11001011 \n00100 r" );
        this.instructions.put( "SLA (HL)",  "11001011 \n00100110" );
        //this.instructions.put( "SLA (IX+d)","11011101 \n11001011 \n d \n00100110" );
        //this.instructions.put( "SLA (IY+d)","11111101 \n11001011 \n d \n00100110" );
        this.instructions.put( "SRA r",     "11001011 \n00101 r" );
        this.instructions.put( "SRA (HL)",  "11001011 \n00101110" );
        //this.instructions.put( "SRA (IX+d)","11011101 \n11001011 \n d \n00101110" );
        //this.instructions.put( "SRA (IY+d)","11111101 \n11001011 \n d \n00101110" );
        this.instructions.put( "SRL r",     "11001011 \n00111 r" );
        this.instructions.put( "SRL (HL)",  "11001011 \n00111110" );
        //this.instructions.put( "SRL (IX+d)","11011101 \n11001011 \n d \n00111110" );
        //this.instructions.put( "SRL (IY+d)","11111101 \n11001011 \n d \n00111110" );
        this.instructions.put( "RLD",       "11 101 101 \n01 101 111" );
        this.instructions.put( "RRD",       "11 101 101 \n01 100 111" );

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

        // Grupo de bit y reinicio
        this.instructions.put( "BIT b, r",     "11 001 011 \n01 b r" );
        this.instructions.put( "BIT b, (HL)",  "11 001 011 \n01 b 110" );
        //this.instructions.put( "BIT b, (IX+d)","11 011 101 \n11 001 011 \n d \n01 b 110" );
        //this.instructions.put( "BIT b, (IY+d)","11 111 101 \n11 001 011 \n d \n01 b 110" );
		this.instructions.put( "SET b, r",     "11 001 011 \n11 b r" );
        this.instructions.put( "SET b, (HL)",  "11 001 011 \n11 b 110" );
		//this.instructions.put( "SET b, (IX+d)","11 011 101 \n11 001 011 \n d \n11 b 110" );
        //this.instructions.put( "SET b, (IY+d)","11 111 101 \n11 001 011 \n d \n11 b 110" );
        this.instructions.put( "RES b, r",     "10 001 011 \n11 b r" );
        this.instructions.put( "RES b, (HL)",  "10 001 011 \n11 b 110" );
		//this.instructions.put( "RES b, (IX+d)","10 011 101 \n11 001 011 \n d \n11 b 110" );
        //this.instructions.put( "RES b, (IY+d)","10 111 101 \n11 001 011 \n d \n11 b 110" );

        // Grupo de salto
        this.instructions.put( "JP nn",     "11 000 011" );
		this.instructions.put( "JP cc, nn", "11 cc 010" );
        this.instructions.put( "JR e",      "00 011 000" );
        this.instructions.put( "JR C, e",   "00 111 000" );
        this.instructions.put( "JR NC, e",  "00 110 000" );
		this.instructions.put( "JR Z, e",   "00 101 000" );
        this.instructions.put( "JR NZ, e",  "00 100 000" );
        this.instructions.put( "JP (HL)",   "11 101 001" );
        this.instructions.put( "JP (IX)",   "11 011 101 \n11 101 001" );
		this.instructions.put( "JP (IY)",   "11 111 101 \n11 101 001" );
        this.instructions.put( "DJNZ, e",   "00 010 000" );

        // Grupo de llamada y retorno
        this.instructions.put( "CALL nn",    "11 001 101" );
        this.instructions.put( "CALL cc, nn" "11 cc 100" );
        this.instructions.put( "RET",        "11 001 001" );
        this.instructions.put( "RET cc",     "11 cc 000" );
		this.instructions.put( "RETI",       "11 101 101 \n01 001 101" );
        this.instructions.put( "RETN",       "11 101 101 \n01 000 101" );
        //this.instructions.put( "RST p",      "11 t 111" );

    }

    //TODO: ingresar todos los opcodes
    //TODO: Luego de las instrucciones EX SP todas llevan dos opcodes

}
