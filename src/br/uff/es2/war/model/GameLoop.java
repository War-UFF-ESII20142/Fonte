/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.interfaces.Player;
import br.uff.networks.domino_mania.model.CyclicIterator;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop {
    
    
    ArrayList<Player> players;
    Iterator<Player> it;
    
    
    public GameLoop(ArrayList<Player> players){
        this.players = players;
        it = players.iterator();
    }
    
    public void principalLoop()
    {
        while(it.hasNext())
        {
            System.out.println("Nome: "+it.next().getNome());
        }
        
        /**
        while(true){  //enquanto ninguem ganhou. Implementar as checagens de vitoria){
            Player currentPlayer = it.next();
            
            //if(clicou atacar){
            currentPlayer.attack(currentPlayer);
            
            //if(clicou comprar cartas)
            currentPlayer.buyCard();
            
            //if(clicou trocar cartas)
            currentPlayer.tradeCards();
            
            //if(clicou finalizar)
            currentPlayer = it.next();
          
        }**/
        
        
    }
    
}
