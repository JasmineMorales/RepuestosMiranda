/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import conexion.conexionDB;
import controladores.FacturacionJpaController;
import controladores.ImportacionesJpaController;
import javax.persistence.EntityManagerFactory;
import tablas.Facturacion;
import tablas.Importaciones;

/**
 *
 * @author jrmir
 */
public class controladorCompras implements manejoControlador{

    @Override
    public void nuevoDocumento() {
        EntityManagerFactory dB = conexionDB.obtenerConexion();
        ImportacionesJpaController controller = new ImportacionesJpaController(dB);
        controller.create(new Importaciones());
    }
    
}
