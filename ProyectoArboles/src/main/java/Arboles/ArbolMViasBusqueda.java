/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author brandon
 * @param <K>
 * @param <V>
 */
public  class ArbolMViasBusqueda<K extends Comparable<K>,V>
        implements IArbolBusqueda<K,V> {
 protected NodoMVias<K,V> raiz;
 protected int orden;

    public ArbolMViasBusqueda() {
        this.orden=3;
    }
 

    public ArbolMViasBusqueda(int orden) throws ExceptionOrdenInvalido {
        if(orden<3){
           throw new ExceptionOrdenInvalido();
        }
        this.orden = orden;
    }
 
   public NodoMVias<K,V> nodoVacioParaElArbol(){
       return (NodoMVias<K,V>)NodoMVias.nodoVacio(); 
    }
   
  
   
   
    @Override
    public void insertar(K clave, V valor) throws ExceptionClaveYaExiste {
        if(esArbolVacio()){
          this.raiz=new NodoMVias<>(orden,clave,valor);
          return ;
        }
      NodoMVias<K,V> nodoActual=this.raiz;
      while(!NodoMVias.esNodoVacio(nodoActual)){
          if(this.existeClaveEnElNodo(nodoActual,clave)){
              throw new ExceptionClaveYaExiste();
          }
          //si llegamos a este punto la clave no esta en el nodo  
         if(nodoActual.esHoja()){
             if(nodoActual.estanDatosLlenos()){
             //no hay campo para clave enn este campo
                    int posicionDeDondeBajar=this.porDondeBajar(nodoActual,clave);
                    NodoMVias<K,V> nuevoHijo=new NodoMVias<>(orden,clave,valor); 
                    nodoActual.setHijo(posicionDeDondeBajar,nuevoHijo);
               }else{
              // hay campo para clace_valor en este campo   
              this.insertarOrden(nodoActual,clave,valor);
               }
             nodoActual=NodoMVias.nodoVacio();
         }else{
             int posicionDeDondeBajar=this.porDondeBajar(nodoActual,clave);  
             if(nodoActual.esHijoVacio(posicionDeDondeBajar)){
                NodoMVias<K,V> nuevoHijo=new NodoMVias<>(orden,clave,valor); 
                nodoActual.setHijo(posicionDeDondeBajar,nuevoHijo); 
                nodoActual=NodoMVias.nodoVacio();
             }else{
                nodoActual=nodoActual.getHijo(posicionDeDondeBajar);
             }
         }
          
      }
      
    }
  public int porDondeBajar(NodoMVias<K,V> nodoActual,K clave){
    int cantidadDatos=nodoActual.cantidadDeDatosNoVacios();
        int i=0;
        while(i<cantidadDatos){
            if(nodoActual.getClave(i).compareTo(clave)>=0){
                return i;
            }
            
          i++;
        }
        
        if(!nodoActual.estanDatosLlenos()){
            return i;
        }
        return orden-1;
  }
  
   public void insertarOrden(NodoMVias<K,V> nodoActual,K clave,V valor){  
     int  posicionAPoner=porDondeBajar(nodoActual, clave);
       for (int i = nodoActual.cantidadDeDatosNoVacios()-1; i >= posicionAPoner ; i--) {
          K claveEnTurno=nodoActual.getClave(i);
           if(claveEnTurno.compareTo(clave)>0){
               nodoActual.setClave(i+1, nodoActual.getClave(i));
               nodoActual.setValor(i+1, nodoActual.getValor(i));
               nodoActual.setHijo(i+1, nodoActual.getHijo(i));
           }
       }
               nodoActual.setClave(posicionAPoner,clave);
               nodoActual.setValor(posicionAPoner,valor);
               nodoActual.setHijo(posicionAPoner,NodoMVias.nodoVacio());
       
   }


    protected boolean existeClaveEnElNodo(NodoMVias<K,V> nodoActual,K claveABuscar){
        for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
            if(!nodoActual.esDatoVacio(i)){
                K claveEnTurno=nodoActual.getClave(i);
                if(claveEnTurno.compareTo(claveABuscar)==0){
                    return true;
                }
                }
            }
        return false;
    }
    
    @Override
    public V eliminar(K clave) throws ExceptionClaveNoExiste {
       V valorRetornar= buscar(clave);
       if(valorRetornar==null){
           throw new ExceptionClaveNoExiste();
       }
       this.raiz=eliminar(this.raiz,clave);
       return valorRetornar;
    }
    
    private NodoMVias<K,V> eliminar(NodoMVias<K,V> nodoActual,K claveAEliminar)throws ExceptionClaveNoExiste{
        for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
           K claveActual=nodoActual.getClave(i);
           if(claveAEliminar.compareTo(claveActual)==0){
            //caso 1   
               if(nodoActual.esHoja()){
                  this.eliminarDatoDelNodo(nodoActual, i);
                  if(nodoActual.estanDatosVacios()){
                      return NodoMVias.nodoVacio();
                  }
                  return nodoActual;
               }
              K claveDeReemplazo;
             //caso II
              if(hayHijoMasAdelante(nodoActual,i)){
                 claveDeReemplazo= this.buscarSucessorInOrden(nodoActual,claveAEliminar);
                
              }else{
                 claveDeReemplazo= this.buscarPredecessorInOrden(nodoActual,claveAEliminar);
              }
              V valorDeReemplazo=buscar(claveDeReemplazo);
              
              nodoActual=eliminar(nodoActual, claveDeReemplazo);
              nodoActual.setClave(i, claveDeReemplazo);
              nodoActual.setValor(i, valorDeReemplazo);
              
              return nodoActual;
           }
           
             if(claveAEliminar.compareTo(claveActual)<0){
               NodoMVias<K,V> supuestoNuevoHijo=eliminar(nodoActual.getHijo(i), claveAEliminar);
             nodoActual.setHijo(i,supuestoNuevoHijo);
             return nodoActual;
       
           }
             
             
        }    
        
     NodoMVias<K,V> supuestoNuevoHijo=eliminar(nodoActual.getHijo(orden-1), claveAEliminar);
     nodoActual.setHijo(orden-1,supuestoNuevoHijo);
      return nodoActual;
    }

    public void eliminarDatoDelNodo(NodoMVias<K,V> nodoActual,int posicion){
         nodoActual.setClave(posicion,null);
         nodoActual.setValor(posicion,null);
         
        for (int j = posicion; j < orden - 2; j++) {
            if (nodoActual.esDatoVacio(j + 1)) {
                return;
            }
            nodoActual.setClave(j,nodoActual.getClave(j+1));
            nodoActual.setValor(j,nodoActual.getValor(j+1));
            
            
            nodoActual.setClave(j+1,null);
            nodoActual.setValor(j+1,null);
        }
       
    }
    
   public boolean  hayHijoMasAdelante(NodoMVias<K,V> nodoActual,int posicion){
       if(nodoActual.esHijoVacio(posicion+1) && posicion < this.orden){
           return false;
       }
     return true;
   }
   
   public K buscarSucessorInOrden(NodoMVias<K,V> nodoActual,K clave){
      List<K> recorridoInOrden=new LinkedList<>();
       recorridoInOrdenRecursivo(nodoActual, recorridoInOrden);
       int posicionInOrden=0;
       boolean existe=false;
       for (int i = 0; i < recorridoInOrden.size() && existe== false; i++) {
           if(recorridoInOrden.get(i).compareTo(clave)==0){
               posicionInOrden=i;
               existe=true;
           }
       }
       return recorridoInOrden.get(posicionInOrden+1);
   }
        
     public K buscarPredecessorInOrden(NodoMVias<K,V> nodoActual,K clave){
      List<K> recorridoInOrden=new LinkedList<>();
       recorridoInOrdenRecursivo(nodoActual, recorridoInOrden);
       int posicionInOrden=0;
       boolean existe=false;
       for (int i = 0; i < recorridoInOrden.size() && existe== false; i++) {
           if(recorridoInOrden.get(i).compareTo(clave)==0){
               posicionInOrden=i;
               existe=true;
           }
       }
       return recorridoInOrden.get(posicionInOrden-1);
   }
   
    
    @Override
    public V buscar(K claveABuscar) {
        NodoMVias<K,V> nodoActual=this.raiz;
        while(!NodoMVias.esNodoVacio(nodoActual)){
            NodoMVias<K,V> nodoAnterior=nodoActual;
            for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios()&&
                 nodoAnterior==nodoActual   ; i++) {
                K claveActual=nodoActual.getClave(i);
                 if(claveABuscar.compareTo(claveActual)==0){
                    return nodoActual.getValor(i);
                 }    
                 
                 if(claveABuscar.compareTo(claveActual)<0){
                     if(nodoActual.esHijoVacio(i)){
                         return (V)NodoMVias.datoVacio();
                     }
                   nodoActual=nodoActual.getHijo(i);
                }
            }
          if(nodoAnterior==nodoActual) { 
          nodoActual=nodoActual.getHijo(orden-1);
          }
        
        }
      return (V)nodoActual.datoVacio();
    }

    @Override
    public boolean contiene(K clave) {
          return buscar(clave)!=NodoMVias.datoVacio();
    }

    @Override
    public int size() {
      return size(this.raiz);  
    }

    private int size(NodoMVias<K,V> nodoActual){
        if(NodoMVias.esNodoVacio(nodoActual)){
            return 0;
        }
        
          int cantidad=0;
        for (int i = 0; i < orden-1; i++) {
            cantidad=cantidad+size(nodoActual.getHijo(i));
        }
     cantidad=cantidad+1;
     
     return cantidad;
    }
    
    @Override
    public int altura() {
        return altura(this.raiz);
    }

    private int altura(NodoMVias<K,V> nodoActual){
     if(NodoMVias.esNodoVacio(nodoActual)){
         return 0;
     }    
        int alturaMayor=0;
        for (int i = 0; i < orden; i++) {
           int alturaDeHijo=altura(nodoActual.getHijo(i));
           if(alturaDeHijo>alturaMayor);{
            alturaMayor=alturaDeHijo;
        }
        }
     return alturaMayor+1;
    }
    
     
   

    @Override
    public void vaciar() {
           this.raiz=(NodoMVias<K,V>)NodoMVias.nodoVacio();
    }

    @Override
    public boolean esArbolVacio() {
         return NodoMVias.esNodoVacio(this.raiz);
    }

    @Override
    public int nivel() {
      return  this.altura() - 1;
    }


    @Override
    public List<K> recorridoInOrdenRecursivo() {
           List<K> recorrido=new LinkedList<>();
        recorridoInOrdenRecursivo(this.raiz,recorrido);
        return recorrido;  
      }  
      
      public void recorridoInOrdenRecursivo(NodoMVias<K,V> nodoActual,List<K> recorrido){
          if (NodoMVias.esNodoVacio(nodoActual)){
             return ; 
          }
          for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) { 
               recorridoInOrdenRecursivo(nodoActual.getHijo(i), recorrido);
                recorrido.add(nodoActual.getClave(i));
          }
          recorridoInOrdenRecursivo(nodoActual.getHijo(orden-1), recorrido);
      }

    @Override
    public List<K> recorridoPreOrdenRecursivo() {
       List<K> recorrido=new LinkedList<>();
        recorridoPreOrdenRecursivo(this.raiz,recorrido);
        return recorrido;  
      }  
      
      public void recorridoPreOrdenRecursivo(NodoMVias<K,V> nodoActual,List<K> recorrido){
          if (NodoMVias.esNodoVacio(nodoActual)){
             return ; 
          }
          for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
              recorrido.add(nodoActual.getClave(i)); 
               recorridoPreOrdenRecursivo(nodoActual.getHijo(i), recorrido);
          }
          recorridoPreOrdenRecursivo(nodoActual.getHijo(orden-1), recorrido);
      }

   
    @Override
    public List<K> recorridoEnPostOrden() {
        
       List<K> recorrido=new LinkedList<>();
        recorridoEnPostOrden(this.raiz,recorrido);
        return recorrido;  
      }  
      
      public void recorridoEnPostOrden(NodoMVias<K,V> nodoActual,List<K> recorrido){
          if (NodoMVias.esNodoVacio(nodoActual)){
             return ; 
          }
          recorridoEnPostOrden(nodoActual.getHijo(0), recorrido);
          for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
               recorridoEnPostOrden(nodoActual.getHijo(i+1), recorrido);
               recorrido.add(nodoActual.getClave(i)); 
          }
          
      }
    

    @Override
    public List<K> recorridoPorNiveles() {
     List<K> recorrido= new LinkedList<>();
      if (esArbolVacio()){
         return recorrido; 
      }
        
      Queue<NodoMVias<K, V>> colaDeNodos = new LinkedList<>();
      colaDeNodos.offer(this.raiz);
      
      while(!colaDeNodos.isEmpty()){
        NodoMVias<K,V> nodoActual= colaDeNodos.poll();
        
          for (int i = 0; i < nodoActual.cantidadDeDatosNoVacios(); i++) {
               recorrido.add(nodoActual.getClave(i));
               
          if (!nodoActual.esHijoVacio(i)){
          colaDeNodos.offer(nodoActual.getHijo(i));   
        }
          }
        if (!nodoActual.esHijoVacio(orden-1)){
          colaDeNodos.offer(nodoActual.getHijo(orden-1));   
        }
      }
        
        return recorrido;
    }
    
   public int cantidadHojasApartirDeUnNivel(int nivel){
       return cantidadHojasApartirDeUnNivel(this.raiz,nivel,0);
   }
    private int cantidadHojasApartirDeUnNivel(NodoMVias<K,V> nodoActual,int nivelObjetivo,int nivel){
      if(NodoMVias.esNodoVacio(nodoActual)){
          return 0;
      }  
      
      if(nivel>=nivelObjetivo){
          if (nodoActual.esHoja()){
              return 1;
          }
      }
      
      int cantidad=0;
          for (int i = 0; i < orden; i++) {
              cantidad=cantidad+cantidadHojasApartirDeUnNivel(nodoActual.getHijo(i),
                      nivelObjetivo, nivel+1);
          } 
      return cantidad;
    
    }
    
      @Override
    public String toString() {
        return generarCadenaDeArbol(this.raiz, "", false);
    }
    
    private String generarCadenaDeArbol(NodoMVias<K,V> nodoActual,String prefijo, boolean ponerCodo) {
        StringBuilder cadena = new StringBuilder();
        cadena.append(prefijo);
        
        if (prefijo.length() == 0) {
            cadena.append(ponerCodo ? "└──(R)" : "├──(R)"); //arbol vacio o no
        } else {
            cadena.append(ponerCodo ? "└──(D)" : "├──(I)");  //derecha o izquierda
        }
        if (NodoMVias.esNodoVacio(nodoActual)) {
            cadena.append("╣\n");
            return cadena.toString();
        }
        
        cadena.append(nodoActual.getListaDeClaves().toString());
        cadena.append("\n");
        
        String prefijoAux = prefijo + (ponerCodo ? "   ":"|   ");
        for (int i = 0; i < this.orden - 1; i++) {
            NodoMVias<K,V> nodoIzq = nodoActual.getHijo(i);
            cadena.append(generarCadenaDeArbol(nodoIzq, prefijoAux, false));
        }
        
        NodoMVias<K,V> nodoDer = nodoActual.getHijo(this.orden - 1);
        cadena.append(generarCadenaDeArbol(nodoDer, prefijoAux, true));

        return cadena.toString();
    }

    
    //*********************practico*********
    //6. Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol m-vias de bússqueda 
      public int  cantidadNodosConDatosVacios(){
          return cantidadNodosConDatosVacios(this.raiz);
      }
   
      private int cantidadNodosConDatosVacios(NodoMVias<K,V> nodoActual){
         if(NodoMVias.esNodoVacio(nodoActual)){
             return 0;
         }
         
          if(!nodoActual.estanDatosLlenos()){
             return 1;
         }
         int cantidad=0;
         for (int i = 0; i < orden; i++) {
            cantidad=cantidad+cantidadNodosConDatosVacios(nodoActual.getHijo(i));
         }
       
         
         return cantidad;
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
 
 

      
    //9. Implemente un método que retorne verdadero si solo hay hojas en el último nivel de un árbol m-vias de búsqueda. Falso en caso contrario.    
       public boolean hayHojasEnElUltimoNivel(){
          return  hayHojasEnElUltimoNivel(this.raiz,0);
       }
       
      private boolean hayHojasEnElUltimoNivel(NodoMVias<K,V> nodoActual,int nivel){
           if(NodoMVias.esNodoVacio(nodoActual)){
               return false;
           }
           
            if(nivel==nivel()){
            if(nodoActual.esHoja()){
               return true;
            }
           }
          for (int i = 0; i < orden; i++) {
              return hayHojasEnElUltimoNivel(nodoActual.getHijo(i),nivel+1);
          }
          
      return false;
       }
      
     //10. Implemente un método que retorne verdadero si un árbol m-vias esta balanceado según las reglas de un árbol B. Falso en caso contrario.  
      
//11. Implemente un método privado que reciba un dato como parámetro y
//      que retorne cual seria el predecesor inorden de dicho dato, sin realizar el recorrido en inOrden.      
 
   public K predecesorInOrden(K clave){
      int  pos=porDondeBajar(this.raiz, clave);
      return predecesorInOrdenp(this.raiz,pos);
   }   
      
          private K predecesorInOrdenp(NodoMVias<K,V> nodoActual, int i) {
        if (nodoActual.esHijoVacio(i)) {
            return nodoActual.getClave(i - 1);
        }
        NodoMVias<K,V> nodoAnterior = NodoMVias.nodoVacio();
        nodoActual = nodoActual.getHijo(i);
        while (!NodoMVias.esNodoVacio(nodoActual)) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.getHijo(orden - 1);
        }
        return nodoAnterior.getClave(orden - 2);
    }
   
          
     /* public K predecesorInOrden(K clave){
          return predecesorInOrdenP(clave);
      }
      
      private K predecesorInOrdenP(K claveABuscar){
      if (esArbolVacio()){
           return null;
       }
     
    NodoMVias<K,V> nodoActual=this.raiz;
    K clavePre=null;
     
  
  
      while(!NodoMVias.esNodoVacio(nodoActual)){
          NodoMVias<K,V> nodoAnterior=nodoActual;
           int pos=porDondeBajar(nodoActual, claveABuscar);
                 if(!existeClaveEnElNodo(nodoActual, claveABuscar)){
                   nodoActual=nodoActual.getHijo(pos);
                 }else{
                        nodoActual=nodoActual.getHijo(pos);
                         pos=porDondeBajar(nodoActual, claveABuscar);
                        if(nodoActual.esHijoVacio(pos)){
                            if(pos==0){
                               clavePre=nodoAnterior.getClave(pos); 
                            }else{
                         clavePre=nodoActual.getClave(pos-1);
                            }
                       }else{
                             clavePre=nodoActual.getClave(nodoActual.cantidadDeDatosNoVacios()-1);
                        }
                        nodoActual=NodoMVias.nodoVacio();
                     }
              } 
          return clavePre;
      }
   */
   //Para un árbol m-vias implemente un método que retorne la cantidad de nodos que tienen
    //todos sus hijos distintos de vacío luego del nivel N (La excepción a la regla son las hojas) 
    
    public int cantidadNodosDistintoVacio(int nivel){
        return cantidadNodosDistintoVacio(this.raiz,nivel,0);
    }
    
    
    public int cantidadNodosDistintoVacio(NodoMVias<K,V> nodoActual,int nivelABuscar,int nivel){
       if(NodoMVias.esNodoVacio(raiz)){
           return 0;
       }
       
      if(nivelABuscar>=nivel){
          if(nodoActual.estanDatosLlenos()){
              return 1;
          }
      } 
      
      int cantidad=0;
        for (int i = 0; i < orden; i++) {
            cantidad=cantidad+cantidadNodosDistintoVacio(nodoActual.getHijo(i),nivelABuscar,nivel+1);
        }
       return cantidad;
    }
    
    
}
