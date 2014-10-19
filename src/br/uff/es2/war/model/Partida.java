/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author claudio
 */
public class Partida {
    private SimpleIntegerProperty id;
    private Date date;
    
    public Partida(int id, Date date)
    {
        this.id = new SimpleIntegerProperty(id);
        this.date = date;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
    
    
}
