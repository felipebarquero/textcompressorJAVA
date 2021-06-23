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
public class ArbolBinarioDeBusqueda extends ArbolBinario {

    public ArbolBinarioDeBusqueda() {
    }

    public ArbolBinarioDeBusqueda(Comparable elemento) {
        super(elemento);
    }

    public ArbolBinarioDeBusqueda(Comparable[] elementos) {
        super(elementos[0]);
        for (int i = 1; i < elementos.length; ++i) {
            this.inserte(elementos[i]);
        }
    }

    public Boolean busque(Comparable elemento) {
        Comparable elemento_nodo = this.obtengaElemento();
        if (elemento_nodo == null) {
            return false;
        } else if (elemento_nodo.compareTo(elemento) > 0) {
            ArbolBinarioDeBusqueda arbolDerecho = (ArbolBinarioDeBusqueda) this.obtengaArbolDerecho();
            if (arbolDerecho == null) {
                return false;
            }
            return arbolDerecho.busque(elemento);
        } else if (elemento_nodo.compareTo(elemento) < 0) {
            ArbolBinarioDeBusqueda arbolIzquierdo = (ArbolBinarioDeBusqueda) this.obtengaArbolIzquierdo();
            if (arbolIzquierdo == null) {
                return false;
            }
            return arbolIzquierdo.busque(elemento);
        } else {
            return true;
        }
    }

    public void inserte(Comparable elemento) {
        Comparable elemento_nodo = this.obtengaElemento();
        if (elemento_nodo == null) {
            this.asigneElemento(elemento);
        } else if (elemento_nodo.compareTo(elemento) > 0) {
            ArbolBinarioDeBusqueda arbolDerecho = (ArbolBinarioDeBusqueda) this.obtengaArbolDerecho();
            if (arbolDerecho == null) {
                arbolDerecho = new ArbolBinarioDeBusqueda();
                try {
                    this.inserte((ArbolBinario) arbolDerecho, ArbolBinario.DERECHA);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            arbolDerecho.inserte(elemento);
        } else if (elemento_nodo.compareTo(elemento) <= 0) {
            ArbolBinarioDeBusqueda arbolIzquierdo = (ArbolBinarioDeBusqueda) this.obtengaArbolIzquierdo();
            if (arbolIzquierdo == null) {
                arbolIzquierdo = new ArbolBinarioDeBusqueda();
                try {
                    this.inserte((ArbolBinario) arbolIzquierdo, ArbolBinario.IZQUIERDA);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            arbolIzquierdo.inserte(elemento);
        }
    }

    /* Tarea:  complete este método. */
    public void borre(Comparable elemento) {

        this.Borrar(elemento, this, 0);
    }

    /**
     * Método Borrar, halla el valor que se desea borrar y luego le asigna el
     * nodo siguiente.
     *
     * @param elemento Elemento a borrar
     * @param Padre Padre del arbol.
     * @param lado Lado del cual proviene.
     */
    public void Borrar(Comparable elemento, ArbolBinarioDeBusqueda Padre, int lado) {
        Comparable elemento_nodo = this.obtengaElemento();
        if (elemento_nodo == null) {
            return;
        } else if (elemento_nodo.compareTo(elemento) > 0) {
            ArbolBinarioDeBusqueda arbolDerecho = (ArbolBinarioDeBusqueda) this.obtengaArbolDerecho();
            if (arbolDerecho == null) {
                return;
            }
            arbolDerecho.Borrar(elemento, this, DERECHA);
        } else if (elemento_nodo.compareTo(elemento) < 0) {
            ArbolBinarioDeBusqueda arbolIzquierdo = (ArbolBinarioDeBusqueda) this.obtengaArbolIzquierdo();
            if (arbolIzquierdo == null) {
                return;
            }
            arbolIzquierdo.Borrar(elemento, this, IZQUIERDA);
        } else {

            if (this.obtengaArbolIzquierdo() == null) {
                Padre.inserte2(this.obtengaArbolDerecho(), lado);
            }

            if (this.obtengaArbolDerecho() == null) {
                Padre.inserte2(this.obtengaArbolIzquierdo(), lado);
            }

            if (this.obtengaArbolIzquierdo() != null && this.obtengaArbolDerecho() != null) {
                this.obtengaArbolDerecho().RemplazaMin(this, this, lado);

            }

        }
    }
    /**
     * Devuelve si es o no una hoja.
     * @return boolean Devuelve un booleano. 
     */
    public boolean esHoja(){
    return (this.arbolDerecho == null && this.arbolIzquierdo == null); 
    }
    
     

}
