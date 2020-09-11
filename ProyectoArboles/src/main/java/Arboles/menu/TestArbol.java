/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles.menu;

import Arboles.ArbolAVL;
import Arboles.ArbolB;
import Arboles.ArbolBusquedaBinario;
import Arboles.ArbolMViasBusqueda;
import Arboles.ExceptionClaveNoExiste;
import Arboles.ExceptionClaveYaExiste;
import Arboles.ExceptionOrdenInvalido;
import Arboles.IArbolBusqueda;
import Arboles.Practico1.Abb;
import java.util.Scanner;

/**
 *
 * @author brandon
 */
public class TestArbol{
    
 public static void main(String[] args) throws ExceptionClaveYaExiste, ExceptionClaveNoExiste, ExceptionOrdenInvalido  { 
     
      IArbolBusqueda<Integer,String> ArbolDeBusqueda = null;
      IArbolBusqueda<Integer,String> ArbolBe;
      Scanner entrada= new Scanner(System.in);
      
     /*    System.out.println("elija el tipo de arbol,ABB, AVL, AMV, AB");
        
        String tipoDeArbol=entrada.next();
        tipoDeArbol=tipoDeArbol.toUpperCase();
     switch(tipoDeArbol){
          
          case("AMV"):
              ArbolDeBusqueda=new ArbolMViasBusqueda<>(4);
              break;
        
          case("AB"):
              ArbolDeBusqueda=new ArbolB<>(3);
          break;
          default:
              System.out.println("tipo de arbol invaldo, escogiendo.. arbol binario de Mvias");
               ArbolDeBusqueda=new ArbolMViasBusqueda<>();
      }
      
              ArbolDeBusqueda.insertar(20, "cristian soza");
              ArbolDeBusqueda.insertar(7, "julio gonzales");
              ArbolDeBusqueda.insertar(23, "llanos");
              ArbolDeBusqueda.insertar(9, "mario");
              ArbolDeBusqueda.insertar(11, "armando");
              ArbolDeBusqueda.insertar(25, "julia");
              ArbolDeBusqueda.insertar(4, "berta");
              ArbolDeBusqueda.insertar(3, "marcela"); 
              ArbolDeBusqueda.insertar(22, "carol");
              ArbolDeBusqueda.insertar(10, "valeria");
              ArbolDeBusqueda.insertar(8, "valeria");
              ArbolDeBusqueda.insertar(24, "valeria");
              
            
             
          //  arbolBalnaceado.toString();
              System.out.println(ArbolDeBusqueda.eliminar(9));  
       */   
     
     System.out.println("*********************PRACTICO 2 ******************************* ");
     System.out.println("iNGRESE 0 PARA SALIR  ");
     System.out.println("1.-Implemente el método insertar de un árbol m-vias de búsqueda");
     System.out.println("2.-Implemente el método eliminar de un árbol m-vias de búsqueda  ");
     System.out.println("3.-Implemente el método insertar del árbol b ");
     System.out.println("4.-Implemente el método eliminar del árbol b.  ");
     System.out.println("5.- Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol B ");
     System.out.println("6.- Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol m-vias de bússqueda ");
     System.out.println("7.-Implemente un método recursivo que retorne la cantidad nodos con datos vacíos en un árbol B, pero solo en el nivel N ");
     System.out.println("8.- Implemente un método iterativo que retorne la cantidad nodos con datos vacíos en un árbol b, pero solo en el nivel N");
     System.out.println("9.-Implemente un método que retorne verdadero si solo hay hojas en el último nivel de un árbol "
             + "m-vias de búsqueda. Falso en caso contrario. ");
     System.out.println("10.- Implemente un método que retorne verdadero si un árbol m-vias esta balanceado "
             + "según las reglas de un árbol B. Falso en caso contrario.  ");
     System.out.println("11.-Implemente un método privado que reciba un dato como parámetro y que retorne cual seria"
             + " el predecesor inorden de dicho dato, sin realizar el recorrido en inOrden.  ");
       System.out.println("12.- Para un árbol m-vias implemente un método que retorne la cantidad de nodos que tienen todos sus hijos"+ 
             "distintos de vacío luego del nivel N (La excepción a la regla son las hojas");
     int opcion= -1;
     Scanner ponerDato= new Scanner(System.in);
     int cantidad;
    while(opcion!=0){
       System.out.println("Introduca Una Opcion ");
       opcion=entrada.nextInt();
      
        switch(opcion){
            case 1:
                System.out.println("introdusca el orden >= 4");
              cantidad=ponerDato.nextInt();
             ArbolDeBusqueda=new ArbolMViasBusqueda<>(cantidad);
             System.out.println("introdusca cuantos datos quiere insertar");
             
                for (int i = 0; i < cantidad-1; i++) {
                    System.out.println("ingrese clave y valor---> EJEMPLO: 10 mario"); 
                    int clave=ponerDato.nextInt();
                    String valor=ponerDato.next();
                    ArbolDeBusqueda.insertar(clave,valor);
                     
                }
                 ArbolDeBusqueda.toString();
                 System.out.println(ArbolDeBusqueda);  
         break;
             
            case 2:
               System.out.println("introdusca el dato a elimianr mvias"); 
              System.out.println((ArbolDeBusqueda).eliminar(ponerDato.nextInt()));
           //   ArbolDeBusqueda.toString();
                 System.out.println(ArbolDeBusqueda); 
            break;
             
            case 3:
                 
            System.out.println("introdusca el orden >=3");
            cantidad=ponerDato.nextInt();
            ArbolDeBusqueda=new ArbolB<>(cantidad);
                for (int i = 0; i < cantidad-1; i++) {
                    System.out.println("ingrese clave y valor---> ejm 10 mario"); 
                    int clave=ponerDato.nextInt();
                    String valor=ponerDato.next();
                    ArbolDeBusqueda.insertar(clave,valor);
                     
                }
                 ArbolDeBusqueda.toString();
                 System.out.println(ArbolDeBusqueda);  
                
             break;
            
             case 4:
                System.out.println((ArbolDeBusqueda).eliminar(ponerDato.nextInt()));
                System.out.println(ArbolDeBusqueda);  

             break;
             
             case 5://arbolB
                System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).cantidadNodosConDatosVacios());  
             break;
             
             case 6:
                  System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).cantidadNodosConDatosVacios()); 
             break;
             
             case 7: //Arbol B
                  System.out.println("introdusca el numero");
                  System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).cantidadNodosConDatosVaciosDelNivel(ponerDato.nextInt()));  
             break;
             
             case 8://arbol b
                   System.out.println("no esta hecho");
             break;
             
             case 9:
                  System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).hayHojasEnElUltimoNivel());
             break;
             
             case 10://arbol B
              System.out.println("no esta hecho");
             break;
             
             case 11:
                 System.out.println(ArbolDeBusqueda.recorridoInOrdenRecursivo()); 
                 System.out.println("introdusca el numero"); 
                System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).predecesorInOrden(ponerDato.nextInt())); 
                 
             break;
             
             case 12:
                 System.out.println("introdusca el nivel"); 
                  System.out.println(((ArbolMViasBusqueda)ArbolDeBusqueda).cantidadNodosDistintoVacio(ponerDato.nextInt()));
             break;
        }
    } 
              
      
 }     
    
 }    
