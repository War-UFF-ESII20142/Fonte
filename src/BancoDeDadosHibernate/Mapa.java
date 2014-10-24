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
@Table(name = "mapa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mapa.findAll", query = "SELECT m FROM Mapa m"),
    @NamedQuery(name = "Mapa.findById", query = "SELECT m FROM Mapa m WHERE m.id = :id"),
    @NamedQuery(name = "Mapa.findByNome", query = "SELECT m FROM Mapa m WHERE m.nome = :nome"),
    @NamedQuery(name = "Mapa.findByUrl", query = "SELECT m FROM Mapa m WHERE m.url = :url")})
public class Mapa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mapaid")
    private List<Objetivo> objetivoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mapaid")
    private List<Continente> continenteList;
    @JoinColumn(name = "Partida_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Partida partidaid;

    public Mapa() {
    }

    public Mapa(Integer id) {
        this.id = id;
    }

    public Mapa(Integer id, String nome, String url) {
        this.id = id;
        this.nome = nome;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlTransient
    public List<Objetivo> getObjetivoList() {
        return objetivoList;
    }

    public void setObjetivoList(List<Objetivo> objetivoList) {
        this.objetivoList = objetivoList;
    }

    @XmlTransient
    public List<Continente> getContinenteList() {
        return continenteList;
    }

    public void setContinenteList(List<Continente> continenteList) {
        this.continenteList = continenteList;
    }

    public Partida getPartidaid() {
        return partidaid;
    }

    public void setPartidaid(Partida partidaid) {
        this.partidaid = partidaid;
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
        if (!(object instanceof Mapa)) {
            return false;
        }
        Mapa other = (Mapa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.Mapa[ id=" + id + " ]";
    }
    
}
