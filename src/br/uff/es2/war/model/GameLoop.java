/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.IGameLoop;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObservable;
import br.uff.es2.war.interfaces.iObserver;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop implements iObservable, IGameLoop{
    
    
    private ArrayList<Player> players;
    private Iterator<Player> it;
    private int currentIndex;
    private DataManager dataManager;
    private Pais atacante;
    private Pais atacado;
    int numeroDeTropasAAlocarRodada;
    
    ArrayList<iObserver> observerList;
    
    
    public GameLoop(ArrayList<Player> players,DataManager manager){
        numeroDeTropasAAlocarRodada = 0;
        this.players = players;
        it = players.iterator();
        observerList = new ArrayList<>();
        currentIndex = 0;
        this.dataManager = manager;
        atacante = new Pais("", "", null);
        atacado = new Pais("", "", null);
    }
    
    public void carregaRodadaInicial()
    {
        ArrayList<Pais> paises = new ArrayList<>(dataManager.getPaises());
        int valor = paises.size()/players.size();
        
        for(Player p:players)
        {
            for(int i = 0;i<valor;i++)
            {
                int pos = (int)(Math.random()*paises.size());
                p.addPais(paises.get(pos));
                paises.remove(paises.get(pos));
            }
        }
        
        while(paises.size() > 0)
        {
            int pos = (int)(Math.random()*players.size());
            Player aux = players.get(pos);
            aux.addPais(paises.get(0));
            paises.remove(paises.get(0));
        }
        
        observerList.get(0).updateCircles();
        
    }
    
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    
    public Player getCurrentPlayer()
    {
        return players.get(currentIndex);
    }
    
    public int getCurrentIndex()
    {
        return currentIndex;
    }
    
    public int increaseIndex()
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
    
    public void setAtacado(Pais atacado)
    {
        this.atacado = atacado;
        System.out.println("atacado: "+atacado.getNome());
        this.processaAtaque(atacante, atacado);
    }
    
    public void setAtacante(Pais atacante)
    {
        this.atacante = atacante;
        System.out.println("acatacante: "+atacante.getNome());
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
    
    public void processaAtaque(Pais atacante, Pais atacado){
        
    } 
    
    @Override
    public void distribuiTropas (Pais pais){
        Player currentPlayer = players.get(currentIndex);
        if(numeroDeTropasAAlocarRodada > 0){
        if(pais.getDono().getNome().equals(currentPlayer.getNome())){
            pais.incrementaNumeroDeTropas();
            numeroDeTropasAAlocarRodada--;
            avisaMudancas();
        }else{
            System.out.println("Este pais não lhe pertence");
        }
        }else{
            
            System.out.println("Você não possui mais tropas");
        }
    }
    
    @Override
    public void calculaTropasASeremAlocadas(){
        Player currentPlayer = players.get(currentIndex);
        numeroDeTropasAAlocarRodada = currentPlayer.numeroDePaises()/2;
    }
    
    public int numeroDeTropasAAlocarRodada(){
        return this.numeroDeTropasAAlocarRodada();
    }
    
}
