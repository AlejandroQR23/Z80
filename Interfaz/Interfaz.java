package Interfaz;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

//import Opcodes.Opcode;

public class Interfaz extends JFrame {
    JPanel contentPane;

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

    private void inicializador() {
        setSize(800, 650);
        setLayout(null);
        setTitle("Proyecto final");
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private JLabel verJLabel1() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("UNAM");
        ejemplo.setBounds(370, 5, 200, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    private JLabel verJLabel2() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("FACULTAD DE INGENIERIA");
        ejemplo.setBounds(280, 35, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    private JLabel verJLabel3() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("Estructura y Programación de Computadoras");
        ejemplo.setBounds(200, 75, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 0, 18));

        return ejemplo;
    }

    private JLabel verJLabel4() {
        JLabel ejemplo = new JLabel();
        ejemplo.setText("TRADUCTOR DE ENSAMBLADOR");
        ejemplo.setBounds(185, 150, 700, 200);
        ejemplo.setFont(new Font("SANS_SERIF", 2, 27));

        return ejemplo;
    }

    private JTextArea verTextArea (){
        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds(130, 330, 550, 200);
        textArea.setEditable(false);

        return textArea;
    }

    private JScrollPane verScroll (JTextArea textArea){
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setBounds(130, 330, 550, 200);
        return scroll;
    }
    
    private void escogerArchivo(JButton btnTraducir, JTextArea textArea) {

        JButton boton = new JButton("Seleccionar archivo");
        boton.setBounds(290, 300, 200, 20);
        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setFileFilter(new FileNameExtensionFilter("asm", "asm"));
                int seleccion = fc.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File fichero = fc.getSelectedFile();
                    
                    try (FileReader fr = new FileReader(fichero)) {
                        String cadena = "";
                        int valor = fr.read();
                        while (valor != -1) {
                            cadena = cadena + (char) valor;
                            valor = fr.read();
                        }
                        textArea.setText(cadena);
                        
                        BufferedWriter bw = new BufferedWriter (new FileWriter("Interfaz/Data.asm"));
                        bw.write(cadena);
                        bw.close();

                        btnTraducir.setVisible(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        contentPane.add(boton); 
    }

    private JButton botonTraducir(JTextArea textArea) {
        JButton boton = new JButton("Traducir");
        boton.setBounds(290, 550, 200, 20);
        contentPane.add(boton);
        boton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){

                //Metodo que iniciará toda la traducción
                //Opcode.saludar();
                
                //Imprimir en textArea
                String data = verArchivo();
                textArea.setText(data);
                boton.setVisible(false);

            }
        });
        return boton;
    }

    private String verArchivo(){
        File fichero = new File ("tests/test1.txt");
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
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    Interfaz v1 = new Interfaz();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        
        
        

    }
}
