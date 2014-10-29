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
 * @author AleGomes
 */
public class HumanPlayer implements Player {
//    String tipo;
//    String cor;
//    String nome;
     
    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty cor = new SimpleStringProperty();
    private SimpleStringProperty tipo = new SimpleStringProperty();
    private ArrayList<Carta> cards = new ArrayList<>();
    private ArrayList<Pais> meusPaises;
    
    
    public HumanPlayer(String nome, String cor, String tipo){
        this.tipo = new SimpleStringProperty(tipo);
        this.cor = new SimpleStringProperty(cor);
        this.nome = new SimpleStringProperty(nome);
        this.meusPaises = new ArrayList<>();
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
    
    @Override
    public void attack(Player player){
        //param: Another Player to be attacked
        
    }
    
    @Override
    public void buyCard(){
        
    }
    
    @Override
    public void tradeCards(){
        
    }
    
    @Override
    public void finalize(){
        
    }

    @Override
    public ArrayList<Pais> getMeusPaises() {
        return meusPaises;
    }

    @Override
    public void setMeusPaises(ArrayList<Pais> meusPaises) {
        this.meusPaises = meusPaises;
    }
    
    @Override
    public void addPais(Pais pais)
    {
        meusPaises.add(pais);
        pais.setDono(this);
    }
    
    @Override
    public void addAllPaises(Pais... paises)
    {
        for(Pais p:paises)
        {
            meusPaises.add(p);
            p.setDono(this);
        }
    }
    
    @Override
    public void remove(Pais pais)
    {
        meusPaises.remove(pais);
    }
    
    public int numeroDePaises(){
       return this.meusPaises.size();
    }
}