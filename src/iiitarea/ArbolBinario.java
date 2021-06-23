/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iiitarea;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

/**
 *
 * @author Luis Felipe Barquero
 */
public class ArbolBinario implements Comparable<ArbolBinario>{

    // Base de la estructura de ?rbol binario.

    protected Comparable elemento = null;
    protected ArbolBinario padre = null;
    protected ArbolBinario arbolIzquierdo = null;
    protected ArbolBinario arbolDerecho = null;

    // Constantes para representar el lado (escoger un sub?rbol).
    final public static int IZQUIERDA = 0;
    final public static int DERECHA = 1;

    // Constantes para escoger un tipo de recorrido.
    final public static int PREORDEN = 0;
    final public static int ENORDEN = 1;
    final public static int POSTORDEN = 2;

    /**
     *
     */
    public ArbolBinario() {
    }

    /**
     * @param elemento Elemento a asigna.
     */
    public ArbolBinario(Comparable elemento) {
        this.elemento = elemento;
    }

    public ArbolBinario obtengaPadre() {
        return this.padre;
    }

    public Comparable obtengaElemento() {
        return this.elemento;
    }

    
    public void asigneElemento(Comparable elemento) {
        this.elemento = elemento;
    }

    
    public ArbolBinario obtengaArbolIzquierdo() {
        return this.arbolIzquierdo;
    }

    
    public ArbolBinario obtengaArbolDerecho() {
        return this.arbolDerecho;
    }

    public void inserte(ArbolBinario arbol, int lado) throws ExcepcionArbolBinario {
        switch (lado) {
            case ArbolBinario.IZQUIERDA:
                if (this.arbolIzquierdo != null) {
                    throw new ExcepcionArbolBinario("Insert? donde ya hab?a rama.");
                }
                this.arbolIzquierdo = arbol;
                break;
            case ArbolBinario.DERECHA:
                if (this.arbolDerecho != null) {
                    throw new ExcepcionArbolBinario("Insert? donde ya hab?a rama.");
                }
                this.arbolDerecho = arbol;
                break;
        }
    }

    public void inserte2(ArbolBinario arbol, int lado) {
        switch (lado) {
            case ArbolBinario.IZQUIERDA:
                this.arbolIzquierdo = arbol;
                break;
            case ArbolBinario.DERECHA:
                this.arbolDerecho = arbol;
                break;
        }
    }

    public void insertePadre(ArbolBinario arbol, int lado) throws ExcepcionArbolBinario {
        switch (lado) {
            case ArbolBinario.IZQUIERDA:
                if (this.padre.arbolIzquierdo != null) {
                    throw new ExcepcionArbolBinario("Insert? donde ya hab?a rama.");
                }
                this.padre.arbolIzquierdo = arbol;
                break;
            case ArbolBinario.DERECHA:
                if (this.padre.arbolDerecho != null) {
                    throw new ExcepcionArbolBinario("Insert? donde ya hab?a rama.");
                }
                this.padre.arbolDerecho = arbol;
                break;
        }
    }

    /**
     *
     */
    public void imprima() {
        this.imprima(ArbolBinario.ENORDEN);
    }

    public void imprima(int orden) {
        switch (orden) {
            case ArbolBinario.PREORDEN:
                System.out.print(elemento + " ");
                if (arbolDerecho != null) {
                    arbolDerecho.imprima(orden);
                }
                if (arbolIzquierdo != null) {
                    arbolIzquierdo.imprima(orden);
                }
                break;
            case ArbolBinario.ENORDEN:
                if (arbolDerecho != null) {
                    arbolDerecho.imprima(orden);
                }
                System.out.print(elemento + " ");
                if (arbolIzquierdo != null) {
                    arbolIzquierdo.imprima(orden);
                }
                break;
            case ArbolBinario.POSTORDEN:
                if (arbolDerecho != null) {
                    arbolDerecho.imprima(orden);
                }
                if (arbolIzquierdo != null) {
                    arbolIzquierdo.imprima(orden);
                }
                System.out.print(elemento + " ");
                break;
            default:
                System.out.print(elemento + " ");
                if (arbolIzquierdo != null) {
                    arbolIzquierdo.imprima(orden);
                }
                if (arbolDerecho != null) {
                    arbolDerecho.imprima(orden);
                }
        }
    }

   
    public void pode(int lado) {
        switch (lado) {
            case ArbolBinario.IZQUIERDA:
                //return this.arbolIzquierdo;
                this.arbolIzquierdo = null;
                break;
            case ArbolBinario.DERECHA:
                this.arbolDerecho = null;
                break;
        }

    }
    /**
     * Remplaza el Nodo por el hijo a la derecha con el menor valor. 
     * @param Raiz Raiz del arbol.
     * @param Padre Padre del arbol.
     * @param lado  Lado del cual proviene.
     */
       public void RemplazaMin(ArbolBinarioDeBusqueda Raiz, ArbolBinarioDeBusqueda Padre, int lado){
     ArbolBinarioDeBusqueda Aux = (ArbolBinarioDeBusqueda) this;
        if(Aux.arbolIzquierdo == null){
         Comparable aux = this.obtengaElemento();
         Padre.inserte2(this.obtengaArbolDerecho(), lado);
         Raiz.asigneElemento(aux);
     }
     else {
         this.obtengaArbolIzquierdo().RemplazaMin(Raiz, (ArbolBinarioDeBusqueda) this, lado);
     }
    }

    /**
     * Método para comparar dos Arboles Binarios. Implementado de Comparable.java.
     * @param t ArbolBinario a comparar.
     * @return int
     */
    @Override
    public int compareTo(ArbolBinario t) {
      SimboloHufmann elemento = (SimboloHufmann)this.elemento;
      
      SimboloHufmann elemento_t = (SimboloHufmann)t.elemento;
      double frec = elemento.obtengaFrecuencia();
      double frec_t = elemento_t.obtengaFrecuencia();
      return (frec - frec_t > 0) ? 1 : (frec - frec_t == 0)? 0 : -1  ;     
    }
}