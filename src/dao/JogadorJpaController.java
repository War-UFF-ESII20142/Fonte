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
import BancoDeDadosHibernate.Joga;
import BancoDeDadosHibernate.Jogador;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class JogadorJpaController implements Serializable {

    public JogadorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Jogador jogador) {
        if (jogador.getJogaList() == null) {
            jogador.setJogaList(new ArrayList<Joga>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Joga> attachedJogaList = new ArrayList<Joga>();
            for (Joga jogaListJogaToAttach : jogador.getJogaList()) {
                jogaListJogaToAttach = em.getReference(jogaListJogaToAttach.getClass(), jogaListJogaToAttach.getJogaPK());
                attachedJogaList.add(jogaListJogaToAttach);
            }
            jogador.setJogaList(attachedJogaList);
            em.persist(jogador);
            for (Joga jogaListJoga : jogador.getJogaList()) {
                Jogador oldJogadorOfJogaListJoga = jogaListJoga.getJogador();
                jogaListJoga.setJogador(jogador);
                jogaListJoga = em.merge(jogaListJoga);
                if (oldJogadorOfJogaListJoga != null) {
                    oldJogadorOfJogaListJoga.getJogaList().remove(jogaListJoga);
                    oldJogadorOfJogaListJoga = em.merge(oldJogadorOfJogaListJoga);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Jogador jogador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogador persistentJogador = em.find(Jogador.class, jogador.getId());
            List<Joga> jogaListOld = persistentJogador.getJogaList();
            List<Joga> jogaListNew = jogador.getJogaList();
            List<String> illegalOrphanMessages = null;
            for (Joga jogaListOldJoga : jogaListOld) {
                if (!jogaListNew.contains(jogaListOldJoga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Joga " + jogaListOldJoga + " since its jogador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Joga> attachedJogaListNew = new ArrayList<Joga>();
            for (Joga jogaListNewJogaToAttach : jogaListNew) {
                jogaListNewJogaToAttach = em.getReference(jogaListNewJogaToAttach.getClass(), jogaListNewJogaToAttach.getJogaPK());
                attachedJogaListNew.add(jogaListNewJogaToAttach);
            }
            jogaListNew = attachedJogaListNew;
            jogador.setJogaList(jogaListNew);
            jogador = em.merge(jogador);
            for (Joga jogaListNewJoga : jogaListNew) {
                if (!jogaListOld.contains(jogaListNewJoga)) {
                    Jogador oldJogadorOfJogaListNewJoga = jogaListNewJoga.getJogador();
                    jogaListNewJoga.setJogador(jogador);
                    jogaListNewJoga = em.merge(jogaListNewJoga);
                    if (oldJogadorOfJogaListNewJoga != null && !oldJogadorOfJogaListNewJoga.equals(jogador)) {
                        oldJogadorOfJogaListNewJoga.getJogaList().remove(jogaListNewJoga);
                        oldJogadorOfJogaListNewJoga = em.merge(oldJogadorOfJogaListNewJoga);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = jogador.getId();
                if (findJogador(id) == null) {
                    throw new NonexistentEntityException("The jogador with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Jogador jogador;
            try {
                jogador = em.getReference(Jogador.class, id);
                jogador.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The jogador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Joga> jogaListOrphanCheck = jogador.getJogaList();
            for (Joga jogaListOrphanCheckJoga : jogaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Jogador (" + jogador + ") cannot be destroyed since the Joga " + jogaListOrphanCheckJoga + " in its jogaList field has a non-nullable jogador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(jogador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Jogador> findJogadorEntities() {
        return findJogadorEntities(true, -1, -1);
    }

    public List<Jogador> findJogadorEntities(int maxResults, int firstResult) {
        return findJogadorEntities(false, maxResults, firstResult);
    }

    private List<Jogador> findJogadorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Jogador.class));
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

    public Jogador findJogador(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Jogador.class, id);
        } finally {
            em.close();
        }
    }

    public int getJogadorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Jogador> rt = cq.from(Jogador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
