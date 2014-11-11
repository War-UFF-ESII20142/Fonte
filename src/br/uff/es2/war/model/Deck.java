/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.util.ArrayList;

/**
 *
 * @author Hugo Farias
 */
public class Deck {
    private final int NTERRITORIOS = 21;
    private ArrayList<Carta> cartas;
    private final String formas[] = {"Triângulo", "Quadrado", "Círculo"};

    public Deck() {
        cartas = new ArrayList<Carta>(NTERRITORIOS);
    }
    
    public void generateCards(){
        for (int i = 0; i < cartas.size(); i++) {
            cartas[i] = new Carta(null, null, formas[i%3]);
        }
    }
    
}
