/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jogo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author AleGomes
 */
public class JogoSalvo {
    private Date data;
    private int NumeroJogadores;
    private List<String> NomeJogadores;

    /**
     * @return the data
     */
    public Date getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * @return the NumeroJogadores
     */
    public int getNumeroJogadores() {
        return NumeroJogadores;
    }

    /**
     * @param NumeroJogadores the NumeroJogadores to set
     */
    public void setNumeroJogadores(int NumeroJogadores) {
        this.NumeroJogadores = NumeroJogadores;
    }
}
