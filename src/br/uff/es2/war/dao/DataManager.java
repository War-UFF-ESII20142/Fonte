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
        Continente continente1 = new Continente(types.PRAIAVERMELHA,types.sPRAIAVERMELHA);
        Pais pais1 = new Pais("Fisica","pais 1",continente1);
        Pais pais2 = new Pais("Geofisica","pais 2",continente1);
        Pais pais3 = new Pais("Computacao","pais 3",continente1);
        Pais pais4 = new Pais("Arquitetura","pais 4",continente1);
        Pais pais5 = new Pais("Engenharia","pais 5",continente1);
        continente1.setPaises(pais1,pais2,pais3,pais4,pais5);
        pais1.addAllVizinhos(pais2,pais5);
        pais2.addAllVizinhos(pais1,pais3,pais4,pais5);
        pais3.addAllVizinhos(pais2,pais4);
        pais4.addAllVizinhos(pais2,pais3,pais4);
        pais5.addAllVizinhos(pais1,pais2,pais4);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
                
        Continente continente2 = new Continente(types.UNIDADESISOLADAS,types.sUNIDADEISOLADAS);
        pais1 = new Pais("Veterinaria","pais 1",continente2);
        pais2 = new Pais("HUAP","pais 2",continente2);
        pais3 = new Pais("Reitoria","pais 3",continente2);
        pais4 = new Pais("Direito","pais 4",continente2);
        pais5 = new Pais("IACS","pais 5",continente2);
        continente2.setPaises(pais1,pais2,pais3,pais4,pais5);
        pais1.addAllVizinhos(pais2,pais3,pais4,pais5);
        pais2.addAllVizinhos(pais1,pais3);
        pais3.addAllVizinhos(pais1,pais2,pais4);
        pais4.addAllVizinhos(pais1,pais3,pais5);
        pais5.addAllVizinhos(pais1,pais4);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
        
        Continente continente3 = new Continente(types.GRAGOATA,types.sGRAGOATA);
        pais1 = new Pais("Historia","pais 1",continente3);
        pais2 = new Pais("Filosofia","pais 2",continente3);
        pais3 = new Pais("Bandejao","pais 3",continente3);
        pais4 = new Pais("EducacaoFisica","pais 4",continente3);
        pais5 = new Pais("Pedagogia","pais 5",continente3);
        continente3.setPaises(pais1,pais2,pais3,pais4,pais5);
        pais1.addAllVizinhos(pais2);
        pais2.addAllVizinhos(pais1,pais3);
        pais3.addAllVizinhos(pais2,pais4,pais5);
        pais4.addAllVizinhos(pais3,pais5);
        pais5.addAllVizinhos(pais3,pais4);
        paises.add(pais1);
        paises.add(pais2);
        paises.add(pais3);
        paises.add(pais4);
        paises.add(pais5);
        
        Continente continente4 = new Continente(types.VALONGUINHO,types.sVALONGUINHO);
        pais1 = new Pais("Matematica","pais 1",continente4);
        pais2 = new Pais("Odontologia","pais 2",continente4);
        pais3 = new Pais("Quimica","pais 3",continente4);
        pais4 = new Pais("Biologia","pais 4",continente4);
        pais5 = new Pais("Administracao","pais 5",continente4);
        Pais pais6 = new Pais("STI","pais 6",continente4);
        continente4.setPaises(pais1,pais2,pais3,pais4,pais5,pais6);
        pais1.addAllVizinhos(pais2,pais6);
        pais2.addAllVizinhos(pais1,pais6,pais3);
        pais3.addAllVizinhos(pais2,pais6,pais4);
        pais4.addAllVizinhos(pais6,pais3,pais5);
        pais5.addAllVizinhos(pais6,pais4);
        pais6.addAllVizinhos(pais1,pais2,pais3,pais4,pais5);
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
    
    public Pais getPais(String codigo,String continente)
    {
        Pais pais = new Pais("","",new Continente(0, ""));
        
        for(Continente c : continentes)
        {
            if(c.getNome().equals(continente))
            {
                for(Pais p: c.getPaises())
                {
                    if(p.getCodigo().equals(codigo))
                    {
                        return p;
                    }
                }
            }
        }
        
        return pais;
    }
    

    public Pais getPais(String nome){
        for (Pais c : paises) {
            if (c.getNome().equals(nome)) {
                return c;
            }
        }
        return (new Pais("", "", null));
    }
     
    public Pais getPaisByNome(String nome,String continente)
    {
        Pais pais = new Pais("","",new Continente(0, ""));
        
        for(Continente c : continentes)
        {
            if(c.getNome().equals(continente))
            {
                for(Pais p: c.getPaises())
                {
                    if(p.getNome().equals(nome))
                    {
                        return p;
                    }
                }
            }
        }
        return pais;

    }   
}
