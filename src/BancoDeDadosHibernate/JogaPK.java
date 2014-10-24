/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoDeDadosHibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Filipe
 */
@Embeddable
public class JogaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "Partida_id")
    private int partidaid;
    @Basic(optional = false)
    @Column(name = "Jogador_id")
    private int jogadorid;

    public JogaPK() {
    }

    public JogaPK(int partidaid, int jogadorid) {
        this.partidaid = partidaid;
        this.jogadorid = jogadorid;
    }

    public int getPartidaid() {
        return partidaid;
    }

    public void setPartidaid(int partidaid) {
        this.partidaid = partidaid;
    }

    public int getJogadorid() {
        return jogadorid;
    }

    public void setJogadorid(int jogadorid) {
        this.jogadorid = jogadorid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) partidaid;
        hash += (int) jogadorid;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JogaPK)) {
            return false;
        }
        JogaPK other = (JogaPK) object;
        if (this.partidaid != other.partidaid) {
            return false;
        }
        if (this.jogadorid != other.jogadorid) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BancoDeDadosHibernate.JogaPK[ partidaid=" + partidaid + ", jogadorid=" + jogadorid + " ]";
    }
    
}
