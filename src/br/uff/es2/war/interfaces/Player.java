/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;

/**
 *
 * @author RulffdaCosta
 */
public interface Player {
    
    public void attack(Player player);
    public void finalize();
    public void buyCard();
    public void tradeCards();

    public Object getNome();
}