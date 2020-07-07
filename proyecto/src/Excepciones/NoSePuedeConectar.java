/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 * Clase para manejo de excepciones
 * @author Juampa
 */
public class NoSePuedeConectar extends Exception{
    /**
     * Constructor por defecto
     * @param msj mensaje a mostrar
     */
    public NoSePuedeConectar(String msj){
        super(msj);
    }
}
