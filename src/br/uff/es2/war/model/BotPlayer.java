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
    
    public BotPlayer(String nome, String cor, String tipo)
    {
        this.nome.set(nome);
        this.cor.set(cor);
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
        return "";
    }

    @Override
    public String getCor() {
        return "";
    }
    
    @Override
    public void finalize(){
        
    }
    
}
