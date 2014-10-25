/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop {
    
    
    ArrayList<HumanPlayer> players;
    Iterator<HumanPlayer> it;
    
    
    public GameLoop(ArrayList players){
        players = this.players;
        it = players.iterator();
      
    }
    
    public void principalLoop(){
        
        while(true /*enquanto ninguem ganhou. Implementar as checagens de vitoria*/){
            HumanPlayer currentPlayer = it.next();
            
            //if(clicou atacar){
            currentPlayer.attack(currentPlayer);
            
            //if(clicou comprar cartas)
            currentPlayer.buyCard();
            
            //if(clicou trocar cartas)
            currentPlayer.tradeCards();
            
            //if(clicou finalizar)
            currentPlayer = it.next();
            
          
        }
        
        
    }
    
}
