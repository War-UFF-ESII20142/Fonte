/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoDeDadosHibernate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Filipe
 */
@Entity
@Table(name = "objetivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Objetivo.findAll", query = "SELECT o FROM Objetivo o"),
    @NamedQuery(name = "Objetivo.findById", query = "SELECT o FROM Objetivo o WHERE o.id = :id"),
    @NamedQuery(name = "Objetivo.findByDescr", query = "SELECT o FROM Objetivo o WHERE o.descr = :descr")})
public class Objetivo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descr")
    private String descr;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "objetivoid")
    private List<Joga> jogaList;
    @JoinColumn(name = "Mapa_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Mapa mapaid;

    public Objetivo() {
    }

    public Objetivo(Integer id) {
        this.id = id;
    }

    public Objetivo(Integer id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    @XmlTransient
    public List<Joga> getJogaList() {
        return jogaList;
    }

    public void setJogaList(List<Joga> jogaList) {
        this.jogaList = jogaList;
    }

    public Mapa getMapaid() {
        return mapaid;
    }

    public void setMapaid(Mapa mapaid) {
        this.mapaid = mapaid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Objetivo)) {
            return false;
        }
        Objetivo other = (Objetivo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.Objetivo[ id=" + id + " ]";
    }
    
}
