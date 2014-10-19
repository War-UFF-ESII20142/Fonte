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
public class Carta {
    private SimpleIntegerProperty id;
    private SimpleStringProperty descricao;
    
    public Carta(int id, String descricao)
    {
        this.id = new SimpleIntegerProperty(id);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }
    
}
