/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.GameManager;
import br.uff.es2.war.interfaces.Player;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author claudio
 */
public class BotPlayer implements Player {

    private SimpleStringProperty nome = new SimpleStringProperty();
    private SimpleStringProperty cor = new SimpleStringProperty();
    private SimpleStringProperty tipo = new SimpleStringProperty();
    private ArrayList<Carta> cards = new ArrayList<>();
    private ArrayList<Pais> meusPaises;
    private Objetivo objetivo;
    private boolean newTroopa;
    private int numJogadas;

    public BotPlayer(String nome, String cor, String tipo) {
        newTroopa = false;
        this.nome.set(nome);
        this.cor.set(cor);
        this.tipo.set(tipo);
        this.meusPaises = new ArrayList<>();
        this.objetivo = new Objetivo(0,"");
        this.numJogadas = 0;
    }

    /**
     * @return the tipo
     */
    @Override
    public String getTipo() {
        return tipo.get();
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo.set(tipo);
    }
    
    

    @Override
    public void attack(Player player) {
    }

    @Override
    public void buyCard() {
    }

    @Override
    public void tradeCards() {
    }

    @Override
    public String getNome() {
        return nome.get();
    }

    @Override
    public String getCor() {
        return cor.get();
    }

    @Override
    public void finalize() {
    }

    @Override
    public ArrayList<Pais> getMeusPaises() {
        return this.meusPaises;
    }

    @Override
    public void setMeusPaises(ArrayList<Pais> meusPaises) {
        this.meusPaises = meusPaises;
    }

    @Override
    public void addPais(Pais pais) {
        meusPaises.add(pais);
        pais.setDono(this);
    }

    @Override
    public void addAllPaises(Pais... paises) {
        for (Pais p : paises) {
            meusPaises.add(p);
            p.setDono(this);
        }
    }

    @Override
    public void remove(Pais pais) {
        meusPaises.remove(pais);
    }

    public int numeroDePaises() {
        return meusPaises.size();
    }

    @Override
    public ArrayList<Carta> getCards() {
        return this.cards;
    }

    @Override
    public void setCards(ArrayList<Carta> cards) {
        this.cards = cards;
    }

    @Override
    public Objetivo getObjetivo() {
        return this.objetivo;
    }

    @Override
    public void setObjetivo(Objetivo objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public boolean checaObjetivo() {
        return this.objetivo.checaObjetivo(this);
    }

    @Override
    public ArrayList<Continente> getMeusContinentes() {
        ArrayList<Continente> conts = new ArrayList<>(), aux = new ArrayList<>();
        System.out.println("meusPaises.size(): " + meusPaises.size());
        for (Pais mP : meusPaises) {
            if (!conts.contains(mP.getContinente())) {
                conts.add(mP.getContinente()); //add continentes onde tenho países, sem repetir
            }
        }
        boolean isAll;
        for (Continente c : conts) {
            isAll = true;
            for (Pais p : c.getPaises()) {
                if (!p.getDono().getNome().equals(nome.get())/*!getMeusPaises.contains(p)*/) {
                    isAll = false;
                }
            }
            if (!isAll) {
                aux.add(c); //continentes os quais possuo algum país, mas não todos
            }
        }
        for (Continente c : aux) {
            conts.remove(c);
        }
        return conts;
    }
    
    public void processaRodada(GameLoop gameLoop,GameManager controller)
    {
        controller.setIsBot(true);
        //faz troca de cartas

        //Adiciona exercitos
        addExercitos(gameLoop);

        //faz ataque
        //gameLoop.setInAttack(true);
        numJogadas = 0;
        fazAtaque(gameLoop,controller);
        numJogadas = 0;
        
        //redistribui exercitos
        controller.roundTerminou();
        controller.setIsBot(false);
    }
    
    private void addExercitos(GameLoop gameLoop) {
        ArrayList<Continente> continentes = getMeusContinentes();

        if (continentes.size() != 0) {
            for (Continente c : continentes) {
                Pais p = paisMaisFracoPorContinente(c);
                System.out.println("To aquii!!!");
                gameLoop.distribuiTropas(p);
            }
        }

        while (gameLoop.temTropa()) {
            //pega pais com menos exercito
            Pais p = paisMaisFraco();
            System.out.println(p.getNome());
            gameLoop.distribuiTropas(p);
        }
    }
    
    private void fazAtaque(GameLoop gameLoop,GameManager controller)
    {
        gameLoop.setInAttack(true);
        Pais pa = temPaisParaAtaque();
        while(pa != null && numJogadas < 5)
        {
            Pais atacante = new Pais("","",null);
            Pais atacado = new Pais("","",null);
            int max = 1;
            
            List<Pais> list = new CopyOnWriteArrayList<Pais>(meusPaises);
            System.out.println(list.size());
            
            
            for(Pais vizinho :pa.getVizinhos())
            {
                if(pa.getNumeroDeTroopas() >= vizinho.getNumeroDeTroopas() &&
                   pa.getNumeroDeTroopas() > max &&
                  !vizinho.getDono().getNome().equals(this.nome.get()) && 
                   pa.getNumeroDeTroopas() > 1)
                {
                    atacante = pa;
                    atacado = vizinho;
                    max = pa.getNumeroDeTroopas();
                    int qtdSoldado = (pa.getNumeroDeTroopas() > 3)?3:pa.getNumeroDeTroopas()-1;
                    controller.fazAtaque(atacante);
                    controller.informaQtdSoldadosSelec(qtdSoldado);
                    controller.fazAtaque(atacado);
                    /*gameLoop.setAtacante(atacante, qtdSoldado);
                    gameLoop.setAtacado(atacado);*/
                }else if(!vizinho.getDono().getNome().equals(this.nome.get()) && 
                          pa.getNumeroDeTroopas() > 1)
                {
                    atacante = pa;
                    atacado = vizinho;
                    max = pa.getNumeroDeTroopas();
                    int qtdSoldado = (pa.getNumeroDeTroopas() > 3)?3:pa.getNumeroDeTroopas()-1;
                    controller.fazAtaque(atacante);
                    controller.informaQtdSoldadosSelec(qtdSoldado);
                    controller.fazAtaque(atacado);
                }
                
            }
            numJogadas++;
            pa = temPaisParaAtaque();
        }
    }

    private void espalhaExercito(GameLoop gameLoop) {
    }

    private Pais paisMaisFraco() {
        Pais pTemp = new Pais("", "", null);

        int min = 10000;

        for (Pais p : meusPaises) {
            if (p.getNumeroDeTroopas() < min) {
                min = p.getNumeroDeTroopas();
                pTemp = p;
            }
        }

        return pTemp;
    }

    private Pais paisMaisFracoPorContinente(Continente continente) {
        Pais pTemp = new Pais("", "", null);

        int min = 10000;

        for (Pais p : meusPaises) {
            if (p.getContinente().getNome().equals(continente.getNome())) {
                if (p.getNumeroDeTroopas() < min) {
                    min = p.getNumeroDeTroopas();
                    pTemp = p;
                }
            }
        }

        return pTemp;
    }

    private Pais temPaisParaAtaque() {
        for(Pais p : meusPaises) {
            for(Pais vizinho : p.getVizinhos()) {
                if(!vizinho.getDono().getNome().equals(p.getDono().getNome())) {
                    if(p.getNumeroDeTroopas() > 1) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean getNewTroopa() {
        return newTroopa; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setNewTroopa(boolean novaTropa) {
        newTroopa = novaTropa; //To change body of generated methods, choose Tools | Templates.
    }
}
