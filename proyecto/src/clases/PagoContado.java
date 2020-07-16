/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import tablas.Facturacion;

/**
 *
 * @author manue
 */
public class PagoContado implements PagosStrategy{

    double saldo;
    String estado;

    public PagoContado(double saldo, String estado) {
        this.saldo = saldo;
        this.estado = estado;
    }
    
    Facturacion f = new Facturacion();
    
    @Override
    public void realizarPago() {
        f.setTotal(saldo);
        f.setEstado(estado);
    }
    
}
