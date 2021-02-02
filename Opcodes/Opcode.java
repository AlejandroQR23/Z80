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

            System.out.print( "\n Intruccion: " + inst );
            String gInst = decoder.getInst( inst );                             // se obtiene la instruccion general
            System.out.print( "\n Intruccion G: " + gInst );
            String gOpc = this.instructions.get( gInst );                       // se extrae el opcode general
            System.out.println( "\n Opcode G " + gOpc );
            String opcode = decoder.getOpc( gOpc );                             // se obtiene el opcode final en binario
            System.out.println( "\n Opcode " + opcode );

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

        // Grupo de carga 8-bit
        this.instructions.put( "LD r, r'",      "01r,r'" );
        this.instructions.put( "LD r, nn",      "00r110\nwnn" );
        this.instructions.put( "LD r, (HL)",    "01r110" );
        this.instructions.put( "LD r, (IX+d)",  "01011101\n01r110\nwd" );
        this.instructions.put( "LD r, (IY+d)",  "11111101\n01r110\nwd" );
        this.instructions.put( "LD (HL), r",    "01110r" );
        this.instructions.put( "LD (IX+d), r",  "11011101\n01110r\nwd" );
        this.instructions.put( "LD (IY+d), r",  "11111101\n01110r\nwd" );
        this.instructions.put( "LD (HL), nn",   "00110110\nwnn" );
        this.instructions.put( "LD (IX+d), nn", "11011101\n00110110\nwd\nwnn" );
        this.instructions.put( "LD r, (BC)",    "00001010" );
        this.instructions.put( "LD r, (DE)",    "00011010" );
        this.instructions.put( "LD r, (nn)",    "00111010\nwnn" );
        this.instructions.put( "LD (BC), r",    "00000010" );                   // se sabe por tablas que r solo puede ser A
        this.instructions.put( "LD (DE), r",    "00010010" );
        this.instructions.put( "LD (nn), r",    "00110010\nwnn" );
        this.instructions.put( "LD r, I",       "11101101\n01010111" );
        this.instructions.put( "LD r, R",       "11101101\n01011111" );
        this.instructions.put( "LD I, r",       "11101101\n01000111" );
        this.instructions.put( "LD R, r",       "11101101\n01001111" );

        //Grupo de carga de 16 bits
        this.instructions.put( "LD dd, nn",    "00dd0001\nwnn" );
        this.instructions.put( "LD SP, nn",    "00110001\nwnn" );
        this.instructions.put( "LD IX, nn",    "11011101\n00100001\nwnn" );
        this.instructions.put( "LD IY, nn",    "11111101\n00100001\nwnn" );
        this.instructions.put( "LD dd, (nn)",  "11101101\n01dd1011\nwnn" );
        this.instructions.put( "LD IX, (nn)",  "11011 101\n00101010\nwnn" );
        this.instructions.put( "LD IY, (nn)",  "11111101\n00101010\nwnn" );
        this.instructions.put( "LD (nn), dd",  "11101101\n01dd0011\nwnn" );
        this.instructions.put( "LD (nn), IX",  "11011101\n00100010\nwnn" );
        this.instructions.put( "LD (nn), IY",  "11111101\n00100010\nwnn" );
        //this.instructions.put( "LD SP, HL",  "11 111 001" );
        this.instructions.put( "LD dd, IX",    "11011101\n11111001" );          // dd solo deberia ser SP
        this.instructions.put( "LD dd, IY",    "11111101\n11111001" );          // dd solo deberia ser SP
        this.instructions.put( "PUSH dd",      "11dd0101" );
        this.instructions.put( "PUSH AF",      "11 110101" );
        this.instructions.put( "PUSH IX",      "11011101\n11100101" );
        this.instructions.put( "PUSH IY",      "11011101\n11111001" );
        this.instructions.put( "POP dd",       "11dd0001" );
        this.instructions.put( "POP AF",       "11110001" );
        this.instructions.put( "POP IX",       "11011101\n11100001" );
        this.instructions.put( "POP IY",       "11111101\n11100001" );

        // Grupo de transferencia e intercambio
        //this.instructions.put( "EX DE, HL",   "11 101 011" );
        //this.instructions.put( "EX AF, AF'",  "00 001 000" );
        this.instructions.put( "EXX",         "11011001" );
        this.instructions.put( "EX (SP), dd", "11100011" );                     // dd solo puede ser HL
        this.instructions.put( "EX (SP), IX", "11011101\n11100011" );
        this.instructions.put( "EX (SP), IY", "11111101\n11100011" );
        this.instructions.put( "LDI",         "11101101\n10100000" );
        this.instructions.put( "LDIR",        "11101101\n10110000");
        this.instructions.put( "LDD",         "11101101\n10101000");
        this.instructions.put( "LDDR",        "11101101\n10111000");
        this.instructions.put( "CPI",         "11101101\n10100001");
        this.instructions.put( "CPIR",        "11101101\n10110001");
        this.instructions.put( "CPD",         "11101101\n10101001");
        this.instructions.put( "CPDR",        "11101101\n10111001");

        // Grupo aritmetico y logico de 8 bits
        this.instructions.put( "ADD r",       "10000r");
        this.instructions.put( "ADD nn",      "11000110wnn");
        this.instructions.put( "ADD (HL)",    "10000110");
        this.instructions.put( "ADD (IX+d)",  "11011101\n10000110wd");
        this.instructions.put( "ADD (IY+d)",  "11111101\n10000110wd ");
        this.instructions.put( "ADC r",       "10001r");
        this.instructions.put( "ADC nn",      "11001110wnn");
        this.instructions.put( "ADC (HL)",    "10001110");
        this.instructions.put( "ADC (IX+d)",  "11011101\n10001110wd");
        this.instructions.put( "ADC (IY+d)",  "11111101\n10001110wd");
        this.instructions.put( "SUB r",       "10010r");
        this.instructions.put( "SUB nn",      "11010110nn");
        this.instructions.put( "SUB (HL)",    "10010110");
        this.instructions.put( "SUB (IX+d)",  "11011101\n10010110wd");
        this.instructions.put( "SUB (IY+d)",  "11111101\n10010110wd");
        this.instructions.put( "SBC r",       "10011r");
        this.instructions.put( "SBC nn",      "11011110wnn");
        this.instructions.put( "SBC (HL)",    "10011110");
        this.instructions.put( "SBC (IX+d)",  "11011101\n10011110wd");
        this.instructions.put( "SBC (IY+d)",  "11111101\n10011110wd");
        this.instructions.put( "AND r",       "10100r");
        this.instructions.put( "AND nn",      "11100110wnn");
        this.instructions.put( "AND (HL)",    "10100110");
        this.instructions.put( "AND (IX+d)",  "11011101\n10100110wd");
        this.instructions.put( "AND (IY+d)",  "11111101\n10100110wd");
        this.instructions.put( "OR r",        "10110r");
        this.instructions.put( "OR nn",       "11110110wnn");
        this.instructions.put( "OR (HL)",     "10110110");
        this.instructions.put( "OR (IX+d)",   "11011101\n10110110wd");
        this.instructions.put( "OR (IY+d)",   "11111101\n10110110wd");
        this.instructions.put( "XOR r",       "10101r");
        this.instructions.put( "XOR (HL)",    "10101110");
        this.instructions.put( "XOR (IX+d)",  "11011101\n10101110wd");
        this.instructions.put( "XOR (IY+d)",  "11111101\n10101110wd");
        this.instructions.put( "XOR nn",      "11101110wnn");
        this.instructions.put( "CP r",        "10111r");
        this.instructions.put( "CP nn",       "11111110nn");
        this.instructions.put( "CP (HL)",     "10111110");
        this.instructions.put( "CP (IX+d)",   "11011101\n10111110wd");
        this.instructions.put( "CP (IY+d)",   "11111101\n10111110wd");
        this.instructions.put( "INC r",       "00r100");
        this.instructions.put( "INC (HL)",    "00110100");
        this.instructions.put( "INC (IX+d)",  "11011101\n00110100wd");
        this.instructions.put( "INC (IY+d)",  "11111101\n00110100wd");
        this.instructions.put( "DEC r",       "00r101");
        this.instructions.put( "DEC (HL)",    "00110101");
        this.instructions.put( "DEC (IX+d)",  "11011101\n00110101wd");
        this.instructions.put( "DEC (IY+d)",  "11111101\n00110101wd");

        // Grupo aritmetico de 16 bits
        this.instructions.put( "ADD dd",     "00dd1001" );
        this.instructions.put( "ADC dd",     "11101101\n01dd1010" );
        this.instructions.put( "SBC dd",     "11101101\n01dd0010" );
        this.instructions.put( "ADD IX, dd", "11011101\n00dd1001" );
        this.instructions.put( "ADD IY, dd", "11111101\n00dd1001" );
        this.instructions.put( "INC dd",     "00dd0011" );
        this.instructions.put( "INC IY",     "11111101\n00100011" );
        this.instructions.put( "DEC dd",     "00dd1011" );
        this.instructions.put( "DEC IX",     "11011101\n00101011" );
        this.instructions.put( "DEC IY",     "11111101\n00101011" );

        // Grupo de rotacion y cambio
        this.instructions.put( "RLCA",       "00000111" );
        this.instructions.put( "RLA",        "00010111" );
        this.instructions.put( "RRCA",       "00001111" );
        this.instructions.put( "RRA",        "00011111" );
        this.instructions.put( "RLC r",      "11001011\n00000r" );
        this.instructions.put( "RLC (HL)",   "11001011\n00000110" );
        this.instructions.put( "RLC (IX+d)", "11011101\n11001011\nwd\n00000110" );
        this.instructions.put( "RLC (IY+d)", "11111101\n11001011\nwd\n00000110" );
        this.instructions.put( "RL r",       "11001011\n00010r" );
        this.instructions.put( "RL (HL)",    "11001011\n00010110" );
        this.instructions.put( "RL (IX+d)",  "11011101\n11001011\nwd\n00010110" );
        this.instructions.put( "RL (IY+d)",  "11111101\n11001011 \nwd\n00010110" );
        this.instructions.put( "RRC r",      "11001011\n00001r" );
        this.instructions.put( "RRC (HL)",   "11001011\n00001110" );
        this.instructions.put( "RRC (IX+d)", "11011101\n11001011\nwd\n00001110" );
        this.instructions.put( "RRC (IY+d)", "11111101\n11001011\nwd\n00001110" );
        this.instructions.put( "RR r",       "11001011\n00011r" );
        this.instructions.put( "RR (HL)",    "11001011\n00011110" );
        this.instructions.put( "RR (IX+d)",  "11011101\n11001011\nwd\n00011110" );
        this.instructions.put( "RR (IY+d)",  "11111101\n11001011\nwd\n00011110" );
        this.instructions.put( "SLA r",      "11001011\n00100r" );
        this.instructions.put( "SLA (HL)",   "11001011\n00100110" );
        this.instructions.put( "SLA (IX+d)", "11011101\n11001011\nwd\n00100110" );
        this.instructions.put( "SLA (IY+d)", "11111101\n11001011\nwd\n00100110" );
        this.instructions.put( "SRA r",      "11001011\n00101r" );
        this.instructions.put( "SRA (HL)",   "11001011\n00101110" );
        this.instructions.put( "SRA (IX+d)", "11011101\n11001011\nwd\n00101110" );
        this.instructions.put( "SRA (IY+d)", "11111101\n11001011\nwd\n00101110" );
        this.instructions.put( "SRL r",      "11001011\n00111r" );
        this.instructions.put( "SRL (HL)",   "11001011\n00111110" );
        this.instructions.put( "SRL (IX+d)", "11011101\n11001011\nwd\n00111110" );
        this.instructions.put( "SRL (IY+d)", "11111101\n11001011\nwd\n00111110" );
        this.instructions.put( "RLD",        "11101101\n01101111" );
        this.instructions.put( "RRD",        "11101101\n01100111" );

        // Grupo de aritmetica y control -- 76 543 210
        this.instructions.put( "NOP",  "00000000" );
        this.instructions.put( "DAA",  "00100111" );
        this.instructions.put( "CPL",  "00101111" );
        this.instructions.put( "NEG",  "11101101" );
        this.instructions.put( "CCF",  "00111111" );
        this.instructions.put( "SCF",  "00110111" );
        this.instructions.put( "HALT", "01110110" );
        this.instructions.put( "DI",   "11110011" );
        this.instructions.put( "EI",   "01111110" );
        this.instructions.put( "IM 0", "11101101\n01000110" );
        this.instructions.put( "IM 1", "11101101\n01010110" );
        this.instructions.put( "IM 2", "11101101\n01011110" );

        // Grupo de bit y reinicio
        this.instructions.put( "BIT b, r",      "11001011\n01br" );
        this.instructions.put( "BIT b, (HL)",   "11001011\n01b110" );
        this.instructions.put( "BIT b, (IX+d)", "11011101\n11001011\nd\n01b110" );
        this.instructions.put( "BIT b, (IY+d)", "11111101\n11001011\nd\n01b110" );
		this.instructions.put( "SET b, r",      "11001011\n11br" );
        this.instructions.put( "SET b, (HL)",   "11001011\n11b110" );
		this.instructions.put( "SET b, (IX+d)", "11011101\n11001011\nwd\n11b110" );
        this.instructions.put( "SET b, (IY+d)", "11111101\n11001011\nwd\n11b110" );
        this.instructions.put( "RES b, r",      "10001011\n11br" );
        this.instructions.put( "RES b, (HL)",   "10001011\n11b110" );
		this.instructions.put( "RES b, (IX+d)", "10011101\n11001011\nwd\n11b110" );
        this.instructions.put( "RES b, (IY+d)", "10111101\n11001011\nwd\n11b110" );

        // Grupo de salto
        this.instructions.put( "JP nn",     "11000011" );
		this.instructions.put( "JP cc, nn", "11cc010" );
        this.instructions.put( "JR e",      "00011000" );
        this.instructions.put( "JR C, e",   "00111000" );
        this.instructions.put( "JR NC, e",  "00110000" );
		this.instructions.put( "JR Z, e",   "00101000" );
        this.instructions.put( "JR NZ, e",  "00100000" );
        this.instructions.put( "JP (HL)",   "11101001" );
        this.instructions.put( "JP (IX)",   "11011101\n11101001" );
		this.instructions.put( "JP (IY)",   "11111101\n11101001" );
        this.instructions.put( "DJNZ, e",   "00010000" );

        // Grupo de llamada y retorno
        this.instructions.put( "CALL nn",    "11001101" );
        this.instructions.put( "CALL cc, nn", "11cc100" );
        this.instructions.put( "RET",        "11001001" );
        this.instructions.put( "RET cc",     "11cc000" );
		this.instructions.put( "RETI",       "11101101\n01001101" );
        this.instructions.put( "RETN",       "11101101\n01000101" );
        this.instructions.put( "RST p",      "11t111" );

    }

}
