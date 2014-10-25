/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war;

import br.uff.es2.war.view.JanelaPrincipal;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author claudio
 */
public class MainClass extends Application
{

    @Override
    public void start(Stage primaryStage) throws Exception {
        WindowManager manager = new WindowManager();
        primaryStage.close();
    }
    
    public static void main(String[] args) 
    {
        launch(args);
    }
}
