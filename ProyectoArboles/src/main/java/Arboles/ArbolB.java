/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

import java.util.Stack;

/**
 *
 * @author brandon
 * @param <K>
 * @param <V>
 */
public class ArbolB<K extends Comparable<K>,V>extends ArbolMViasBusqueda<K,V>{
  private int nroMaximoDeDatos;
  private int nroMinimoDeDatos;
  private int nroMinimoDeHijos;
    
 public ArbolB(){
    nroMaximoDeDatos= 2;
    nroMinimoDeDatos=1;
    nroMinimoDeHijos=2;
 } 
 
 public ArbolB(int orden)throws ExceptionOrdenInvalido{
   super(orden);
   this.nroMaximoDeDatos=super.orden-1; 
   this.nroMinimoDeDatos=this.nroMaximoDeDatos/2;
   this.nroMinimoDeHijos=this.nroMinimoDeDatos+1;
 }
 
  @Override
    public void insertar(K clave, V valor) throws ExceptionClaveYaExiste {
        if(esArbolVacio()){
          this.raiz=new NodoMVias<>(orden+1,clave,valor);
        }
        
        Stack<NodoMVias<K,V>> pilaAncestros=new Stack<>();
      NodoMVias<K,V> nodoActual=this.raiz;
      while(NodoMVias.esNodoVacio(nodoActual)){
      if(this.existeClaveEnElNodo(nodoActual,clave)){
              throw new ExceptionClaveYaExiste();
          }
      if(nodoActual.esHoja()){
          super.insertarOrden(nodoActual,clave, valor);
          if(nodoActual.cantidadDeDatosNoVacios()>this.nroMaximoDeDatos){
              this.dividir(nodoActual,pilaAncestros);
          }
          nodoActual=NodoMVias.nodoVacio();
      }else{
       int posicionDeDondeBajar=this.porDondeBajar(nodoActual,clave);  
       pilaAncestros.push(nodoActual);
       nodoActual=nodoActual.getHijo(posicionDeDondeBajar);
       }
      }//fin While
}
    
