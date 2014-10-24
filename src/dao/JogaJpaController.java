/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BancoDeDadosHibernate.Partida;
import BancoDeDadosHibernate.Jogador;
import BancoDeDadosHibernate.Objetivo;
import BancoDeDadosHibernate.Pais;
import java.util.ArrayList;
import java.util.List;
import BancoDeDadosHibernate.Carta;
import BancoDeDadosHibernate.Joga;
import BancoDeDadosHibernate.JogaPK;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class JogaJpaController implements Serializable {

    public JogaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Joga joga) throws PreexistingEntityException, Exception {
        if (joga.getJogaPK() == null) {
            joga.setJogaPK(new JogaPK());
        }
        if (joga.getPaisList() == null) {
            joga.setPaisList(new ArrayList<Pais>());
        }
        if (joga.getCartaList() == null) {
            joga.setCartaList(new ArrayList<Carta>());
        }
        joga.getJogaPK().setPartidaid(joga.getPartida().getId());
        joga.getJogaPK().setJogadorid(joga.getJogador().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida partida = joga.getPartida();
            if (partida != null) {
                partida = em.getReference(partida.getClass(), partida.getId());
                joga.setPartida(partida);
            }
            Jogador jogador = joga.getJogador();
            if (jogador != null) {
                jogador = em.getReference(jogador.getClass(), jogador.getId());
                joga.setJogador(jogador);
            }
            Objetivo objetivoid = joga.getObjetivoid();
            if (objetivoid != null) {
                objetivoid = em.getReference(objetivoid.getClass(), objetivoid.getId());
                joga.setObjetivoid(objetivoid);
            }
            List<Pais> attachedPaisList = new ArrayList<Pais>();
            for (Pais paisListPaisToAttach : joga.getPaisList()) {
                paisListPaisToAttach = em.getReference(paisListPaisToAttach.getClass(), paisListPaisToAttach.getId());
                attachedPaisList.add(paisListPaisToAttach);
            }
            joga.setPaisList(attachedPaisList);
            List<Carta> attachedCartaList = new ArrayList<Carta>();
            for (Carta cartaListCartaToAttach : joga.getCartaList()) {
                cartaListCartaToAttach = em.getReference(cartaListCartaToAttach.getClass(), cartaListCartaToAttach.getId());
                attachedCartaList.add(cartaListCartaToAttach);
            }
            joga.setCartaList(attachedCartaList);
            em.persist(joga);
            if (partida != null) {
                partida.getJogaList().add(joga);
                partida = em.merge(partida);
            }
            if (jogador != null) {
                jogador.getJogaList().add(joga);
                jogador = em.merge(jogador);
            }
            if (objetivoid != null) {
                objetivoid.getJogaList().add(joga);
                objetivoid = em.merge(objetivoid);
            }
            for (Pais paisListPais : joga.getPaisList()) {
                paisListPais.getJogaList().add(joga);
                paisListPais = em.merge(paisListPais);
            }
            for (Carta cartaListCarta : joga.getCartaList()) {
                cartaListCarta.getJogaList().add(joga);
                cartaListCarta = em.merge(cartaListCarta);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findJoga(joga.getJogaPK()) != null) {
                throw new PreexistingEntityException("Joga " + joga + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Joga joga) throws NonexistentEntityException, Exception {
        joga.getJogaPK().setPartidaid(joga.getPartida().getId());
        joga.getJogaPK().setJogadorid(joga.getJogador().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Joga persistentJoga = em.find(Joga.class, joga.getJogaPK());
            Partida partidaOld = persistentJoga.getPartida();
            Partida partidaNew = joga.getPartida();
            Jogador jogadorOld = persistentJoga.getJogador();
            Jogador jogadorNew = joga.getJogador();
            Objetivo objetivoidOld = persistentJoga.getObjetivoid();
            Objetivo objetivoidNew = joga.getObjetivoid();
            List<Pais> paisListOld = persistentJoga.getPaisList();
            List<Pais> paisListNew = joga.getPaisList();
            List<Carta> cartaListOld = persistentJoga.getCartaList();
            List<Carta> cartaListNew = joga.getCartaList();
            if (partidaNew != null) {
                partidaNew = em.getReference(partidaNew.getClass(), partidaNew.getId());
                joga.setPartida(partidaNew);
            }
            if (jogadorNew != null) {
                jogadorNew = em.getReference(jogadorNew.getClass(), jogadorNew.getId());
                joga.setJogador(jogadorNew);
            }
            if (objetivoidNew != null) {
                objetivoidNew = em.getReference(objetivoidNew.getClass(), objetivoidNew.getId());
                joga.setObjetivoid(objetivoidNew);
            }
            List<Pais> attachedPaisListNew = new ArrayList<Pais>();
            for (Pais paisListNewPaisToAttach : paisListNew) {
                paisListNewPaisToAttach = em.getReference(paisListNewPaisToAttach.getClass(), paisListNewPaisToAttach.getId());
                attachedPaisListNew.add(paisListNewPaisToAttach);
            }
            paisListNew = attachedPaisListNew;
            joga.setPaisList(paisListNew);
            List<Carta> attachedCartaListNew = new ArrayList<Carta>();
            for (Carta cartaListNewCartaToAttach : cartaListNew) {
                cartaListNewCartaToAttach = em.getReference(cartaListNewCartaToAttach.getClass(), cartaListNewCartaToAttach.getId());
                attachedCartaListNew.add(cartaListNewCartaToAttach);
            }
            cartaListNew = attachedCartaListNew;
            joga.setCartaList(cartaListNew);
            joga = em.merge(joga);
            if (partidaOld != null && !partidaOld.equals(partidaNew)) {
                partidaOld.getJogaList().remove(joga);
                partidaOld = em.merge(partidaOld);
            }
            if (partidaNew != null && !partidaNew.equals(partidaOld)) {
                partidaNew.getJogaList().add(joga);
                partidaNew = em.merge(partidaNew);
            }
            if (jogadorOld != null && !jogadorOld.equals(jogadorNew)) {
                jogadorOld.getJogaList().remove(joga);
                jogadorOld = em.merge(jogadorOld);
            }
            if (jogadorNew != null && !jogadorNew.equals(jogadorOld)) {
                jogadorNew.getJogaList().add(joga);
                jogadorNew = em.merge(jogadorNew);
            }
            if (objetivoidOld != null && !objetivoidOld.equals(objetivoidNew)) {
                objetivoidOld.getJogaList().remove(joga);
                objetivoidOld = em.merge(objetivoidOld);
            }
            if (objetivoidNew != null && !objetivoidNew.equals(objetivoidOld)) {
                objetivoidNew.getJogaList().add(joga);
                objetivoidNew = em.merge(objetivoidNew);
            }
            for (Pais paisListOldPais : paisListOld) {
                if (!paisListNew.contains(paisListOldPais)) {
                    paisListOldPais.getJogaList().remove(joga);
                    paisListOldPais = em.merge(paisListOldPais);
                }
            }
            for (Pais paisListNewPais : paisListNew) {
                if (!paisListOld.contains(paisListNewPais)) {
                    paisListNewPais.getJogaList().add(joga);
                    paisListNewPais = em.merge(paisListNewPais);
                }
            }
            for (Carta cartaListOldCarta : cartaListOld) {
                if (!cartaListNew.contains(cartaListOldCarta)) {
                    cartaListOldCarta.getJogaList().remove(joga);
                    cartaListOldCarta = em.merge(cartaListOldCarta);
                }
            }
            for (Carta cartaListNewCarta : cartaListNew) {
                if (!cartaListOld.contains(cartaListNewCarta)) {
                    cartaListNewCarta.getJogaList().add(joga);
                    cartaListNewCarta = em.merge(cartaListNewCarta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                JogaPK id = joga.getJogaPK();
                if (findJoga(id) == null) {
                    throw new NonexistentEntityException("The joga with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(JogaPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Joga joga;
            try {
                joga = em.getReference(Joga.class, id);
                joga.getJogaPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The joga with id " + id + " no longer exists.", enfe);
            }
            Partida partida = joga.getPartida();
            if (partida != null) {
                partida.getJogaList().remove(joga);
                partida = em.merge(partida);
            }
            Jogador jogador = joga.getJogador();
            if (jogador != null) {
                jogador.getJogaList().remove(joga);
                jogador = em.merge(jogador);
            }
            Objetivo objetivoid = joga.getObjetivoid();
            if (objetivoid != null) {
                objetivoid.getJogaList().remove(joga);
                objetivoid = em.merge(objetivoid);
            }
            List<Pais> paisList = joga.getPaisList();
            for (Pais paisListPais : paisList) {
                paisListPais.getJogaList().remove(joga);
                paisListPais = em.merge(paisListPais);
            }
            List<Carta> cartaList = joga.getCartaList();
            for (Carta cartaListCarta : cartaList) {
                cartaListCarta.getJogaList().remove(joga);
                cartaListCarta = em.merge(cartaListCarta);
            }
            em.remove(joga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Joga> findJogaEntities() {
        return findJogaEntities(true, -1, -1);
    }

    public List<Joga> findJogaEntities(int maxResults, int firstResult) {
        return findJogaEntities(false, maxResults, firstResult);
    }

    private List<Joga> findJogaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Joga.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Joga findJoga(JogaPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Joga.class, id);
        } finally {
            em.close();
        }
    }

    public int getJogaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Joga> rt = cq.from(Joga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
