/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.GameManager;
import br.uff.es2.war.dao.DataManager;
import br.uff.es2.war.interfaces.IGameLoop;
import br.uff.es2.war.interfaces.Player;
import br.uff.es2.war.interfaces.iObservable;
import br.uff.es2.war.interfaces.iObserver;
import br.uff.es2.war.util.types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author RulffdaCosta
 */
public class GameLoop implements iObservable, IGameLoop {

    private int numExercitos = 2;
    private ArrayList<Player> players;
    private Iterator<Player> it;
    private int currentIndex;
    private DataManager dataManager;
    private Pais atacante;
    private Pais atacado;
    private int numeroDeTropasAAlocarRodada, numeroDeTropasValonguinho, numeroDeTropasPV, numeroDeTropasGragoata, numeroDeTropasUI;
    private int numeroTropasAtaque;
    private boolean inAttack;
    private GameManager gameController;
    Deck deck;

    public GameLoop() {
    }

    ArrayList<iObserver> observerList;

    public GameLoop(ArrayList<Player> players, DataManager manager, GameManager controller) {
        numeroDeTropasAAlocarRodada = numeroDeTropasGragoata = numeroDeTropasPV = numeroDeTropasUI = numeroDeTropasValonguinho = 0;
        numeroTropasAtaque = 0;
        inAttack = true;
        this.players = players;
        it = players.iterator();
        observerList = new ArrayList<>();
        currentIndex = 0;
        this.dataManager = manager;
        atacante = new Pais("", "", null);
        atacado = new Pais("", "", null);
        this.gameController = controller;
    }

