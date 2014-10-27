/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.es2.war.dao;

import br.uff.es2.war.model.Continente;
import br.uff.es2.war.model.Pais;
import br.uff.es2.war.util.types;
import java.util.ArrayList;

/**
 *
 * @author claudio
 */
public class DataManager 
{
    private ArrayList<Pais> paises;
    private ArrayList<Continente> continentes;
    
    public DataManager()
    {
        paises = new ArrayList<>();
        continentes = new ArrayList<>();
        carregaDadosBd();
    }
    
    public void carregaDadosBd()
    {
        Continente continente1 = new Continente(types.PRAIAVERMELHA,"Praia Vermelha");
        Pais pais1 = new Pais("Fisica","pais 1",continente1);
        Pais pais2 = new Pais("Geofisica","pais 2",continente1);
        Pais pais3 = new Pais("Computacao","pais 3",continente1);
        Pais pais4 = new Pais("Arquitetura","pais 4",continente1);
        Pais pais5 = new Pais("Engenharia","pais 5",continente1);
        continente1.setPaises(pais1,pais2,pais3,pais4,pais5);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
                
        Continente continente2 = new Continente(types.UNIDADESISOLADAS,"Unidades Isoladas");
        pais1 = new Pais("Veterinaria","pais 1",continente2);
        pais2 = new Pais("HUAP","pais 2",continente2);
        pais3 = new Pais("Reitoria","pais 3",continente2);
        pais4 = new Pais("Direito","pais 4",continente2);
        pais5 = new Pais("IACS","pais 5",continente2);
        continente2.setPaises(pais1,pais2,pais3,pais4,pais5);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
        
        Continente continente3 = new Continente(types.GRAGOATA,"Gragoata");
        pais1 = new Pais("Historia","pais 1",continente3);
        pais2 = new Pais("Filosofia","pais 2",continente3);
        pais3 = new Pais("Bandejao","pais 3",continente3);
        pais4 = new Pais("EducacaoFisica","pais 4",continente3);
        pais5 = new Pais("Pedagogia","pais 5",continente3);
        continente3.setPaises(pais1,pais2,pais3,pais4,pais5);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
        
        Continente continente4 = new Continente(types.VALONGUINHO,"Valonguinha");
        pais1 = new Pais("Matematica","pais 1",continente4);
        pais2 = new Pais("Odontologia","pais 2",continente4);
        pais3 = new Pais("Quimica","pais 3",continente4);
        pais4 = new Pais("Biologia","pais 4",continente4);
        pais5 = new Pais("Administracao","pais 5",continente4);
        Pais pais6 = new Pais("STI","pais 5",continente4);
        continente4.setPaises(pais1,pais2,pais3,pais4,pais5,pais6);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
        
        continentes.add(continente1);
        continentes.add(continente2);
        continentes.add(continente3);
        continentes.add(continente4);
    }
    
    public ArrayList<Pais> getPaises()
    {
        return paises;
    }
    
    public ArrayList<Continente> getContinentes()
    {
        return continentes;
    }
}
