/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author AleGomes
 */
public class Player {
//    String tipo;
//    String cor;
//    String nome;
     
    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty cor = new SimpleStringProperty();
    private SimpleStringProperty tipo = new SimpleStringProperty();
    
    
    public Player(String nome, String cor, String tipo){
        this.tipo = new SimpleStringProperty(tipo);
        this.cor = new SimpleStringProperty(cor);
        this.nome = new SimpleStringProperty(nome);
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome.get();
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome.set(nome);
    }

    /**
     * @return the cor
     */
    public String getCor() {
        return cor.get();
    }

    /**
     * @param cor the cor to set
     */
    public void setCor(String cor) {
        this.cor.set(cor);
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo.get();
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }
}