    public V eliminar(K claveAEliminar) throws ExceptionClaveNoExiste{
       Stack<NodoMVias<K,V>> pilaAncestros=new Stack<>();
        NodoMVias<K,V> nodoActual=buscarNodoDeLaClave(claveAEliminar,pilaAncestros);
            if(NodoMVias.esNodoVacio(nodoActual)){
              throw new  ExceptionClaveNoExiste();
            }
            
        int posicionClaveEnElNodo=this.porDondeBajar(nodoActual, claveAEliminar)-1;
        V valorARetornar=nodoActual.getValor(posicionClaveEnElNodo);
           
        if(nodoActual.esHoja()){
             this.eliminarDatoDelNodo(nodoActual,posicionClaveEnElNodo); 
             if(nodoActual.cantidadDeDatosNoVacios()>this.nroMinimoDeDatos){
                 if(pilaAncestros.isEmpty()){
                     if(nodoActual.estanDatosLlenos()){
                      super.vaciar();
                     }else{
                         this.prestarOFusionar(nodoActual,pilaAncestros);
                     }
                 }
             }
            }else{//sino es hoja
            pilaAncestros.push(nodoActual);
            NodoMVias<K,V> nodoDelPredecesor=BuscarPredesesor(pilaAncestros,
                    nodoActual.getHijo(posicionClaveEnElNodo));
            int posicionDelPrdecesor=nodoDelPredecesor.cantidadDeDatosNoVacios()-1;
            K clavePredecesora=nodoDelPredecesor.getClave(posicionDelPrdecesor);
            V ValorPredecesor=nodoDelPredecesor.getValor(posicionDelPrdecesor);
            super.eliminarDatoDelNodo(nodoDelPredecesor,posicionDelPrdecesor);
            nodoActual.setClave(posicionDelPrdecesor, clavePredecesora);
            nodoActual.setValor(posicionDelPrdecesor, ValorPredecesor);
            if(nodoDelPredecesor.cantidadDeDatosNoVacios()< this.nroMinimoDeDatos){
                this.prestarOFusionar(nodoDelPredecesor,pilaAncestros);
            }
        } 
        
        return valorARetornar;
    }
    
 
    private NodoMVias<K,V> buscarNodoDeLaClave(K claveAEliminar,Stack<NodoMVias<K,V>> pilaAncestros){
    NodoMVias<K,V> nodoActual=this.raiz;
    while(!NodoMVias.esNodoVacio(nodoActual)){
        int tamanoDeNodoActual=nodoActual.cantidadDeDatosNoVacios();
        NodoMVias<K,V> anterior=nodoActual;
        for (int i = 0; i < tamanoDeNodoActual && anterior==nodoActual; i++) {
           K claveactual=nodoActual.getClave(i);
           if(claveAEliminar.compareTo(claveactual)==0){
             return nodoActual;
                     
           }
           
           if(claveAEliminar.compareTo(claveactual)<0){
               if(nodoActual.esHoja()){
                    pilaAncestros.push(nodoActual);
                 nodoActual=nodoActual.getHijo(i);
               }else{
                   return NodoMVias.nodoVacio();
               }
           }
        }//fin for
         if (anterior==nodoActual){
               if(nodoActual.esHoja()){
                    pilaAncestros.push(nodoActual);
                 nodoActual=nodoActual.getHijo(tamanoDeNodoActual);
               }else{
                   return NodoMVias.nodoVacio();
               }
         }
        
    }//fin while
    
   return NodoMVias.nodoVacio();
    }
    

private void dividir(NodoMVias<K,V> nodoActual, Stack<NodoMVias<K,V>> pilaDePadres) {
        while (true) {
            int posicionQSube = this.nroMaximoDeDatos / 2;
            K claveQSube = nodoActual.getClave(posicionQSube);
             V valorQSube = nodoActual.getValor(posicionQSube);
            NodoMVias<K,V> nodoIzq = new NodoMVias<>(orden + 1);
            NodoMVias<K,V> nodoDer = new NodoMVias<>(orden + 1);
            for (int i = 0; i < posicionQSube; i++) {
                insertarOrden(nodoIzq, nodoActual.getClave(i), nodoActual.getValor(i));
                nodoIzq.setHijo(i, nodoActual.getHijo(i));
                if (i == posicionQSube - 1) {
                    nodoIzq.setHijo(i + 1, nodoActual.getHijo(i + 1));
                }
            }
            for (int i = posicionQSube + 1; i < this.orden; i++) {
                insertarOrden(nodoDer, nodoActual.getClave(i), nodoActual.getValor(i));
                nodoDer.setHijo(i - (posicionQSube + 1), nodoActual.getHijo(i));
                if (i == this.orden - 1) {
                    nodoDer.setHijo(i - posicionQSube, nodoActual.getHijo(i + 1));
                }
            }
            if (pilaDePadres.isEmpty()) {
                vaciarNodo1(nodoActual);
                insertarOrden(nodoActual, claveQSube,valorQSube);
                nodoActual.setHijo(0, nodoIzq);
                nodoActual.setHijo(1, nodoDer);
                break;
            } else {
                NodoMVias<K,V> nodoPadre = pilaDePadres.pop();
                insertarOrden(nodoPadre, claveQSube,valorQSube);
                nodoPadre.setHijo(porDondeBajar(nodoPadre, claveQSube), nodoIzq);
                nodoPadre.setHijo(porDondeBajar(nodoPadre, claveQSube) + 1, nodoDer);
                if (!nodoPadre.estanDatosLlenos()) {
                    break;
                } else {
                    nodoActual = nodoPadre;
                }
            }
        }
    }

     public void vaciarNodo1(NodoMVias<K,V> nodo) {
        for (int i = 0; i < orden; i++) {
            nodo.setClave(i, null);
            nodo.setValor(i, null);
        }
    }
      //7 Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol B, pero solo en el nivel N 
         public int cantidadNodosConDatosVaciosDelNivel(int nivelABuscar){
             return cantidadNodosConDatosVaciosDelNivel(this.raiz,nivelABuscar,0);
         }
    
       private int cantidadNodosConDatosVaciosDelNivel(NodoMVias<K,V> nodoActual,int nivelABuscar,int nivel){
           if(NodoMVias.esNodoVacio(nodoActual)){
               return 0;
           }
          
           if(nivel==nivelABuscar){
               if(!nodoActual.estanDatosLlenos()){
                   return 1;
               }
           }
           
           int cantidad=0;
           for (int i = 0; i < orden; i++) {
               cantidad=cantidad+cantidadNodosConDatosVaciosDelNivel(nodoActual.getHijo(i), nivelABuscar, nivel+1);
           }
           
           return cantidad;
       }
}
    
  
    
    

