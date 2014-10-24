/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoDeDadosHibernate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Filipe
 */
@Entity
@Table(name = "carta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Carta.findAll", query = "SELECT c FROM Carta c"),
    @NamedQuery(name = "Carta.findById", query = "SELECT c FROM Carta c WHERE c.id = :id"),
    @NamedQuery(name = "Carta.findByFormaGeo", query = "SELECT c FROM Carta c WHERE c.formaGeo = :formaGeo")})
public class Carta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "forma_geo")
    private String formaGeo;
    @JoinTable(name = "jog_carta", joinColumns = {
        @JoinColumn(name = "Carta_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Joga_Partida_id", referencedColumnName = "Partida_id"),
        @JoinColumn(name = "Joga_Jogador_id", referencedColumnName = "Jogador_id")})
    @ManyToMany
    private List<Joga> jogaList;
    @JoinColumn(name = "Pais_id", referencedColumnName = "id")
    @ManyToOne
    private Pais paisid;

    public Carta() {
    }

    public Carta(Integer id) {
        this.id = id;
    }

    public Carta(Integer id, String formaGeo) {
        this.id = id;
        this.formaGeo = formaGeo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFormaGeo() {
        return formaGeo;
    }

    public void setFormaGeo(String formaGeo) {
        this.formaGeo = formaGeo;
    }

    @XmlTransient
    public List<Joga> getJogaList() {
        return jogaList;
    }

    public void setJogaList(List<Joga> jogaList) {
        this.jogaList = jogaList;
    }

    public Pais getPaisid() {
        return paisid;
    }

    public void setPaisid(Pais paisid) {
        this.paisid = paisid;
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
        if (!(object instanceof Carta)) {
            return false;
        }
        Carta other = (Carta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.Carta[ id=" + id + " ]";
    }
    
}
