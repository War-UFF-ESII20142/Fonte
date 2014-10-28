/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war;

import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.model.GameLoop;
import br.uff.es2.war.model.Pais;
import br.uff.es2.war.view.JanelaJogo;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author RulffdaCosta
 */
public class GameManager 
{
    private ObservableList<Player> olPlayers;
    private GameLoop gameLoop;
    private JanelaJogo janelaJogo;
    DataManager dataManager;
    
    public GameManager(ArrayList<Player> players,DataManager data, JanelaJogo janelaJogo)
    {
        dataManager = data;
        gameLoop = new GameLoop(players,dataManager);
        this.janelaJogo = janelaJogo;
    }

    public ObservableList<Player> getOlPlayers() {
        return olPlayers;
    }

    public void setOlPlayers(ObservableList<Player> olPlayers) {
        this.olPlayers = olPlayers;
    }
    
    public void roundTerminou()
    {
        gameLoop.principalLoop();
    }
    
    public void attack(){
       Pais paisAtacante = this.getPlaceAtacante();
       Pais paisAtacado = this.getPlaceAtacado();
        System.out.println("Atacou"+paisAtacante.getNome()+" "+paisAtacado.getNome());
    }
    
    
    public Pais getPlaceAtacante(){
        String pais = janelaJogo.paisAtacante();
        Pais temp = dataManager.getPais(pais);
        return temp;
    }
    
    public Pais getPlaceAtacado(){
        String pais = janelaJogo.paisAtacado();
        Pais temp = dataManager.getPais(pais);
        return temp;
    }
    
    public GameLoop getGameLoop()
    {
        return gameLoop;
    }
}
