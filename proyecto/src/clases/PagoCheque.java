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
public class PagoCheque implements PagosStrategy{

    String nomber;
    String numeroCheque;
    String estado;
    double monto;

    public PagoCheque(String nomber, String numeroCheque, String estado, double monto) {
        this.nomber = nomber;
        this.numeroCheque = numeroCheque;
        this.estado = estado;
        this.monto = monto;
    }
    
    Facturacion f = new Facturacion();
    
    @Override
    public void realizarPago() {
        f.setEstado(estado);
        f.setTotal(monto);
    }
}
