/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war;

import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.util.Tools;
import br.uff.es2.war.view.JanelaCarregarJogo;
import br.uff.es2.war.view.JanelaCriacaoJogo;
import br.uff.es2.war.view.JanelaFimJogo;
import br.uff.es2.war.view.JanelaJogo;
import br.uff.es2.war.view.JanelaPrincipal;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

/**
 *
 * @author csouza
 */
public class WindowManager 
{
    private JanelaPrincipal mainWindow;
    private JanelaCriacaoJogo windowCreateGame;
    private JanelaCarregarJogo windowLoadGame;
    private JanelaJogo windowGame;
    private JanelaFimJogo windowWinner;
    
    public WindowManager()
    {
        mainWindow = new JanelaPrincipal();
        mainWindow.setWindowController(this);
        mainWindow.start(new Stage());
    }
    
    public void startMainWindow(iWindow window)
    {
        mainWindow = new JanelaPrincipal();
        mainWindow.setWindowController(this);
        mainWindow.start(new Stage());
        window.getStage().close();
    }
    
    public void startCreateGameWindow(iWindow window)
    {
        windowCreateGame = new JanelaCriacaoJogo();
        windowCreateGame.setWindowController(this);
        windowCreateGame.start(new Stage());
        window.getStage().close();
    }
    
    public void startLoadGameWindow(iWindow window)
    {
        windowLoadGame = new JanelaCarregarJogo();
        windowLoadGame.setWindowController(this);
        windowLoadGame.start(new Stage());
        window.getStage().close();
    }
    
    public void startGamePlay(iWindow window)
    {
        ObservableList<Player> players = windowCreateGame.getPlayerList();
        ArrayList<Player> arrayPlayer = Tools.convertObservableToArrayList(players);
        
        DataManager dataManager = new DataManager();
        
        windowGame = new JanelaJogo(dataManager,this);
        windowGame.setDataManager(dataManager);
        GameManager gameManager = new GameManager(arrayPlayer,dataManager, windowGame);
        
        windowGame.setGameControler(gameManager);
        windowGame.setGameLoop(gameManager.getGameLoop());
        windowGame.start(new Stage());
        //windowGame.updateGameImage();
        
        gameManager.getGameLoop().carregaRodadaInicial();
        
        window.getStage().close();
    }
    
    public void mostraJanelaFimJogo(iWindow window, Player winner)
    {
        windowWinner = new JanelaFimJogo(winner,this);
        window.getStage().close();
    }
    
    public void onExit( Stage stage )
    {
        stage.close();
    }

}
