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
    private DataManager dataManager;
    private boolean ataque, distribuiTropas, remanejaTropas, auxiliarNaDistribuicao;
    private boolean inAttack;
    private boolean inRelocation;
    private Pais paisAtacante;
    
    
    public GameManager(ArrayList<Player> players,DataManager data, JanelaJogo janelaJogo)
            
    {
        auxiliarNaDistribuicao = true;
        ataque = true;
        inAttack = false;
        inRelocation = false;
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
        inRelocation = false;
        gameLoop.principalLoop();
    }
    
    public void attack(){
       if(!inAttack)
       {
           distribuiTropas = false;
           inAttack = true;
           gameLoop.setInAttack(true);
       }
       else
       {
            inAttack = false;
            gameLoop.setInAttack(inAttack);
            inRelocation = true;
       }
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
    
    public void finalizaAtaque()
    {
        inAttack = false;
        inRelocation = true;
    }
    
    public void fazAtaque(Pais pais)
    {
        if(ataque)
        {
            if(pais.getDono().getNome().equals(gameLoop.getCurrentPlayer().getNome()))
            {
                //pais que vai atacar 
                this.paisAtacante = pais;
                ataque = false;
                janelaJogo.getQtdExercito(pais);
            }
        }else
        {
            //pais que será atacado
            if(inRelocation)
            {
                if(pais.getDono().getNome().equals(gameLoop.getCurrentPlayer().getNome()))
                {
                    gameLoop.setAtacado(pais);
                    ataque = true;
                }
            }else
            {
                gameLoop.setAtacado(pais);
                ataque = true;
            }
            
            
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
        
        if(inAttack || inRelocation)
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
    
    public boolean isInAttack()
    {
        return inAttack;
    }
    
    public void setAtaque(boolean ataque)
    {
        System.out.println(ataque + "  " + this.ataque);
        this.ataque = ataque;
    }
}
