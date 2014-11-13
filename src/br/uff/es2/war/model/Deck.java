/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import br.uff.es2.war.interfaces.Player;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author Hugo Farias
 */
public class Deck {

    private Stack<Carta> cartas;
    private final String formas[] = {"Triângulo", "Quadrado", "Círculo"};
    private ArrayList<Pais> paises;

    public Deck(ArrayList<Pais> countries) {
        cartas = new Stack<>();
        paises = countries;
        generateCards();
    }

    private void generateCards() {
        for (int i = 0; i < paises.size(); i++) {
            cartas.push(new Carta(paises.get(i).getNome(), formas[i % 3])); //Alternando formas para distribuir
        }
        Collections.shuffle(cartas);
    }

    public Carta getCarta() {
        return cartas.pop();
    }
}
