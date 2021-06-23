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
public class Compresor {

    public ListaOrdenada listaFinal = new ListaOrdenada();
    public String codigo = "";
    public String arbolString = "";

    /**
     * Ejecuta todos los métodos para comprimir un archivo de texto. 
     */
    public  void compresor() {


        //Define los lectores de Archivo.
        File archivo = null;
        FileReader lectorDeArchivo = null;
        BufferedReader lectorDeMemoria = null;

        JFileChooser fc = new JFileChooser();

        int resultado = fc.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda.
                archivo = fc.getSelectedFile();
                lectorDeArchivo = new FileReader(archivo);
                lectorDeMemoria = new BufferedReader(lectorDeArchivo);

                // Lectura del fichero y conteo.
                long[] apariciones = new long[256];
                int caracteres = 0;
                int caracter;
                while ((caracter = lectorDeMemoria.read()) != -1) {
                    apariciones[caracter]++;
                    caracteres++;
                    System.out.println("Caracter " + ((char) caracter) + " (código entero " + caracter + ").");
                }
                //Calcula la frecuencia.
                double[] frecuencia = new double[256];
                for (int i = 0; i < 256; i++) {
                    frecuencia[i] = apariciones[i] / (double) caracteres;
                    if (frecuencia[i] != 0) {
                        System.out.println(frecuencia[i]);
                    }

                }

                //Crear Lista
                ListaOrdenada listaOriginal = creaLista(frecuencia, caracteres);
                ListaOrdenada lista = listaOriginal;
                
                //Crea Arbol de Hufmann.
                ArbolBinarioDeBusqueda arbol = (ArbolBinarioDeBusqueda) creaArbol(lista);
                
                //Asigna el código a cada caracter(hoja) del arbol.
                asignaCodigo(arbol);
                
                //Vuelve a leer el archivo y lo codifica.
                archivo = fc.getSelectedFile();
                lectorDeArchivo = new FileReader(archivo);
                lectorDeMemoria = new BufferedReader(lectorDeArchivo);
                String mensaje = "";
                caracter = 0;
                while ((caracter = lectorDeMemoria.read()) != -1) {
                    char carac = (char) caracter;
                    mensaje += codifica(carac);

                }
               // System.out.println(mensaje);
                
                //Serializa el Arbol de Hufmann.
                String arbolString = serializa(arbol);

                //Guarda el Arbol de Hufmann serializado.
                try {
                    FileOutputStream fileOut
                            = new FileOutputStream("arbolString.bin");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(arbolString);
                    out.close();
                    fileOut.close();
                    System.out.printf("Se guardó el Arbol serializable arbol.ar");
                } catch (IOException i) {
                    i.printStackTrace();
                }

                //Crea un BitSet con el mensaje codificado.
                BitSet conjuntoDeBits = new BitSet(mensaje.length());
                for (int i = 0; i < mensaje.length(); i++) {
                    if (mensaje.charAt(i) == '1') {
                        conjuntoDeBits.set(i, true);
                    } else {
                        conjuntoDeBits.set(i, false);
                    }
                }

                //Guatda el BitSet
                try {
                    FileOutputStream fileOut
                            = new FileOutputStream("archivo.bin");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(conjuntoDeBits);
                    out.close();
                    fileOut.close();
                    System.out.printf("Se guardó el mensaje serializable archivo.bin \n");
                } catch (IOException i) {
                    i.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // En el finally cerramos el fichero, para asegurarnos
                // que se cierra tanto si todo va bien como si salta
                // una excepcion.
                try {
                    if (lectorDeArchivo != null) {
                        lectorDeArchivo.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //El usuario canceló.
            JOptionPane.showMessageDialog(null, "¡No se leyó el archivo porque usted canceló!");
        }

    }

    /**
     * Crea una lista ordenada con nodos arboles que contienen un simboloHufmann 
     * con su respectivo caracter y frecuencia.
     * @param frecuencia Frecuencia de SimboloHufmann
     * @param caracteres Cantidad de Caracteres.
     * @return  ListaOrdenada
     */
    public ListaOrdenada creaLista(double[] frecuencia, int caracteres) {
        ListaOrdenada lista = new ListaOrdenada();
        for (int i = 0; i < 256; i++) {
            if (frecuencia[i] != (double) 0) {
                SimboloHufmann simb = new SimboloHufmann((char) i, (double) frecuencia[i]);
                ArbolBinarioDeBusqueda arbol = new ArbolBinarioDeBusqueda();
                arbol.asigneElemento(simb);
                lista.inserte(arbol);
            }
        }
        return lista;
    }

    /**
     * Crea el arbol Hufmann con la lista ordenada.
     * @param lista Lista Ordenada
     * @return ARbolBinarioDeBusqueda.
     */
    public ArbolBinarioDeBusqueda creaArbol(ListaOrdenada lista) {
        while (!lista.estaVacia()) {
            ArbolBinarioDeBusqueda padre = new ArbolBinarioDeBusqueda();
            ArbolBinarioDeBusqueda hijoIzquierda = new ArbolBinarioDeBusqueda();
            hijoIzquierda = (ArbolBinarioDeBusqueda) lista.obtengaNodoN(1).obtengaElemento();
            SimboloHufmann primero;
            primero = (SimboloHufmann) hijoIzquierda.obtengaElemento();
            ArbolBinarioDeBusqueda hijoDerecha = new ArbolBinarioDeBusqueda();
            hijoDerecha = (ArbolBinarioDeBusqueda) lista.obtengaNodoN(2).obtengaElemento();
            SimboloHufmann segundo;
            segundo = (SimboloHufmann) hijoIzquierda.obtengaElemento();

            SimboloHufmann aux = new SimboloHufmann('?',
                    (double) primero.obtengaFrecuencia() + segundo.obtengaFrecuencia());
            padre.asigneElemento(aux);
            lista.borre(hijoIzquierda);
            lista.borre(hijoDerecha);
            try {
                padre.inserte(hijoIzquierda, 0);
            } catch (ExcepcionArbolBinario ex) {
                Logger.getLogger(Compresor.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                padre.inserte(hijoDerecha, 1);
            } catch (ExcepcionArbolBinario ex) {
                Logger.getLogger(Compresor.class.getName()).log(Level.SEVERE, null, ex);
            }
            lista.inserte(padre);

        }
        ArbolBinarioDeBusqueda arbol = (ArbolBinarioDeBusqueda) lista.obtengaNodoN(1).obtengaElemento();
        return arbol;
    }

    /**
     * Asigna el código a cada hoja del arbol de Hufmann, es decir recorre todo 
     * el arbol y asigna el código correspondiente a cada caracter.
     * @param arbol Arbol Binario 
     */
    public void asignaCodigo(ArbolBinarioDeBusqueda arbol) {
        ArbolBinarioDeBusqueda arbolDerecho = (ArbolBinarioDeBusqueda) arbol.obtengaArbolDerecho();
        ArbolBinarioDeBusqueda arbolIzquierdo = (ArbolBinarioDeBusqueda) arbol.obtengaArbolIzquierdo();
        if (arbolDerecho != null) {
            codigo = codigo + "1";
            asignaCodigo(arbolDerecho);
            codigo = codigo.substring(0, codigo.length() - 1);

        }
        if (arbolIzquierdo != null) {
            codigo = codigo + "0";
            asignaCodigo(arbolIzquierdo);
            codigo = codigo.substring(0, codigo.length() - 1);
        }
        if (arbolDerecho == null && arbolIzquierdo == null) {
            SimboloHufmann simb = (SimboloHufmann) arbol.obtengaElemento();
            simb.asignaCodigo(codigo);
            this.listaFinal.inserte(simb);
        }

    }

    /**
     * Llama al método hallar código con la listaFinal.
     * 
     * @param elemento Caracter.
     * @return String Devuelve el código
     */
    public String codifica(char elemento) {
        return this.listaFinal.hallaCodigo(elemento);
    }

    public void listaFinal(ListaOrdenada lista) {
        this.listaFinal = lista;
    }

    /**
     * Serializa el arbol binario.
     * @param arbol ArbolBinario
     * @return String  
     */
    public String serializa(ArbolBinarioDeBusqueda arbol) {
        String arbolString = "";
        serializar(arbolString, arbol);
        return this.arbolString;
    }

    /**
     * Método Auxiliar de serializa.
     * @param arbolString Strimg
     * @param arbol ArbolBinario
     */
    public void serializar(String arbolString, ArbolBinarioDeBusqueda arbol) {
        ArbolBinario puntero = arbol;

        if (puntero.arbolDerecho != null) {
            SimboloHufmann simb = (SimboloHufmann) puntero.arbolDerecho.obtengaElemento();
            arbolString += "#D#" + Character.toString(simb.obtengaCaracter());
            this.arbolString += "#D#" + Character.toString(simb.obtengaCaracter());
            serializar(arbolString, (ArbolBinarioDeBusqueda) arbol.obtengaArbolDerecho());

        }
        if (puntero.arbolIzquierdo != null) {
            SimboloHufmann simb = (SimboloHufmann) puntero.arbolIzquierdo.obtengaElemento();
            arbolString += "#I#" + Character.toString(simb.obtengaCaracter());
            this.arbolString += "#I#" + Character.toString(simb.obtengaCaracter());
            serializar(arbolString, (ArbolBinarioDeBusqueda) arbol.obtengaArbolIzquierdo());

        }
        this.arbolString += "#F#";
    }
}
