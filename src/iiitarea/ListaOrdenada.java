/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iiitarea;

/**
 *
 * @author Luis Felipe Barquero
 */
public class ListaOrdenada {

    private Nodo cabeza = null;

    public ListaOrdenada() {
        // TODO Auto-generated constructor stub
        this.cabeza = new Nodo(null);
        this.cabeza.asigneSiguiente(null);
    }

    public Nodo obtengaNodoN(int N) {
        Nodo puntero = this.cabeza;
        for (int i = 0; i < N; i++) {
            puntero = puntero.obtengaSiguiente();
        }
        return puntero;
    }

    public void inserte(Comparable elemento) {
        Class clase;

        Nodo nuevo_nodo = new Nodo(elemento);
        Nodo puntero = this.cabeza;
        if(elemento.getClass() == ArbolBinarioDeBusqueda.class){
            
            while ((puntero.obtengaSiguiente() != null) && 
                puntero.obtengaSiguiente().obtengaElemento().compareTo((ArbolBinarioDeBusqueda)elemento) < 0){
                puntero = puntero.obtengaSiguiente();
            }
        }
        else if (elemento.getClass() == SimboloHufmann.class){
        while ((puntero.obtengaSiguiente() != null) && 
                puntero.obtengaSiguiente().obtengaElemento().compareTo((SimboloHufmann)elemento) < 0){
                puntero = puntero.obtengaSiguiente();
            }
        }

      
        
        nuevo_nodo.asigneSiguiente(puntero.obtengaSiguiente());
        puntero.asigneSiguiente(nuevo_nodo);
    }

    public boolean miembro(Comparable elemento) {
        Nodo puntero = this.cabeza.obtengaSiguiente();

        while (puntero != null) {
            if (puntero.obtengaElemento().compareTo(elemento) == 0) {
                return true;
            }
        }
        return false;
    }

    public void limpie() {
        Nodo puntero = this.cabeza.obtengaSiguiente();

        while (puntero != null) {
            Nodo siguiente = puntero.obtengaSiguiente();
            puntero = null;
            puntero = siguiente;
        }
        this.cabeza.asigneSiguiente(null);
    }

    public void borre(Comparable elemento) {
        // OJO:  Este m?todo debe ser reescrito...
        Nodo puntero = this.cabeza;

        while ((puntero.obtengaSiguiente() != null) && (puntero.obtengaSiguiente().obtengaElemento().compareTo(elemento) != 0)) {
            puntero = puntero.obtengaSiguiente();
        }
        if ((puntero.obtengaSiguiente() != null) && (puntero.obtengaSiguiente().obtengaElemento().compareTo(elemento) == 0)) {
            Nodo borrar = puntero.obtengaSiguiente();
            puntero.asigneSiguiente(puntero.obtengaSiguiente().obtengaSiguiente());
            borrar = null;
        }
    }

    public void imprima() {
        Nodo puntero = this.cabeza.obtengaSiguiente();

        while (puntero != null) {
            System.out.print(puntero.obtengaElemento() + " ");
            puntero = puntero.obtengaSiguiente();
        }
    }

    public boolean estaVacia() {
        Nodo puntero = this.cabeza;
        return (puntero.obtengaSiguiente().obtengaSiguiente() == null);

    }
    
    /**
     * Halla el código para un caracter dado. Devuelve null si no halla el caracter.
     * @param elemento Caracter
     * @return String
     */
    public String hallaCodigo(char elemento) {
        Nodo puntero = this.cabeza.obtengaSiguiente();

        while (puntero != null) {
            SimboloHufmann simb = (SimboloHufmann) puntero.obtengaElemento();
            char caracter = simb.obtengaCaracter();
            if (caracter == elemento) {
                String codigo = simb.obtengaCodigo();
         
                return codigo;
            }
            puntero = puntero.obtengaSiguiente();
        }
        return null;
    }
}

