/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import conexion.conexionDB;
import controladores.FacturacionJpaController;
import javax.persistence.EntityManagerFactory;
import tablas.Facturacion;

/**
 *
 * @author jrmir
 */
public class controladorVentas implements manejoControlador{

    @Override
    public void nuevoDocumento() {
        EntityManagerFactory dB = conexionDB.obtenerConexion();
        FacturacionJpaController controller = new FacturacionJpaController(dB);
        controller.create(new Facturacion());
    }
    
}
