/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.shape.Circle;
import jogo.TerritorioTela;

/**
 *
 * @author Claudio
 */
public class AdministradorDeArquivo 
{
    public static ArrayList<TerritorioTela> listaTerritorios() throws FileNotFoundException
    {
        ArrayList<TerritorioTela> lista = new ArrayList<>();
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
                Circle circle = new Circle(x,y,10);
                TerritorioTela temp = new TerritorioTela(circle,nomePais,continente);
                lista.add(temp);
            }
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
