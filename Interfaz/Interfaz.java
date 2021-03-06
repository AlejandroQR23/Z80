package Interfaz;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Opcodes.*;
import Files.*;

/**
 * Clase que implementa la interfaz gráfica del ensamblador
 *
 * @version 0.2 16/01/2021
 * @author Lizeth Durán González
 */
public class Interfaz extends JFrame {
   
    JPanel contentPane;
    public static String nombreArchivoIn = "SUMA";

    public Interfaz() {

        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);
        contentPane.add(verJLabel1());
        contentPane.add(verJLabel2());
        contentPane.add(verJLabel3());
        contentPane.add(verJLabel4());
        inicializador();

        JTextArea textArea = verTextArea();
        contentPane.add(verScroll(textArea));
        JButton btnTraducir = botonTraducir(textArea);
        btnTraducir.setVisible(false);
        escogerArchivo(btnTraducir, textArea);

    }

    /**
     * Da formato a la ventana principal
     */
    private void inicializador() {
        setSize(800, 650);
        setLayout(null);
        setTitle("Proyecto final");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    /**
     * Etiqueta 1 a mostrar en la ventana
     * 
     * @return
     */
    private JLabel verJLabel1() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("UNAM");
        ejemplo.setBounds(370, 5, 200, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    /**
     * Etiqueta 2 a mostrar en la ventana
     * 
     * @return
     */
    private JLabel verJLabel2() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("FACULTAD DE INGENIERIA");
        ejemplo.setBounds(280, 35, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    /**
     * Etiqueta 3 a mostrar en la ventana
     * 
     * @return
     */
    private JLabel verJLabel3() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("Estructura y Programacion de Computadoras");
        ejemplo.setBounds(200, 75, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    /**
     * Etiqueta 4 a mostra en la ventana
     * 
     * @return
     */
    private JLabel verJLabel4() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("ENSAMBLADOR FOR DUMMIES");
        ejemplo.setBounds(190, 150, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 2, 27));

        return ejemplo;
    }

    /**
     * Area de la ventana donde se mostrará el código
     * 
     * @return
     */
    private JTextArea verTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(130, 310, 550, 230);
        textArea.setEditable(false);

        return textArea;
    }

    /**
     * Permite hacer scroll a la ventana donde se mostrará el código
     */
    private JScrollPane verScroll(JTextArea textArea) {
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(130, 310, 550, 230);
        return scroll;
    }

    /**
     * Muestra al usuario su directorio de archivos de donde puede escoger el
     * archivo tipo asm a ser ensamblado.
     * 
     * El código en ensamblador se muestra en la ventana.
     * 
     * @param btnTraducir
     * @param textArea
     */
    private void escogerArchivo(JButton btnTraducir, JTextArea textArea) {

        JButton boton = new JButton("Seleccionar archivo");
        boton.setBounds(290, 280, 200, 20);
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setFileFilter(new FileNameExtensionFilter("asm", "asm"));
                int seleccion = fc.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File fichero = fc.getSelectedFile();
                    // Nombre del archivo seleccionado
                    String nombreArch = fc.getName(fichero);
                    //System.out.println("NOmbre Arch: " + nombreArch);

                    try (FileReader fr = new FileReader(fichero)) {
                        String cadena = "";
                        int valor = fr.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = fr.read();
                        }
                        textArea.setText(cadena);

                        // Envia los datos del archivo .asm
                        ValidarArchivoIn.obtenerCodigo(cadena, nombreArch);
                        nombreArchivoIn = nombreArch.split(".asm")[0];
                        //System.out.println("auxname: " + nombreArchivoIn);
                        btnTraducir.setVisible(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        contentPane.add(boton);
    }

    /**
     * Al hacer clic al botón, se manda a llamar al método que inicia todo el
     * proceso de ensamblado.
     * 
     * @param textArea
     * @return
     */

    private JButton botonTraducir(JTextArea textArea) {
        JButton boton = new JButton("Traducir");
        boton.setBounds(290, 550, 200, 20);
        contentPane.add(boton);
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // ************************************ */
                // Metodo que iniciará toda la traducción

                FileOptions f = new FileOptions(Interfaz.nombreArchivoIn);
                LinkedList<String> list = f.readFile();                       
               // se leen las instrucciones del archivo de texto
               
                f.createFile();                                       
                // crea el archivo donde iran los opcodes

                Opcode opc = new Opcode();
                LinkedList<String> opcodes = opc.decodeInst( list );
                f.writeFile( opcodes );  
                ConvertirAHexadecimal.formatear(opcodes);

                //Imprimir en textArea
                CrearArchivoFinal.init();
                String data = verArchivo();
                textArea.setText(data);
                boton.setVisible(false);

            }
        });
        return boton;
    }

    /**
     * Lee el archivo tipo lst 
     * @return
     */
    private String verArchivo(){
        //Direccion del archivo ensamblado
        File fichero = new File ("../tests/" + Interfaz.nombreArchivoIn + ".lst");
        String cadena = "";    
        try (FileReader fr = new FileReader(fichero)) {
            
                int valor = fr.read();
                while (valor != -1) {
                    cadena = cadena + (char) valor;
                    valor = fr.read();
                }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return cadena;
    }

    public static void crearDirectorio(){
        File directorio = new File("../tests/");
        if (!(directorio.exists())) {
            directorio.mkdirs();
        }
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    crearDirectorio();
                    Interfaz v1 = new Interfaz();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        
        
        

    }
}
