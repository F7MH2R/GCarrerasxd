/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author black
 */
@Entity
@Table(name = "intereses")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Intereses.findAll", query = "SELECT i FROM Intereses i"),
    @NamedQuery(name = "Intereses.findByIdInteres", query = "SELECT i FROM Intereses i WHERE i.idInteres = :idInteres"),
    @NamedQuery(name = "Intereses.findByFechaSeleccion", query = "SELECT i FROM Intereses i WHERE i.fechaSeleccion = :fechaSeleccion")})
public class Intereses implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_interes")
    private Integer idInteres;
    @Column(name = "fecha_seleccion")
    @Temporal(TemporalType.DATE)
    private Date fechaSeleccion;


     @Column(name = "id_usuario")
    private Integer idUsuario;
    
     @Column(name = "id_carrera")
    private Integer idCarrera;

    public Intereses(Date fechaSeleccion, Integer idUsuario, Integer idCarrera) {
        this.fechaSeleccion = fechaSeleccion;
        this.idUsuario = idUsuario;
        this.idCarrera = idCarrera;
    }
     

    public Intereses() {
    }

        public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }
    
    public Intereses(Integer idInteres) {
        this.idInteres = idInteres;
    }

    public Integer getIdInteres() {
        return idInteres;
    }

    public void setIdInteres(Integer idInteres) {
        this.idInteres = idInteres;
    }

    public Date getFechaSeleccion() {
        return fechaSeleccion;
    }

    public void setFechaSeleccion(Date fechaSeleccion) {
        this.fechaSeleccion = fechaSeleccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInteres != null ? idInteres.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Intereses)) {
            return false;
        }
        Intereses other = (Intereses) object;
        if ((this.idInteres == null && other.idInteres != null) || (this.idInteres != null && !this.idInteres.equals(other.idInteres))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Intereses[ idInteres=" + idInteres + " ]";
    }
    
}
