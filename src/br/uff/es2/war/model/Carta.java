/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author claudio
 */
public class Carta {
//    private SimpleIntegerProperty id;
//    private SimpleStringProperty descricao;
//    
//    public Carta(int id, String descricao)
//    {
//        this.id = new SimpleIntegerProperty(id);
//        this.descricao = new SimpleStringProperty(descricao);
//    }
//
//    public int getId() {
//        return id.get();
//    }
//
//    public void setId(int id) {
//        this.id.set(id);
//    }
//
//    public String getDescricao() {
//        return descricao.get();
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao.set(descricao);
//    }

    //private BooleanProperty escolhida;
    //private StringProperty continente;
    private StringProperty pais;
    private StringProperty forma;

    public Carta(/*String continente,*/ String pais, String forma) {

        //this.escolhida = new SimpleBooleanProperty(escolhida);

        //this.continente = new SimpleStringProperty(continente);

        this.pais = new SimpleStringProperty(pais);

        this.forma = new SimpleStringProperty(forma);

//        this.escolhida.addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
//                //System.out.println("Marcou");
//            }
//        });
    }
//
//    /**
//     * @return the escolhida
//     */
//    public boolean getEscolhida() {
//        return escolhida.get();
//    }
//
//    /**
//     * @param escolhida the escolhida to set
//     */
//    public void setEscolhida(boolean escolhida) {
//        this.escolhida.set(escolhida);
//    }

    /**
     * @return the continente
     */
    //public String getContinente() {
    //    return continente.get();
    //}

    /**
     * @param continente the continente to set
     */
    //public void setContinente(String continente) {
    //    this.continente.set(continente);
    //}

    /**
     * @return the pais
     */
    public String getPais() {
        return pais.get();
    }

    /**
     * @param pais the pais to set
     */
    public void setPais(String pais) {
        this.pais.set(pais);
    }

    /**
     * @return the forma
     */
    public String getForma() {
        return forma.get();
    }

    /**
     * @param forma the forma to set
     */
    public void setForma(String forma) {
        this.forma.set(forma);
    }
}

//package br.uff.es2.war.model;
//
//import javafx.beans.property.SimpleIntegerProperty;
//import javafx.beans.property.SimpleStringProperty;
//
///**
// *
// * @author claudio
// */
//public class Carta {
//    private SimpleIntegerProperty id;
//    private SimpleStringProperty descricao;
//    
//    public Carta(int id, String descricao)
//    {
//        this.id = new SimpleIntegerProperty(id);
//        this.descricao = new SimpleStringProperty(descricao);
//    }
//
//    public int getId() {
//        return id.get();
//    }
//
//    public void setId(int id) {
//        this.id.set(id);
//    }
//
//    public String getDescricao() {
//        return descricao.get();
//    }
//
//    public void setDescricao(String descricao) {
//        this.descricao.set(descricao);
//    }
//    
//}
