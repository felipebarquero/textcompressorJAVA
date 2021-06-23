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
public class Nodo {
   private Comparable elemento;
    private Nodo siguiente=null;
    
    public Nodo(Comparable elemento)
    {
        this.elemento = elemento;
    }
    
    public Nodo obtengaSiguiente()
    {
        return this.siguiente;
    }
    
    public void asigneSiguiente(Nodo siguiente)
    {
        this.siguiente = siguiente;
    }
    
    public Comparable obtengaElemento()
    {
        return this.elemento;
    }
    
    public void asigneElemento(Comparable elemento)
    {
        this.elemento = elemento;
    }  
}
