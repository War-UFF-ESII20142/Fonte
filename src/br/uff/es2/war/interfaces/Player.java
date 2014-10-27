/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;

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

    public String getNome();
    public String getCor();
    
    public ArrayList<Pais> getMeusPaises();

    public void setMeusPaises(ArrayList<Pais> meusPaises);
    
    public void addPais(Pais pais);
    
    public void addAllPaises(Pais... paises);
    
    public void remove(Pais pais);
    
}
