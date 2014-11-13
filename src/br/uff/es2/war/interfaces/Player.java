/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;


import br.uff.es2.war.model.Carta;
import br.uff.es2.war.model.Continente;
import br.uff.es2.war.model.Objetivo;
import br.uff.es2.war.model.Pais;
import java.util.ArrayList;

/**
 *
 * @author RulffdaCosta
 */
public interface Player {
    
    public void attack(Player player);
    public void finalize();
    public void buyCard();
    public void tradeCards();
    
    public String getTipo();

    public String getNome();
    public String getCor();
    
    public ArrayList<Pais> getMeusPaises();
    
    public ArrayList<Continente> getMeusContinentes();
    
    public ArrayList<Carta> getCards();

    public Objetivo getObjetivo();
    
    public void setMeusPaises(ArrayList<Pais> meusPaises);
    
    public void addPais(Pais pais);
    
    public void addAllPaises(Pais... paises);
    
    public void remove(Pais pais);
    
    public int numeroDePaises();
    
    public void setObjetivo(Objetivo objetivo);
    
    public void setCards(ArrayList<Carta> meusPaises);
    
    public boolean checaObjetivo();
    
    public boolean getNewTroopa();
    
    public void setNewTroopa(boolean novaTropa);
    
}
