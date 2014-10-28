/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.util;

import br.uff.es2.war.interfaces.Player;
import java.util.ArrayList;
import javafx.collections.ObservableList;

/**
 *
 * @author claudio
 */
public class Tools 
{
    public static ArrayList<Player> convertObservableToArrayList(ObservableList<Player> list)
    {
        ArrayList<Player> temp = new ArrayList<>();
        
        for(Player p : list)
        {
            temp.add(p);
        }
        
        return temp;
    }
    
    public static String convertCorToColor(String cor)
    {
        switch(cor)
        {
            case "Amarelo":
                return "yellow";
            case "Verde":
                return "green";
            case "Vermelho":
                return "orange";
            case "Preto":
                return "gray";
            case "Azul":
                return "lightblue";
            case "Branco":
                return "white";
            default:
                return "";
            
        }
    }
}
