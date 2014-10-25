/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war;

import br.uff.es2.war.view.JanelaCarregarJogo;
import br.uff.es2.war.view.JanelaCriacaoJogo;
import br.uff.es2.war.view.JanelaPrincipal;
import javafx.application.Application;
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
    
    public void startMainWindow()
    {
        mainWindow = new JanelaPrincipal();
        mainWindow.setWindowController(this);
        mainWindow.start(new Stage());
    }
    
    public void startCreateGameWindow()
    {
        windowCreateGame = new JanelaCriacaoJogo();
        windowCreateGame.setWindowController(this);
        windowCreateGame.start(new Stage());
    }
    
    public void startLoadGameWindow()
    {
        windowLoadGame = new JanelaCarregarJogo();
        windowLoadGame.setWindowController(this);
        windowLoadGame.start(new Stage());
    }
    
    public void onExit( Stage stage )
    {
        stage.close();
    }
    
    

}