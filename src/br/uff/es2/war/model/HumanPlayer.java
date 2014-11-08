/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war.model;

import br.uff.es2.war.interfaces.Player;
import java.io.PrintStream;
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
    private Objetivo objetivo;
    
    public HumanPlayer(String nome, String cor, String tipo){
        this.tipo = new SimpleStringProperty(tipo);
        this.cor = new SimpleStringProperty(cor);
        this.nome = new SimpleStringProperty(nome);
        this.meusPaises = new ArrayList<>();
        this.objetivo = new Objetivo(0,"");
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
    @Override
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

    /**
     * @return the cards
     */
    public ArrayList<Carta> getCards() {
        return cards;
    }

    /**
     * @return the objetivo
     */
    @Override
    public Objetivo getObjetivo() {
        return objetivo;
    }
    
    public void setObjetivo(Objetivo objetivo){
        this.objetivo = objetivo;
    }
    
    public boolean checaObjetivo(){
        return this.objetivo.checaObjetivo(this);
    }

    @Override
    public ArrayList<Continente> getMeusContinentes() {
        ArrayList<Continente> conts = new ArrayList<>(), aux = new ArrayList<>();
        System.out.println("meusPaises.size(): " + meusPaises.size());
        for(Pais mP : meusPaises){
            if(!conts.contains(mP.getContinente()))
                conts.add(mP.getContinente()); //add continentes onde tenho países, sem repetir
        }
        boolean isAll;
        for(Continente c : conts){
            isAll = true;
            for(Pais p : c.getPaises()){
                if(!p.getDono().getNome().equals(nome.get())/*!getMeusPaises.contains(p)*/)
                    isAll = false;
            }
            if(!isAll)
                aux.add(c); //continentes os quais possuo algum país, mas não todos
        }
        for (Continente c : aux) {
            conts.remove(c);
        }
        return conts;
    }
    
}