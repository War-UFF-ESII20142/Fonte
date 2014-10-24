/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import BancoDeDadosHibernate.Carta;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BancoDeDadosHibernate.Pais;
import BancoDeDadosHibernate.Joga;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class CartaJpaController implements Serializable {

    public CartaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carta carta) {
        if (carta.getJogaList() == null) {
            carta.setJogaList(new ArrayList<Joga>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais paisid = carta.getPaisid();
            if (paisid != null) {
                paisid = em.getReference(paisid.getClass(), paisid.getId());
                carta.setPaisid(paisid);
            }
            List<Joga> attachedJogaList = new ArrayList<Joga>();
            for (Joga jogaListJogaToAttach : carta.getJogaList()) {
                jogaListJogaToAttach = em.getReference(jogaListJogaToAttach.getClass(), jogaListJogaToAttach.getJogaPK());
                attachedJogaList.add(jogaListJogaToAttach);
            }
            carta.setJogaList(attachedJogaList);
            em.persist(carta);
            if (paisid != null) {
                paisid.getCartaList().add(carta);
                paisid = em.merge(paisid);
            }
            for (Joga jogaListJoga : carta.getJogaList()) {
                jogaListJoga.getCartaList().add(carta);
                jogaListJoga = em.merge(jogaListJoga);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carta carta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carta persistentCarta = em.find(Carta.class, carta.getId());
            Pais paisidOld = persistentCarta.getPaisid();
            Pais paisidNew = carta.getPaisid();
            List<Joga> jogaListOld = persistentCarta.getJogaList();
            List<Joga> jogaListNew = carta.getJogaList();
            if (paisidNew != null) {
                paisidNew = em.getReference(paisidNew.getClass(), paisidNew.getId());
                carta.setPaisid(paisidNew);
            }
            List<Joga> attachedJogaListNew = new ArrayList<Joga>();
            for (Joga jogaListNewJogaToAttach : jogaListNew) {
                jogaListNewJogaToAttach = em.getReference(jogaListNewJogaToAttach.getClass(), jogaListNewJogaToAttach.getJogaPK());
                attachedJogaListNew.add(jogaListNewJogaToAttach);
            }
            jogaListNew = attachedJogaListNew;
            carta.setJogaList(jogaListNew);
            carta = em.merge(carta);
            if (paisidOld != null && !paisidOld.equals(paisidNew)) {
                paisidOld.getCartaList().remove(carta);
                paisidOld = em.merge(paisidOld);
            }
            if (paisidNew != null && !paisidNew.equals(paisidOld)) {
                paisidNew.getCartaList().add(carta);
                paisidNew = em.merge(paisidNew);
            }
            for (Joga jogaListOldJoga : jogaListOld) {
                if (!jogaListNew.contains(jogaListOldJoga)) {
                    jogaListOldJoga.getCartaList().remove(carta);
                    jogaListOldJoga = em.merge(jogaListOldJoga);
                }
            }
            for (Joga jogaListNewJoga : jogaListNew) {
                if (!jogaListOld.contains(jogaListNewJoga)) {
                    jogaListNewJoga.getCartaList().add(carta);
                    jogaListNewJoga = em.merge(jogaListNewJoga);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = carta.getId();
                if (findCarta(id) == null) {
                    throw new NonexistentEntityException("The carta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carta carta;
            try {
                carta = em.getReference(Carta.class, id);
                carta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carta with id " + id + " no longer exists.", enfe);
            }
            Pais paisid = carta.getPaisid();
            if (paisid != null) {
                paisid.getCartaList().remove(carta);
                paisid = em.merge(paisid);
            }
            List<Joga> jogaList = carta.getJogaList();
            for (Joga jogaListJoga : jogaList) {
                jogaListJoga.getCartaList().remove(carta);
                jogaListJoga = em.merge(jogaListJoga);
            }
            em.remove(carta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carta> findCartaEntities() {
        return findCartaEntities(true, -1, -1);
    }

    public List<Carta> findCartaEntities(int maxResults, int firstResult) {
        return findCartaEntities(false, maxResults, firstResult);
    }

    private List<Carta> findCartaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carta.class));
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

    public Carta findCarta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carta.class, id);
        } finally {
            em.close();
        }
    }

    public int getCartaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carta> rt = cq.from(Carta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
