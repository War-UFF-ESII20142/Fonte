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
import java.util.Scanner;
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
    private DataManager dataManager;
    private boolean ataque, distribuiTropas, remanejaTropas, auxiliarNaDistribuicao;
    private boolean inAttack;
    private Pais paisAtacante;
    
    
    public GameManager(ArrayList<Player> players,DataManager data, JanelaJogo janelaJogo)
            
    {
        auxiliarNaDistribuicao = true;
        ataque = true;
        inAttack = false;
        distribuiTropas = true;
        remanejaTropas = false;
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
        this.distribuiTropas = true;
        inAttack = false;
        gameLoop.principalLoop();
    }
    
    public void attack(){
       if(!inAttack)
       {
           distribuiTropas = false;
       }
       inAttack = !inAttack;
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
    
    /*
    * Informa a quantidade de soldados selecionados
    */
    public void informaQtdSoldadosSelec(int qtdSoldados)
    {
            gameLoop.setAtacante(paisAtacante,qtdSoldados);
    }
    
    public void fazAtaque(Pais pais)
    {
        if(ataque)
        {
           //pais que vai atacar 
            this.paisAtacante = pais;
            janelaJogo.getQtdExercito(pais);
            ataque = false;
        }else
        {
            //pais que será atacado
            gameLoop.setAtacado(pais);
            ataque = true;
            
            
        }
        
    }
    
    public void distribuicaoDeTropas(Pais pais){
        if(gameLoop.temTropa())
        {
            gameLoop.distribuiTropas(pais);
        }
        else
        {
            janelaJogo.avisaAcabouTropas();
            //terminaDistruibuicao();
        }
    }   
    
    
    public void fazCoisa(Pais pais)
    {
        if(distribuiTropas){
            if(auxiliarNaDistribuicao){
                gameLoop.calculaTropasASeremAlocadas();
                auxiliarNaDistribuicao = false;
            }
            distribuicaoDeTropas(pais);
        }
        
        if(inAttack)
        {
            fazAtaque(pais);
        }
    }
    
    public void terminaDistruibuicao(){
        this.distribuiTropas = false;
    }
    
    public String mostrarObjetivo(){
        return this.gameLoop.getObjetivo();
    }
    
}
