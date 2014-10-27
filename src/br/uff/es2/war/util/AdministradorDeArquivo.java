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
        Scanner in = new Scanner(new File("paisesListaCoordenadas.txt"));
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
            temp.setPais(dataManager.getPais(nomePais, continente));
            lista.add(temp);

        }
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
