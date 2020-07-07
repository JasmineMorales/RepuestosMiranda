/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 * Clase para guardar datos relevantes de usuarios
 * 
 */
public class Usuario {
    private int idPermisos;
    private String usuario;
    public Usuario(String usuario, int idPermisos){
        this.usuario=usuario;
        this.idPermisos=idPermisos;
    }

    public int getIdPermisos() {
        return idPermisos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setIdPermisos(int idPermisos) {
        this.idPermisos = idPermisos;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
}
