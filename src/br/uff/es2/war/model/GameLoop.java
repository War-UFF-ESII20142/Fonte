/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.IGameLoop;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObservable;
import br.uff.es2.war.interfaces.iObserver;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop implements iObservable, IGameLoop{
    
    
    private ArrayList<Player> players;
    private Iterator<Player> it;
    private int currentIndex;
    private DataManager dataManager;
    private Pais atacante;
    private Pais atacado;
    private int numeroDeTropasAAlocarRodada;
    private int numeroTropasAtaque;

    public GameLoop() {
    }
    
    ArrayList<iObserver> observerList;
    
    
    public GameLoop(ArrayList<Player> players,DataManager manager){
        numeroDeTropasAAlocarRodada = 0;
        numeroTropasAtaque = 0;
        this.players = players;
        it = players.iterator();
        observerList = new ArrayList<>();
        currentIndex = 0;
        this.dataManager = manager;
        atacante = new Pais("", "", null);
        atacado = new Pais("", "", null);
    }
    
    public void carregaRodadaInicial()
    {
        ArrayList<Pais> paises = new ArrayList<>(dataManager.getPaises());
        int valor = paises.size()/players.size();
        
        for(Player p:players)
        {
            for(int i = 0;i<valor;i++)
            {
                int pos = (int)(Math.random()*paises.size());
                p.addPais(paises.get(pos));
                paises.remove(paises.get(pos));
            }
        }
        
        while(paises.size() > 0)
        {
            int pos = (int)(Math.random()*players.size());
            Player aux = players.get(pos);
            aux.addPais(paises.get(0));
            paises.remove(paises.get(0));
        }
        
        avisaMudancas();
        
    }
    
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    
    public Player getCurrentPlayer()
    {
        return players.get(currentIndex);
    }
    
    public int getCurrentIndex()
    {
        return currentIndex;
    }
    
    public int increaseIndex()
    {
        currentIndex++;
        if(currentIndex > players.size()-1) currentIndex = 0;
        avisaMudancas();
        return currentIndex;
    }
    
    public boolean temTropa()
    {
        return numeroDeTropasAAlocarRodada > 0;
    }
    
    public void principalLoop()
    {
        
        increaseIndex();
        calculaTropasASeremAlocadas();
        /**
        while(true){  //enquanto ninguem ganhou. Implementar as checagens de vitoria){
            Player currentPlayer = it.next();
            
            //if(clicou atacar){
            currentPlayer.attack(currentPlayer);
            
            //if(clicou comprar cartas)
            currentPlayer.buyCard();
            
            //if(clicou trocar cartas)
            currentPlayer.tradeCards();
            
            //if(clicou finalizar)
            currentPlayer = it.next();
          
        }**/
    }
    
    public void setAtacado(Pais atacado)
    {
        this.atacado = atacado;
        System.out.println("atacado: "+atacado.getNome());
        if (this.atacante.getDono().getNome().equals(getCurrentPlayer().getNome())) {
            if(this.atacante.getNumeroDeTroopas() > 1){
                processaAtaque(atacante, atacado, numeroTropasAtaque);
            }else{
                System.out.println("Você não possui tropas suficientes para atacar");
            }
        }else{
            System.out.println("O território atacante não lhe pertence");
        }
        this.processaAtaque(atacante, atacado);

    }
    
    public boolean processaVencedorAtaque(){
        return  false;
    }
    
    public void setAtacante(Pais atacante,int qtdExercito)
    {
        this.atacante = atacante;
        this.numeroTropasAtaque = qtdExercito;
        System.out.println("atacante: "+atacante.getNome()+" Exercito: "+qtdExercito);
       
    }
    

    @Override
    public void addObserver(iObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(iObserver observer) {
        observerList.remove(observer);
    }
    
    @Override
    public void avisaMudancas()
    {
        for(iObserver o : observerList)
        {
            o.updateGameImage();
        }
    }
    
    public void processaAtaque(Pais atacante, Pais atacado){
        if(atacante.isVizinho(atacado)){
            processaAtaque(atacante, atacado, currentIndex);
        }else{
            System.out.println("Esses paises não são vizinhos");
        }
    } 
    
    @Override
    public void distribuiTropas (Pais pais){
        Player currentPlayer = getCurrentPlayer();
        if(numeroDeTropasAAlocarRodada > 0){
        if(pais.getDono().getNome().equals(currentPlayer.getNome())){
            pais.incrementaNumeroDeTropas();
            numeroDeTropasAAlocarRodada--;
            avisaMudancas();
        }else{
            System.out.println("Este pais não lhe pertence");
        }
        }else{
            
            System.out.println("Você não possui mais tropas");
        }
        avisaMudancas();
    }
    
    @Override
    public void calculaTropasASeremAlocadas(){
        Player currentPlayer = getCurrentPlayer();
        numeroDeTropasAAlocarRodada = currentPlayer.numeroDePaises()/2;
    }
    
    public int numeroDeTropasAAlocarRodada(){
        return this.numeroDeTropasAAlocarRodada();
    }
    
    public String getObjetivo(){
        return this.getCurrentPlayer().getObjetivo();
    }
    
    private void orgVetor(int valorDado, int[] dados) {
        for (int j = 0; j < 3; j++) {
            if (valorDado > dados[j]) {
                for (int k = j; k < 2; k++) {
                    dados[k + 1] = dados[k];
                }
            }
            dados[j] = valorDado;
            break;
        }
    }
 
    private int[] processaDanos(int numAtacantes, int numDefensores) {
        int baixas[] = new int[2];
        int dadosAtaque[] = new int[3];
        int dadosDefesa[] = new int[3];
        int valorDado;
        while (numAtacantes != 0) {
            valorDado = (int) (Math.random() * 6 + 1);
            orgVetor(valorDado, dadosAtaque);
            orgVetor(valorDado, dadosDefesa);
            numAtacantes--;
        }
        for (int i = 0; i < 3; i++) {
            if(dadosAtaque[i] > dadosDefesa[i] && dadosDefesa[i] != 0) baixas[0]++;
            else if(dadosAtaque[i] <= dadosDefesa[i] && dadosAtaque[i] != 0) baixas[1]++;
        }
        return baixas;
    }
 
    public void processaAtaque(Pais atacante, Pais atacado, int qtdDeTropas) {
        int baixas[], numDefensores;
        if (atacante.isVizinho(atacado)) {
            if (qtdDeTropas > 3) {
                System.out.println("Não se pode atacar com mais de 3 tropas");
            } else if (atacante.getNumeroDeTroopas() - qtdDeTropas < 1) {
                System.out.println("Uma tropa deve permanecer em guarda");
            } else {
                if(atacado.getNumeroDeTroopas() > 3) numDefensores = 3;
                else numDefensores = atacado.getNumeroDeTroopas();
                baixas = processaDanos(qtdDeTropas, numDefensores);
                atacante.setTropas(atacante.getNumeroDeTroopas() - baixas[0]);
                numDefensores = atacado.getNumeroDeTroopas();
                if(numDefensores - baixas[1] > 0){
                    atacado.setTropas(atacado.getNumeroDeTroopas() - baixas[1]);
                }else{
                    atacado.setDono(atacante.getDono());
                    System.out.println("Digite o número de tropas para transferir.");
                }
                this.avisaMudancas();
            }
        } else {
            System.out.println("Esses paises não são vizinhos");
        }
    }
}
