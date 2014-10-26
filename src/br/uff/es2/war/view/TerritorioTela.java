/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.view;

import br.uff.es2.war.model.Pais;
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
    private Pais pais;
    
    public TerritorioTela()
    {
        this.circulo = new Circle();
        this.label = new Label();
    }
    
    public TerritorioTela(Circle circle, String nome, String continente)
    {
        this.circulo = circle;
        this.continente = continente;
        label = new Label("1");
        label.setLayoutX(circle.getCenterX() - (circle.getRadius()/2));
        label.setLayoutY(circle.getCenterY() - (circle.getRadius()));
        this.nome = nome;
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
