/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObservable;
import br.uff.es2.war.interfaces.iObserver;
<<<<<<< HEAD
import br.uff.es2.war.util.CyclicIterator;
=======
>>>>>>> 9e613c56af4b8b36be55b60f1219c28ca7e3323f
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
    private DataManager dataManager;
    
    ArrayList<iObserver> observerList;
    
    
    public GameLoop(ArrayList<Player> players,DataManager manager){
        this.players = players;
        it = players.iterator();
        observerList = new ArrayList<>();
        currentIndex = 0;
        this.dataManager = manager;
        carregaRodadaInicial();
    }
    
    private void carregaRodadaInicial()
    {
        ArrayList<Pais> paises = dataManager.getPaises();
        while(paises.size() > 0)
        {
            int pos = (int)(Math.random()*players.size());
            Player aux = players.get(pos);
            aux.addPais(paises.get(0));
            paises.remove(paises.get(0));
        }
    }
    
    public ArrayList<Player> getPlayers()
    {
        return players;
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
