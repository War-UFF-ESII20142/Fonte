/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.interfaces;

import br.uff.es2.war.model.Pais;
import java.util.ArrayList;

/**
 *
 * @author RulffdaCosta
 */
public interface IGameLoop {
    
    public void carregaRodadaInicial();
    public ArrayList<Player> getPlayers();
    public Player getCurrentPlayer();
    public int getCurrentIndex();
    public int increaseIndex();
    public void principalLoop();
    public void setAtacado(Pais atacado);
    public void setAtacante(Pais atacante,int qtdExercito);
    public void addObserver(iObserver observer);
    public void removeObserver(iObserver observer);
    public void avisaMudancas();
    public void processaAtaque(Pais atacante, Pais atacado);
    public void distribuiTropas (Pais pais);
    public void calculaTropasASeremAlocadas();
    
    
    
}
