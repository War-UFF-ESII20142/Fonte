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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
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
@Table(name = "pais")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pais.findAll", query = "SELECT p FROM Pais p"),
    @NamedQuery(name = "Pais.findById", query = "SELECT p FROM Pais p WHERE p.id = :id"),
    @NamedQuery(name = "Pais.findByNome", query = "SELECT p FROM Pais p WHERE p.nome = :nome")})
public class Pais implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "nome")
    private String nome;
    @Lob
    @Column(name = "coordenada")
    private Object coordenada;
    @ManyToMany(mappedBy = "paisList")
    private List<Joga> jogaList;
    @JoinTable(name = "paises_vizinhos", joinColumns = {
        @JoinColumn(name = "Pais_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "Pais_id1", referencedColumnName = "id")})
    @ManyToMany
    private List<Pais> paisList;
    @ManyToMany(mappedBy = "paisList")
    private List<Pais> paisList1;
    @OneToMany(mappedBy = "paisid")
    private List<Carta> cartaList;
    @JoinColumn(name = "Continente_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Continente continenteid;

    public Pais() {
    }

    public Pais(Integer id) {
        this.id = id;
    }

    public Pais(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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

    public Object getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Object coordenada) {
        this.coordenada = coordenada;
    }

    @XmlTransient
    public List<Joga> getJogaList() {
        return jogaList;
    }

    public void setJogaList(List<Joga> jogaList) {
        this.jogaList = jogaList;
    }

    @XmlTransient
    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    @XmlTransient
    public List<Pais> getPaisList1() {
        return paisList1;
    }

    public void setPaisList1(List<Pais> paisList1) {
        this.paisList1 = paisList1;
    }

    @XmlTransient
    public List<Carta> getCartaList() {
        return cartaList;
    }

    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }

    public Continente getContinenteid() {
        return continenteid;
    }

    public void setContinenteid(Continente continenteid) {
        this.continenteid = continenteid;
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
        if (!(object instanceof Pais)) {
            return false;
        }
        Pais other = (Pais) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.Pais[ id=" + id + " ]";
    }
    
}
