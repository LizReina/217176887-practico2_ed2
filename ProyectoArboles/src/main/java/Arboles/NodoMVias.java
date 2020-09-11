/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author brandon
 */
public class NodoMVias<K,V> {
   private List<K> listaDeClaves;
   private List<V> listaDeValores;
   private List<NodoMVias<K,V>> listaDeHijos;
   
     public NodoMVias(int orden) {
       listaDeClaves=new LinkedList<>();
       listaDeValores=new LinkedList<>();
       listaDeHijos=new LinkedList<>();
        for (int i = 0; i < orden-1; i++) {
            listaDeClaves.add((K)NodoMVias.datoVacio());
            listaDeValores.add((V)NodoMVias.datoVacio());
            listaDeHijos.add(NodoMVias.nodoVacio());      
        }
       listaDeHijos.add(NodoMVias.nodoVacio());
    }

    public NodoMVias(int orden,K primerClave, V PrimerValor) {
        this(orden);
       listaDeClaves.set(0, primerClave);
       listaDeValores.set(0, PrimerValor);
    }
// retorna la clave de la posicion indicada
// pre:el valor del parametro posicion esta dentro del rango 
    public K getClave(int posicion) {
        return this.listaDeClaves.get(posicion);
    }

    public V getValor(int posicion) {
        return this.listaDeValores.get(posicion);
    }


    public NodoMVias<K,V>  getHijo(int posicion) {
        return this.listaDeHijos.get(posicion);
    }

    public void setClave(int posicion,K clave) {
        this.listaDeClaves.set(posicion, clave);
    }

    public void setValor(int posicion,V valor) {
        this.listaDeValores.set(posicion, valor);
    }

    public void setHijo(int posicion,NodoMVias<K,V> dato) {
        this.listaDeHijos.set(posicion, dato);
    }

     public static boolean esNodoVacio(NodoMVias nodo){
        return nodo==NodoMVias.nodoVacio();
    }
   
    public  boolean esDatoVacio(int posicion){
        return this.listaDeClaves.get(posicion) ==NodoMVias.datoVacio();
   }
    
    public  boolean esHijoVacio(int posicion){
        return this.listaDeHijos.get(posicion) ==NodoMVias.nodoVacio();
   }

   public static NodoMVias nodoVacio(){
       return null;
   }
   
      public static Object datoVacio(){
       return null;
   }
      
     public boolean esHoja(){
         for (int i = 0; i < this.listaDeHijos.size(); i++) {
            if(!esHijoVacio(i)){
               return false; 
            } 
         }
       return true;
   }
   
 public boolean estanDatosLlenos(){
     for (K unaClave : listaDeClaves) {
        if(unaClave==NodoMVias.datoVacio()){
            return false;
        } 
     }
     
   return true;
 }
      
 
 public boolean estanDatosVacios(){
     for (K unaClave : listaDeClaves) {
        if(unaClave!=NodoMVias.datoVacio()){
            return false;
        } 
     }
     
   return true;
 }  
 
public int cantidadDeDatosNoVacios(){
    int cantidad=0;
    for (int i = 0; i < this.listaDeClaves.size(); i++) {
        if(!esDatoVacio(i)){
            cantidad++;
        }
            
    }
   return cantidad;
}
   

public int cantidadDeHijosVacios(){
    int cantidad=0;
    for (int i = 0; i < this.listaDeHijos.size(); i++) {
        if(esHijoVacio(i)){
            cantidad++;
        }
            
    }
   return cantidad;
}


public int cantidadDeHijosNoVacios(){
    int cantidad=0;
    for (int i = 0; i < this.listaDeHijos.size(); i++) {
        if(!esHijoVacio(i)){
            cantidad++;
        }
            
    }
   return cantidad;
}

public List<K> getListaDeClaves() {
        return listaDeClaves;
    }
    
    
    
    @Override
    public String toString() {
        return "Claves:"+listaDeClaves.toString() + "\n"
                +"Hijos:"+listaDeHijos.toString();
    }
}
