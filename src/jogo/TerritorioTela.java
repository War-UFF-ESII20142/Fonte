/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo;

import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

/**
 *
 * @author Claudio
 */
public class TerritorioTela {
    private Circle circulo;
    private Label label;
    private String nome;
    private String continente;
    
    public TerritorioTela()
    {
        setCirculo(new Circle());
        setLabel(new Label());
    }
    
    public TerritorioTela(Circle circle, String nome, String continente)
    {
        setCirculo(circle);
        setContinente(continente);
        circle.setId(continente);
        setLabel(new Label("1"));
        label.setLayoutX(circle.getLayoutX() - (circle.getRadius()/2));
        label.setLayoutY(circle.getLayoutY() - (circle.getRadius()));
        setNome(nome);
    }
    
    public TerritorioTela(TerritorioTela original)
    {
        setCirculo(original.getCirculo());
        setLabel(original.getLabel());
        setNome(original.getNome());
        
    }

    public Circle getCirculo() {
        return circulo;
    }

    public void setCirculo(Circle circulo) {
        this.circulo = circulo;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }
    
    
}