    public void carregaRodadaInicial() {
        ArrayList<Pais> paises = new ArrayList<>(dataManager.getPaises());
        int valor = paises.size() / players.size();
        deck = new Deck(paises);
        for (Player p : players) {
            for (int i = 0; i < valor; i++) {
                int pos = (int) (Math.random() * paises.size());
                p.addPais(paises.get(pos));
                paises.remove(paises.get(pos));
            }
        }

        while (paises.size() > 0) {
            int pos = (int) (Math.random() * players.size());
            Player aux = players.get(pos);
            aux.addPais(paises.get(0));
            paises.remove(paises.get(0));
        }

        if (getCurrentPlayer().getTipo().equals("Bot")) {
            calculaTropasASeremAlocadas();
            ((BotPlayer) (getCurrentPlayer())).processaRodada(this, gameController);
        }

        avisaMudancas();

    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return players.get(currentIndex);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int increaseIndex() {
        currentIndex++;
        if (currentIndex > players.size() - 1) {
            currentIndex = 0;
        }

        avisaMudancas();
        return currentIndex;
    }

    public boolean temTropa() {
        int valor = numeroDeTropasAAlocarRodada + (numeroDeTropasGragoata + numeroDeTropasPV + numeroDeTropasUI + numeroDeTropasValonguinho);
        return valor > 0;
    }

    public void principalLoop() {

        increaseIndex();
        calculaTropasASeremAlocadas();

        //Se tiver 5 cartas, tem que realizar uma troca de cartas
        if (getCurrentPlayer().getCards().size() == 5) {
            gameController.avisaTrocaDeCarta();
        }

        if (getCurrentPlayer().getTipo().equals("Bot")) {
            ((BotPlayer) (getCurrentPlayer())).processaRodada(this, gameController);
        }
        /**
         * while(true){ //enquanto ninguem ganhou. Implementar as checagens de
         * vitoria){ Player currentPlayer = it.next();
         *
         * //if(clicou atacar){ currentPlayer.attack(currentPlayer);
         *
         * //if(clicou comprar cartas) currentPlayer.buyCard();
         *
         * //if(clicou trocar cartas) currentPlayer.tradeCards();
         *
         * //if(clicou finalizar) currentPlayer = it.next();
         *
         * }*
         */
    }

    public void setAtacado(Pais atacado) {
        this.atacado = atacado;
        System.out.println("atacado: " + atacado.getNome());
        if (this.atacante.getDono().getNome().equals(getCurrentPlayer().getNome())) {
            if (this.atacante.getNumeroDeTroopas() > 1) {
                if (inAttack) {
                    processaAtaque(atacante, atacado, numeroTropasAtaque);
                } else {
                    processaRemanejamento(atacante, atacado, numeroTropasAtaque);
                }
            } else {
                System.out.println("Você não possui tropas suficientes para atacar");
            }
        } else {
            System.out.println("O território atacante não lhe pertence");
        }
        //this.processaAtaque(atacante, atacado);

    }

    public boolean processaVencedorAtaque() {
        return false;
    }

    @Override
    public void setAtacante(Pais atacante, int qtdExercito) {
        this.atacante = atacante;
        this.numeroTropasAtaque = qtdExercito;
        System.out.println("atacante: " + atacante.getNome() + " Exercito: " + qtdExercito);

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
    public void avisaMudancas() {
        for (iObserver o : observerList) {
            o.updateGameImage();
        }
    }

    @Override
    public void processaAtaque(Pais atacante, Pais atacado) {
        if (atacante.isVizinho(atacado)) {
            processaAtaque(atacante, atacado, currentIndex);
        } else {
            System.out.println("Esses paises não são vizinhos");
        }
    }

    @Override
    public void distribuiTropas(Pais pais) {
        Player currentPlayer = getCurrentPlayer();
        if (numeroDeTropasValonguinho > 0 && pais.getContinente().getNome().equals(types.sVALONGUINHO)) { //Alternativa pra forçar a ordem: colocar if's separados, de modo que só possa ir pra um continente depois de fechar o outro.
            pais.incrementaNumeroDeTropas();
            numeroDeTropasValonguinho--;
        } else if (numeroDeTropasGragoata > 0 && pais.getContinente().getNome().equals(types.sGRAGOATA)) {
            pais.incrementaNumeroDeTropas();
            numeroDeTropasGragoata--;
        } else if (numeroDeTropasPV > 0 && pais.getContinente().getNome().equals(types.sPRAIAVERMELHA)) {
            pais.incrementaNumeroDeTropas();
            numeroDeTropasPV--;
        } else if (numeroDeTropasUI > 0 && pais.getContinente().getNome().equals(types.sUNIDADEISOLADAS)) {
            pais.incrementaNumeroDeTropas();
            numeroDeTropasUI--;
        } else if (numeroDeTropasAAlocarRodada > 0) {
            if (pais.getDono().getNome().equals(currentPlayer.getNome())) {
                pais.incrementaNumeroDeTropas();
                numeroDeTropasAAlocarRodada--;
                avisaMudancas();
            } else {
                System.out.println("Este pais não te pertence");
            }
        } else {

            System.out.println("Você não possui mais tropas");
        }
        avisaMudancas();
    }

    @Override
    public void calculaTropasASeremAlocadas() {
        Player currentPlayer = getCurrentPlayer();
        numeroDeTropasAAlocarRodada = currentPlayer.numeroDePaises() / 2;
        if (numeroDeTropasAAlocarRodada < 3) {
            numeroDeTropasAAlocarRodada = 3; //min = 3, following the rules
        }
        //Tropas por continente (definidas pelas constantes do código para o WAR UFF):
        for (Continente c : currentPlayer.getMeusContinentes()) {
            if (c.getNome().equals(types.sVALONGUINHO)) {
                numeroDeTropasValonguinho = 2;
            } else if (c.getNome().equals(types.sGRAGOATA)) {
                numeroDeTropasGragoata = 3;
            } else if (c.getNome().equals(types.sPRAIAVERMELHA)) {
                numeroDeTropasPV = 4;
            } else if (c.getNome().equals(types.sUNIDADEISOLADAS)) {
                numeroDeTropasUI = 5;
            }
        }
    }

    public int numeroDeTropasAAlocarRodada() {
        return this.numeroDeTropasAAlocarRodada();
    }

    public Objetivo getObjetivo() {
        return this.getCurrentPlayer().getObjetivo();

    }

    private void orgVetor(int[] num) {
        int j;
        boolean flag = true;
        int temp;
        while (flag) {
            flag = false;
            for (j = 0; j < num.length - 1; j++) {
                if (num[j] > num[j + 1]) {
                    temp = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = temp;
                    flag = true;
                }
            }
        }
    }

    private int[] processaDanos(int numAtacantes, int numDefensores) {
        int baixas[] = new int[2], j = 0, k = 0;
        int dadosAtaque[] = new int[3];
        int dadosDefesa[] = new int[3];
        int valorDado;
        while (numAtacantes != 0) {
            valorDado = (int) (Math.random() * 6 + 1);
            dadosAtaque[k++] = valorDado;
            if (numDefensores > 0) {
                valorDado = (int) (Math.random() * 6 + 1);
                dadosDefesa[j++] = valorDado;
                numDefensores--;
            }
            numAtacantes--;
        }
        orgVetor(dadosAtaque);
        orgVetor(dadosDefesa);
        for (int i = 0; i < 3; i++) {
            if (dadosAtaque[i] > dadosDefesa[i] && dadosDefesa[i] != 0) {
                baixas[0]++;
            } else if (dadosAtaque[i] <= dadosDefesa[i] && dadosAtaque[i] != 0) {
                baixas[1]++;
            }
            System.out.println(dadosAtaque[i] + "<- Ataque " + dadosDefesa[i] + "<-Defesa" + baixas[0] + "<- baixasDefesa" + baixas[1] + "<-baixasAtaque");
        }
        return baixas;
    }

    private void processaAtaque(Pais atacante, Pais atacado, int qtdDeTropas) {
        System.out.println(atacante.getNome() + " atacou " + atacado.getNome() + " com " + qtdDeTropas + " execitos");
        int baixas[], numDefensores;
        if (atacante.isVizinho(atacado) && !atacante.getDono().equals(atacado.getDono())) {
            if (qtdDeTropas > 3) {
                System.out.println("Não se pode atacar com mais de 3 tropas");
            } else if (atacante.getNumeroDeTroopas() - qtdDeTropas < 1) {
                System.out.println("Uma tropa deve permanecer em guarda");
            } else {
                if (atacado.getNumeroDeTroopas() > 3) {
                    numDefensores = 3;
                } else {
                    numDefensores = atacado.getNumeroDeTroopas();
                }
                baixas = processaDanos(qtdDeTropas, numDefensores);
                System.out.println(baixas[1] + " atacante morreram, e " + baixas[0] + "atacado morreram\n\n");
                numDefensores = atacado.getNumeroDeTroopas();
                if (numDefensores - baixas[0] > 0) {
                    atacado.setTropas(atacado.getNumeroDeTroopas() - baixas[0]);
                    atacante.setTropas(atacante.getNumeroDeTroopas() - baixas[1]);
                } else {
                    atacante.getDono().setNewTroopa(true);
                    Player perdedor = atacado.getDono();
                    atacado.getDono().remove(atacado);
                    atacado.setDono(atacante.getDono());
                    atacante.getDono().addPais(atacado);
                    atacado.setTropas(qtdDeTropas - baixas[1]);
                    atacante.setTropas(atacante.getNumeroDeTroopas() - qtdDeTropas);
                    if (getCurrentPlayer().checaObjetivo()) {
                        gameController.finalizaJogo();
                    } else {
                        if (perdedor.getMeusPaises() == null) {
                            for (int i = 0; i < perdedor.getCards().size(); i++) {
                                atacante.getDono().getCards().add(perdedor.getCards().get(i));
                            }
                            perdedor.setCards(null);
                        }
                    }
                    System.out.println(qtdDeTropas + " Bravos soldados dominaram um novo país\n\n");
                }

                this.avisaMudancas();
            }
        } else {
            System.out.println("Esses paises não são vizinhos, ou é seu país");
        }
    }

    public void setInAttack(boolean inAttack) {
        this.inAttack = inAttack;
    }

    private void processaRemanejamento(Pais paisFrom, Pais paisTo, int militarNumber) {
        paisFrom.setTropas(paisFrom.getNumeroDeTroopas() - militarNumber);
        paisTo.setTropas(paisTo.getNumeroDeTroopas() + militarNumber);
        this.avisaMudancas();
        if (getCurrentPlayer().checaObjetivo()) {
            gameController.finalizaJogo();
        }
    }

    public int getQtdTropa() {
        return numeroDeTropasAAlocarRodada;
    }

    public int trocaCarta(ArrayList<Carta> cartas) {
        int n = 3; // Tipos de Formas diferentes
        int[] troca = new int[n];
        final String formas[] = {"Triângulo", "Quadrado", "Círculo"};
        int acm = 0;
        for (int i = 0; i < cartas.size(); i++) {
            for (int j = 0; j < n; j++) {
                if (cartas.get(i).getForma().equals(formas[j])) {
                    troca[j]++;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (troca[i] == 3) {
                acm = 3;
                break;
            } else if (troca[i] == 1) {
                acm++;
            }
        }
        if (acm == 3) {
            numExercitos += 2;
            cartas.stream().forEach((carta) -> {
                for (int j = 0; j < players.get(currentIndex).getMeusPaises().size(); j++) {
                    if (players.get(currentIndex).getMeusPaises().get(j).getNome().equals(carta.getPais())) {
                        players.get(currentIndex).getMeusPaises().get(j).setTropas(players.get(currentIndex).getMeusPaises().get(j).getNumeroDeTroopas() + 2);
                    }
                }
            });
            return numExercitos;
        } else {
            return -1; //ERROR
        }
    }

    public Deck getDeck() {
        return deck;
    }

}
