/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoDeDadosHibernate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "joga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joga.findAll", query = "SELECT j FROM Joga j"),
    @NamedQuery(name = "Joga.findByPartidaid", query = "SELECT j FROM Joga j WHERE j.jogaPK.partidaid = :partidaid"),
    @NamedQuery(name = "Joga.findByJogadorid", query = "SELECT j FROM Joga j WHERE j.jogaPK.jogadorid = :jogadorid")})
public class Joga implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JogaPK jogaPK;
    @JoinTable(name = "jog_paises", joinColumns = {
        @JoinColumn(name = "Joga_Partida_id", referencedColumnName = "Partida_id"),
        @JoinColumn(name = "Joga_Jogador_id", referencedColumnName = "Jogador_id")}, inverseJoinColumns = {
        @JoinColumn(name = "Pais_id", referencedColumnName = "id")})
    @ManyToMany
    private List<Pais> paisList;
    @ManyToMany(mappedBy = "jogaList")
    private List<Carta> cartaList;
    @JoinColumn(name = "Partida_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partida partida;
    @JoinColumn(name = "Jogador_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Jogador jogador;
    @JoinColumn(name = "Objetivo_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Objetivo objetivoid;

    public Joga() {
    }

    public Joga(JogaPK jogaPK) {
        this.jogaPK = jogaPK;
    }

    public Joga(int partidaid, int jogadorid) {
        this.jogaPK = new JogaPK(partidaid, jogadorid);
    }

    public JogaPK getJogaPK() {
        return jogaPK;
    }

    public void setJogaPK(JogaPK jogaPK) {
        this.jogaPK = jogaPK;
    }

    @XmlTransient
    public List<Pais> getPaisList() {
        return paisList;
    }

    public void setPaisList(List<Pais> paisList) {
        this.paisList = paisList;
    }

    @XmlTransient
    public List<Carta> getCartaList() {
        return cartaList;
    }

    public void setCartaList(List<Carta> cartaList) {
        this.cartaList = cartaList;
    }

    public Partida getPartida() {
        return partida;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public Objetivo getObjetivoid() {
        return objetivoid;
    }

    public void setObjetivoid(Objetivo objetivoid) {
        this.objetivoid = objetivoid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jogaPK != null ? jogaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joga)) {
            return false;
        }
        Joga other = (Joga) object;
        if ((this.jogaPK == null && other.jogaPK != null) || (this.jogaPK != null && !this.jogaPK.equals(other.jogaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.Joga[ jogaPK=" + jogaPK + " ]";
    }
    
}
