/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablas;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "detalle_importaciones")
@NamedQueries({
    @NamedQuery(name = "DetalleImportaciones.findAll", query = "SELECT d FROM DetalleImportaciones d"),
    @NamedQuery(name = "DetalleImportaciones.findByCodigo", query = "SELECT d FROM DetalleImportaciones d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "DetalleImportaciones.findByCantidad", query = "SELECT d FROM DetalleImportaciones d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "DetalleImportaciones.findByPrecio", query = "SELECT d FROM DetalleImportaciones d WHERE d.precio = :precio")})
public class DetalleImportaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cantidad")
    private Integer cantidad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @JoinColumn(name = "Importaciones_codigo", referencedColumnName = "codigo")
    @ManyToOne(optional = false)
    private Importaciones importacionescodigo;

    public DetalleImportaciones() {
    }

    public DetalleImportaciones(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Importaciones getImportacionescodigo() {
        return importacionescodigo;
    }

    public void setImportacionescodigo(Importaciones importacionescodigo) {
        this.importacionescodigo = importacionescodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleImportaciones)) {
            return false;
        }
        DetalleImportaciones other = (DetalleImportaciones) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tablas.DetalleImportaciones[ codigo=" + codigo + " ]";
    }
    
}
