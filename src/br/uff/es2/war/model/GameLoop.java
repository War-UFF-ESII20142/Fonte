/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObservable;
import br.uff.es2.war.interfaces.iObserver;
import br.uff.es2.war.util.CyclicIterator;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop implements iObservable{
    
    
    private ArrayList<Player> players;
    private Iterator<Player> it;
    private int currentIndex;
    
    ArrayList<iObserver> observerList;
    
    
    public GameLoop(ArrayList<Player> players){
        this.players = players;
        it = players.iterator();
        observerList = new ArrayList<>();
        currentIndex = 0;
    }
    
    public Player getCurrentPlayer()
    {
        return players.get(currentIndex);
    }
    
    private int getCurrentIndex()
    {
        return currentIndex;
    }
    
    private int increaseIndex()
    {
        currentIndex++;
        if(currentIndex > players.size()-1) currentIndex = 0;
        avisaMudancas();
        return currentIndex;
    }
    
    public void principalLoop()
    {
        increaseIndex();
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

    @Override
    public void addObserver(iObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(iObserver observer) {
        observerList.remove(observer);
    }
    
    @Override
    public void avisaMudancas()
    {
        for(iObserver o : observerList)
        {
            o.updateGameImage();
        }
    }
    
}
