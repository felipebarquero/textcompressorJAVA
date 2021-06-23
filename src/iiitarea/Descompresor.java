/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iiitarea;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.util.BitSet;

/**
 *
 * @author Luis Felipe Barquero
 */
public class Descompresor {

    public ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda(new SimboloHufmann('?', null));
    public String arbolString;
    public String mensajeDecodificado = "";
    public int contador = 0;

   public void descompresor() {
        ArbolBinarioDeBusqueda arbol = null;
        
        BitSet mensaje = new BitSet();
        String arbolString;
        try {
            FileInputStream fileIn = new FileInputStream("archivo.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            mensaje = (BitSet) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Mensaje no encontrado");
            c.printStackTrace();
            return;
        }

        try {
            FileInputStream fileIn = new FileInputStream("arbolString.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            arbolString = (String) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("Mensaje no encontrado");
            c.printStackTrace();
            return;
        }

       // System.out.print(mensaje);
        asignaString(arbolString);
        arbol = deserializa(arbolString);
        String mensajeDecodificado = decodificar(mensaje, arbol);

        System.out.print(mensajeDecodificado);
        
        
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("MensajeDescomprimido.txt");
            pw = new PrintWriter(fichero);
 
            for (int i = 0; i < mensajeDecodificado.length(); i++)
                pw.print(Character.toString(mensajeDecodificado.charAt(i)));
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
        
    }

   /**
    * Deserializa un arbolString.
    * @param arbolString String
    * @return  ArbolBinarioDeBusqueda
    */
    public ArbolBinarioDeBusqueda deserializa(String arbolString) {
        ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda();
        SimboloHufmann simb = new SimboloHufmann('?', null);
        arbol.asigneElemento(simb);
        creaArbol(this.arbol, this.arbol, arbolString);
        return this.arbol;
    }

    /**
     * Contruye el arbol deserializado.
     * @param padre Padre del Arbol.
     * @param nodo Nodo inicial.
     * @param arbolString  String
     */
    public void creaArbol(ArbolBinarioDeBusqueda padre, ArbolBinarioDeBusqueda nodo, String arbolString) {

        if (this.arbolString.startsWith("#D#")) {
            this.arbolString = this.arbolString.replaceFirst("#D#", "");
            if ((char) (this.arbolString.charAt(0)) == '?') {
                this.arbolString = this.arbolString.substring(1);
                ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda(new SimboloHufmann('?', null));
                try {
                    padre.inserte(arbol, 1);
                } catch (ExcepcionArbolBinario ex) {
                    Logger.getLogger(Descompresor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                char caracter = (char) this.arbolString.charAt(0);
                this.arbolString = this.arbolString.substring(1);
                ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda(new SimboloHufmann(caracter, null));
                try {
                    padre.inserte(arbol, 1);
                } catch (ExcepcionArbolBinario ex) {
                    Logger.getLogger(Descompresor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            creaArbol((ArbolBinarioDeBusqueda) padre.obtengaArbolDerecho(), padre, arbolString);
        }
        if (this.arbolString.startsWith("#I#")) {
            this.arbolString = this.arbolString.replaceFirst("#I#", "");
            if ((char) (this.arbolString.charAt(0)) == '?') {
                this.arbolString = this.arbolString.substring(1);
                ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda(new SimboloHufmann('?', null));
                try {
                    padre.inserte(arbol, 0);
                } catch (ExcepcionArbolBinario ex) {
                    Logger.getLogger(Descompresor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {

                char caracter = (char) this.arbolString.charAt(0);
                this.arbolString = this.arbolString.substring(1);
                ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda(new SimboloHufmann(caracter, null));
                try {
                    padre.inserte(arbol, 0);
                } catch (ExcepcionArbolBinario ex) {
                    Logger.getLogger(Descompresor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            creaArbol((ArbolBinarioDeBusqueda) padre.obtengaArbolIzquierdo(), padre, arbolString);
        }
        if (this.arbolString.startsWith("#F#")) {
            this.arbolString = this.arbolString.replaceFirst("#F#", "");
            padre = nodo;

        }

    }

    /**
     * Asigna un String al atributo arbolString.
     * @param arbolString String 
     */
    public void asignaString(String arbolString) {
        this.arbolString = arbolString;
    }

    /**
     * Decodifica el mensaje en Bitset.
     * @param mensaje Bitset
     * @param arbol Arbol de Hufmann
     * @return String
     */
    public String decodificar(BitSet mensaje, ArbolBinarioDeBusqueda arbol) {
        while(this.contador < mensaje.length()){
        decodifica(mensaje, arbol);
        }
        return this.mensajeDecodificado;

    }

    /**
     * Halla el caracter correspondiente a cada código.
     * 
     * @param mensaje BitSet
     * @param arbol  Arbol de Hufmann
     */
    public void decodifica(BitSet mensaje, ArbolBinarioDeBusqueda arbol) {
            if (arbol.esHoja()) {
                SimboloHufmann simb = (SimboloHufmann) arbol.obtengaElemento();
                this.mensajeDecodificado += Character.toString(simb.obtengaCaracter());
            } else {
                if (mensaje.get(this.contador)) {
                    this.contador++;
                    decodifica(mensaje, (ArbolBinarioDeBusqueda) arbol.obtengaArbolDerecho());
                } else  {
                    this.contador++;
                    decodifica(mensaje, (ArbolBinarioDeBusqueda) arbol.obtengaArbolIzquierdo());
                }

            }
        

    }
}
