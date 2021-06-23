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
public class SimboloHufmann implements Comparable<SimboloHufmann>{
 
    private char Simbolo;
    private Double Frecuencia;
    private String codigo;
    /**
     * Constructor de SimboloHufmann.
     * @param Simbolo Simbolo (char) del SimboloHufmann
     * @param Frecuencia Frecuencia (Double) del SimboloHufmann 
     */
    public SimboloHufmann(char Simbolo, Double Frecuencia){
    this.Simbolo = Simbolo;
    this.Frecuencia = Frecuencia;         
    }
    
    /**
     * Devuelve la frecuencia de un SimboloHufmann. 
     * @return Double
     */
    public double obtengaFrecuencia(){
        return this.Frecuencia;
    
    }
    
    /**
     * Le asigna un código al SimboloHufmann.
     * @param codigo String 
     */
    public void asignaCodigo(String codigo){
    this.codigo = codigo;
    
    }
    
    /**
     * Obtiene el código de un SimboloHufmann.
     * @return String 
     */
    public String obtengaCodigo(){
    return this.codigo;
    
    }
    /**
     * Obtiene el caracter de un SimboloHufmann.
     * @return char 
     */
    public char obtengaCaracter(){
    return this.Simbolo;
    
    }
  
    /**
     * Método para comparar dos SimboloHufmann.
     * @param t SimboloHufmann a comparar
     * @return int Entero 
     */
    public int compareTo(SimboloHufmann t) {
    return (this.Frecuencia - t.Frecuencia > 0)? 1 : (this.Frecuencia - t.Frecuencia == 0)? 0 : -1 ; 
    }
    
    
}
