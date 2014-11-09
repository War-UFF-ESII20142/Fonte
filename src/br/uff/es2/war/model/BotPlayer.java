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
public class BotPlayer implements Player{

    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty cor = new SimpleStringProperty();
    private SimpleStringProperty tipo = new SimpleStringProperty();
    private ArrayList<Carta> cards = new ArrayList<>();
    private ArrayList<Pais> meusPaises;
    private Objetivo objetivo;
    
    public BotPlayer(String nome, String cor, String tipo)
    {
        this.nome.set(nome);
        this.cor.set(cor);
        this.tipo.set(tipo);
        this.meusPaises = new ArrayList<>();
        this.objetivo = new Objetivo(0,"");
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
    public void attack(Player player) {
        
    }

    @Override
    public void buyCard() {
        
    }

    @Override
    public void tradeCards() {
        
    }

    @Override
    public String getNome() {
        return nome.get();
    }

    @Override
    public String getCor() {
        return cor.get();
    }
    
    @Override
    public void finalize(){
        
    }

    @Override
    public ArrayList<Pais> getMeusPaises() {
        return this.meusPaises;
    }

    @Override
    public void setMeusPaises(ArrayList<Pais> meusPaises) {
        this.meusPaises = meusPaises;
    }

    @Override
    public void addPais(Pais pais) {
        meusPaises.add(pais);
        pais.setDono(this);
    }

    @Override
    public void addAllPaises(Pais... paises) {
        for(Pais p:paises)
        {
            meusPaises.add(p);
            p.setDono(this);
        }
    }

    @Override
    public void remove(Pais pais) {
        meusPaises.remove(pais);
    }
    
    public int numeroDePaises(){
        return meusPaises.size();
    }

    @Override
    public ArrayList<Carta> getCards() {
        return this.cards;
    }

    @Override
    public Objetivo getObjetivo() {
        return this.objetivo;
    }

    @Override
    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public boolean checaObjetivo() {
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
    
    public void processaRodada(GameLoop gameLoop)
    {
        //faz troca de cartas
        
        //Adiciona exercitos
        addExercitos(gameLoop);
        //faz ataque
        
        //redistribui exercitos
        
        gameLoop.principalLoop();
    }
    
    private void addExercitos(GameLoop gameLoop)
    {
        System.out.println("To aquii!!!");
        while(gameLoop.temTropa())
        {
            //pega pais com menos exercito
            Pais p = paisMaisFraco();
            
            gameLoop.distribuiTropas(p);
        }
    }
    
    private void fazAtaque(GameLoop gameLoop)
    {
        
    }
    
    private void espalhaExercito(GameLoop gameLoop)
    {
        
    }

    private Pais paisMaisFraco() {
        Pais pTemp = new Pais("","",null);
        
        int min = 10000;
        
        for(Pais p : meusPaises)
        {
            if(p.getNumeroDeTroopas() < min)
            {
                min = p.getNumeroDeTroopas();
                pTemp = p;
            }
        }
        
        return pTemp;
    }
    
}
