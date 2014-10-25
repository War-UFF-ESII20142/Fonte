/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;

import javafx.stage.Stage;

/**
 *
 * @author claudio
 */
public interface iWindow 
{
    public Stage getStage();
    public void setStage( Stage stage );
}
