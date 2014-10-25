/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war;

import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iWindow;
import br.uff.es2.war.view.JanelaCarregarJogo;
import br.uff.es2.war.view.JanelaCriacaoJogo;
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
    
    public void startGamePlay()
    {
        windowGame = new JanelaJogo();
        windowGame.start(new Stage());
        
        ArrayList<Player> players = (ArrayList<Player>)windowCreateGame.getPlayerList();
        
        GameManager gameManager = new GameManager(players);
        
        windowGame.setGameControler(gameManager);
    }
    
    public void onExit( Stage stage )
    {
        stage.close();
    }

}
