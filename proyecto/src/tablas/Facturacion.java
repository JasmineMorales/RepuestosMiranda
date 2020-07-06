/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tablas;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author manue
 */
@Entity
@Table(name = "facturacion")
@NamedQueries({
    @NamedQuery(name = "Facturacion.findAll", query = "SELECT f FROM Facturacion f"),
    @NamedQuery(name = "Facturacion.findByCodigo", query = "SELECT f FROM Facturacion f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "Facturacion.findByDescripcion", query = "SELECT f FROM Facturacion f WHERE f.descripcion = :descripcion"),
    @NamedQuery(name = "Facturacion.findByEstado", query = "SELECT f FROM Facturacion f WHERE f.estado = :estado"),
    @NamedQuery(name = "Facturacion.findByTotal", query = "SELECT f FROM Facturacion f WHERE f.total = :total"),
    @NamedQuery(name = "Facturacion.findByFecha", query = "SELECT f FROM Facturacion f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Facturacion.findByClienteid", query = "SELECT f FROM Facturacion f WHERE f.clienteid = :clienteid")})
public class Facturacion implements Serializable {

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
    @Column(name = "total")
    private Double total;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "Cliente_id")
    private int clienteid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facturacioncodigo")
    private List<DetalleFacturacion> detalleFacturacionList;

    public Facturacion() {
    }

    public Facturacion(Integer codigo) {
        this.codigo = codigo;
    }

    public Facturacion(Integer codigo, int clienteid) {
        this.codigo = codigo;
        this.clienteid = clienteid;
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getClienteid() {
        return clienteid;
    }

    public void setClienteid(int clienteid) {
        this.clienteid = clienteid;
    }

    public List<DetalleFacturacion> getDetalleFacturacionList() {
        return detalleFacturacionList;
    }

    public void setDetalleFacturacionList(List<DetalleFacturacion> detalleFacturacionList) {
        this.detalleFacturacionList = detalleFacturacionList;
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
        if (!(object instanceof Facturacion)) {
            return false;
        }
        Facturacion other = (Facturacion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tablas.Facturacion[ codigo=" + codigo + " ]";
    }
    
}
