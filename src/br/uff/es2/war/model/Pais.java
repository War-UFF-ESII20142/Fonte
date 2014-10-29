/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.interfaces.Player;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author claudio
 */
public class Pais {
    private SimpleStringProperty nome;
    private SimpleStringProperty codigoArquivo;
    private ArrayList<Pais> vizinhos;
    private Continente continente;
    private Player dono;
    private int numeroDeTropas;
           
    
    public Pais(String nome, String codigo, Continente continente)
    {
        this.nome = new SimpleStringProperty(nome);
        this.codigoArquivo = new SimpleStringProperty(codigo);
        vizinhos = new ArrayList<>();
        this.continente = continente;
        numeroDeTropas = 1;
    }
    
    public void addVizinhos(Pais t)
    {
        vizinhos.add(t);
    }
    
    public void addAllVizinhos(Pais... t)
    {
        for(Pais p : t)
        {
            vizinhos.add(p);
        }
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getCodigo() {
        return codigoArquivo.get();
    }

    public void setCodigo(String codigo) {
        this.codigoArquivo.set(codigo);
    }

    public Continente getContinente() {
        return continente;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }

    public Player getDono() {
        return dono;
    }

    public void setDono(Player dono) {
        this.dono = dono;
    }
    
    public void incrementaNumeroDeTropas(){
        numeroDeTropas++;
    }
    
    public int getNumeroDeTroopas(){
        return numeroDeTropas;
    }
    
    public boolean isVizinho(Pais pais){
        if (vizinhos.contains(pais)) {
            return true;
        }else{
            return false;
        }
    }
    
}
