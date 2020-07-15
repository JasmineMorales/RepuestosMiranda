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
public class PagoCredito implements PagosStrategy{

    int pagosRestantes;
    double saldoRestante;
    double cuota;
    String estado;

    public PagoCredito(int pagosRestantes, double saldoRestante, double cuota, String estado) {
        this.pagosRestantes = pagosRestantes;
        this.saldoRestante = saldoRestante;
        this.cuota = cuota;
        this.estado = estado;
    }

    Facturacion f = new Facturacion();
    
    @Override
    public void realizarPago() {        
        saldoRestante -= cuota;
        pagosRestantes --;
        f.setTotal(cuota);
        f.setEstado(estado);
    }
    
}
