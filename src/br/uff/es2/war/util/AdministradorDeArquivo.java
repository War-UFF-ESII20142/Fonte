/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.util;

import br.uff.es2.war.dao.DataManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.shape.Circle;
import br.uff.es2.war.view.TerritorioTela;
import javafx.scene.Node;

/**
 *
 * @author Claudio
 */
public class AdministradorDeArquivo 
{
    private static ArrayList<TerritorioTela> lista; 
    public static ArrayList<TerritorioTela> listaTerritorios(DataManager dataManager) throws FileNotFoundException
    {
        lista = new ArrayList<>();
        lista.add(new TerritorioTela(new Circle(412,513,15),"Fisica","PraiaVermelha"));
        lista.add(new TerritorioTela(new Circle(474,434,15),"Geofisica","PraiaVermelha")); 
        lista.add(new TerritorioTela(new Circle(443,348,15),"Computacao","PraiaVermelha")); 
        lista.add(new TerritorioTela(new Circle(381,406,15),"Arquitetura","PraiaVermelha")); 
        lista.add(new TerritorioTela(new Circle(357,463,15),"Engenharia","PraiaVermelha")); 
        lista.add(new TerritorioTela(new Circle(405,234,15),"Veterinaria","UnidadeIsolada")); 
        lista.add(new TerritorioTela(new Circle(353,106,15),"HUAP","UnidadeIsolada")); 
        lista.add(new TerritorioTela(new Circle(334,161,15),"Reitoria","UnidadeIsolada")); 
        lista.add(new TerritorioTela(new Circle(356,219,15),"Direito","UnidadeIsolada")); 
        lista.add(new TerritorioTela(new Circle(375,287,15),"IACS","UnidadeIsolada")); 
        lista.add(new TerritorioTela(new Circle(272,511,15),"Historia","Gragoata")); 
        lista.add(new TerritorioTela(new Circle(236,464,15),"Filosofia","Gragoata")); 
        lista.add(new TerritorioTela(new Circle(235,392,15),"Bandejao","Gragoata")); 
        lista.add(new TerritorioTela(new Circle(189,319,15),"EducacaoFisica","Gragoata")); 
        lista.add(new TerritorioTela(new Circle(235,297,15),"Pedagogia","Gragoata" ));
        lista.add(new TerritorioTela(new Circle(49,199,15),"Matematica","Valonguinho")); 
        lista.add(new TerritorioTela(new Circle(102,151,15),"Odontologia","Valonguinho")); 
        lista.add(new TerritorioTela(new Circle(179,124,15),"Quimica","Valonguinho")); 
        lista.add(new TerritorioTela(new Circle(188,204,15),"Biologia","Valonguinho")); 
        lista.add(new TerritorioTela(new Circle(151,254,15),"Administracao","Valonguinho")); 
        lista.add(new TerritorioTela(new Circle(114,206,15),"STI","Valonguinho")); 
        
        /*Scanner in = new Scanner(new File("paisesListaCoordenadas.txt"));
        while(in.hasNext())
        {
        String nomePais = in.next();
        //int idPais = Integer.parseInt(in.next());
        //nomePais = nomePais+" "+idPais;
        String continente = in.next();
        double x = Double.parseDouble(in.next());
        double y = Double.parseDouble(in.next());
        Circle circle = new Circle(x,y,15);
        circle.setStyle("-fx-fill:"+"white");
        TerritorioTela temp = new TerritorioTela(circle,nomePais,continente);
        temp = temp.setPais(dataManager.getPaisByNome(nomePais, continente));
        lista.add(temp);
        
        }*/
        System.out.println(lista.size());
        return lista;
    }
    
    public static void main(String[] args) throws FileNotFoundException 
    {
        Scanner in = new Scanner(new File("Arquivo de territórios.txt"));
        while(in.hasNext())
        {
            String continente = in.next();
            int qtdPaises = Integer.parseInt(in.next());
            for(int i = 0;i<qtdPaises;i++)
            {
                String nomePais = in.next();
                double x = Double.parseDouble(in.next());
                double y = Double.parseDouble(in.next());
            }
        }
    }
}
