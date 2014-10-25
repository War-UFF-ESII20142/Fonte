/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war;

import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.model.GameLoop;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author RulffdaCosta
 */
public class GameManager 
{
    private ObservableList<Player> olPlayers;
    private GameLoop gameLoop;
    
    public GameManager(ArrayList<Player> players)
    {
        gameLoop = new GameLoop(players);
    }

    public ObservableList<Player> getOlPlayers() {
        return olPlayers;
    }

    public void setOlPlayers(ObservableList<Player> olPlayers) {
        this.olPlayers = olPlayers;
    }
    
}
