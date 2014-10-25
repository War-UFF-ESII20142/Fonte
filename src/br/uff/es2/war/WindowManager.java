/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.uff.es2.war;

import br.uff.es2.war.view.JanelaCriacaoJogo;
import br.uff.es2.war.view.JanelaPrincipal;
import javafx.stage.Stage;

/**
 *
 * @author csouza
 */
public class WindowManager 
{
    private JanelaPrincipal mainWindow;
    private JanelaCriacaoJogo windowCreateGame;
    
    public WindowManager()
    {
        mainWindow = new JanelaPrincipal();
        mainWindow.start(new Stage());
    }
    
    public void startApplication()
    {
        
    }
    
}
