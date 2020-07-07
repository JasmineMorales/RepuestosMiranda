/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablas;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "importaciones")
@NamedQueries({
    @NamedQuery(name = "Importaciones.findAll", query = "SELECT i FROM Importaciones i"),
    @NamedQuery(name = "Importaciones.findByCodigo", query = "SELECT i FROM Importaciones i WHERE i.codigo = :codigo"),
    @NamedQuery(name = "Importaciones.findByDescripcion", query = "SELECT i FROM Importaciones i WHERE i.descripcion = :descripcion"),
    @NamedQuery(name = "Importaciones.findByEstado", query = "SELECT i FROM Importaciones i WHERE i.estado = :estado"),
    @NamedQuery(name = "Importaciones.findByToral", query = "SELECT i FROM Importaciones i WHERE i.toral = :toral"),
    @NamedQuery(name = "Importaciones.findByProveedorid", query = "SELECT i FROM Importaciones i WHERE i.proveedorid = :proveedorid")})
public class Importaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "estado")
    private String estado;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "toral")
    private Double toral;
    @Basic(optional = false)
    @Column(name = "Proveedor_id")
    private int proveedorid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "importacionescodigo")
    private List<DetalleImportaciones> detalleImportacionesList;

    public Importaciones() {
    }

    public Importaciones(Integer codigo) {
        this.codigo = codigo;
    }

    public Importaciones(Integer codigo, int proveedorid) {
        this.codigo = codigo;
        this.proveedorid = proveedorid;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Double getToral() {
        return toral;
    }

    public void setToral(Double toral) {
        this.toral = toral;
    }

    public int getProveedorid() {
        return proveedorid;
    }

    public void setProveedorid(int proveedorid) {
        this.proveedorid = proveedorid;
    }

    public List<DetalleImportaciones> getDetalleImportacionesList() {
        return detalleImportacionesList;
    }

    public void setDetalleImportacionesList(List<DetalleImportaciones> detalleImportacionesList) {
        this.detalleImportacionesList = detalleImportacionesList;
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
        if (!(object instanceof Importaciones)) {
            return false;
        }
        Importaciones other = (Importaciones) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tablas.Importaciones[ codigo=" + codigo + " ]";
    }
    
}
