/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author claudio
 */
public class Continente {
    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    private ArrayList<Pais> alPaises;
    //teste de configuracao
    
    public Continente(int id, String nome)
    {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.alPaises = new ArrayList<>();
    }
    
    public Continente(int id, String nome,ArrayList<Pais> paises)
    {
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(nome);
        this.alPaises = paises;
        //comentario de teste git
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

    public ArrayList<Pais> getPaises() {
        return alPaises;
    }

    public void setPaises(ArrayList<Pais> paises) {
        this.alPaises = paises;
    }
    
    public void setPaises(Pais... paises)
    {
        for(Pais p : paises)
        {
            alPaises.add(p);
        }
    }
    
}
