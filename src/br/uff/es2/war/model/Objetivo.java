/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author claudio
 */
public class Objetivo {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    
    public Objetivo(int id, String nome)
    {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    public void checaObjetivo(){
        
    }
    
}
