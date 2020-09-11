/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Arboles;

/**
 *
 * @author brandon
 */
public class ExceptionOrdenInvalido extends Exception{
     public ExceptionOrdenInvalido() {
        super("el orden de su arbol debe ser mayor o igual a 3");
    }

    public ExceptionOrdenInvalido(String message) {
        super(message);
    } 
}
