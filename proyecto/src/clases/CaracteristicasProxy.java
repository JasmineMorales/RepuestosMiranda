/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author aarroyave
 */
public class CaracteristicasProxy implements Caracteristicas{
    CaracteristicasGenerales caracteristicas = null;

    @Override
    public ArrayList<String> getCaracteristicas() {
        if(caracteristicas == null) caracteristicas = new CaracteristicasGenerales();
        return caracteristicas.getCaracteristicas();
    }

    @Override
    public void addCaracteristica(String Caracteristica) {
       if(this.caracteristicas != null) this.caracteristicas.addCaracteristica(Caracteristica);
    }

    @Override
    public void delCaracteristicas(String Caracteristica) {
        if(this.caracteristicas != null) this.caracteristicas.delCaracteristicas(Caracteristica);
    }

}
