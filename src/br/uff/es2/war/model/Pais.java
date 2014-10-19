/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author claudio
 */
public class Pais {
    private SimpleStringProperty nome;
    private SimpleStringProperty codigoArquivo;
    private ArrayList<Pais> vizinhos;
    
    public Pais(String nome, String codigo, String continente)
    {
        this.nome = new SimpleStringProperty(nome);
        this.codigoArquivo = new SimpleStringProperty(codigo);
        vizinhos = new ArrayList<>();
    }
    
    public void addTerritorio(Pais t)
    {
        vizinhos.add(t);
    }
    
    public void addAllTerritorio(Pais... t)
    {
        for(Pais p : t)
        {
            vizinhos.add(p);
        }
    }

    public String getNome() {
        return nome.getValue();
    }

    public void setNome(String nome) {
        this.nome.setValue(nome);
    }

    public String getCodigo() {
        return codigoArquivo.getValue();
    }

    public void setCodigo(String codigo) {
        this.codigoArquivo.setValue(codigo);
    }
}
