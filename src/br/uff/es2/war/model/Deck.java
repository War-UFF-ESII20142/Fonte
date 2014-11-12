/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Hugo Farias
 */
public class Deck {
    private final int NTERRITORIOS = 21;
    private Stack<Carta> cartas;
    private final String formas[] = {"Triângulo", "Quadrado", "Círculo"};
    private ArrayList<Pais> paises;

    public Deck(ArrayList<Pais> countries) {
        cartas = new Stack<>();
        paises = countries;
        generateCards();
    }
    
    private void generateCards(){
        for (int i = 0; i < paises.size(); i++)
            cartas.push(new Carta(paises.get(i).getNome(), formas[i % 3])); //Alternando formas para distribuir
        embaralhar();
    }

    private void embaralhar() {
        int rand1, rand2;
        Carta aux;
        for (int i = 0; i < cartas.size(); i++) {
            rand1 = (int) (Math.random() * cartas.size());
            rand2 = (int) (Math.random() * cartas.size());
            aux = cartas.get(rand1);
            cartas.add(rand1, cartas.get(rand2));
            cartas.add(rand2, aux);
        }
    }
    
    public Carta getCarta(){
        return cartas.pop();
    }
    
}
