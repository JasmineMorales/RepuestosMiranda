/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author arroyave
 */
public class CaracteristicasGenerales implements Caracteristicas{
    private ArrayList<String> caracteristicas;

    public CaracteristicasGenerales() {
        this.caracteristicas = new ArrayList<>();
        this.caracteristicas.add("peso");
    }
 
    @Override
    public ArrayList<String> getCaracteristicas() {
        return this.caracteristicas;
    }

    @Override
    public void addCaracteristica(String Caracteristica) {
        this.caracteristicas.add(Caracteristica);
    }

    @Override
    public void delCaracteristicas(String Caracteristica) {
        this.caracteristicas.remove(Caracteristica);
    }

    
}
