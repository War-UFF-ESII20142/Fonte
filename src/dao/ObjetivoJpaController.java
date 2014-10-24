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
import BancoDeDadosHibernate.Mapa;
import BancoDeDadosHibernate.Joga;
import BancoDeDadosHibernate.Objetivo;
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
public class ObjetivoJpaController implements Serializable {

    public ObjetivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Objetivo objetivo) {
        if (objetivo.getJogaList() == null) {
            objetivo.setJogaList(new ArrayList<Joga>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mapa mapaid = objetivo.getMapaid();
            if (mapaid != null) {
                mapaid = em.getReference(mapaid.getClass(), mapaid.getId());
                objetivo.setMapaid(mapaid);
            }
            List<Joga> attachedJogaList = new ArrayList<Joga>();
            for (Joga jogaListJogaToAttach : objetivo.getJogaList()) {
                jogaListJogaToAttach = em.getReference(jogaListJogaToAttach.getClass(), jogaListJogaToAttach.getJogaPK());
                attachedJogaList.add(jogaListJogaToAttach);
            }
            objetivo.setJogaList(attachedJogaList);
            em.persist(objetivo);
            if (mapaid != null) {
                mapaid.getObjetivoList().add(objetivo);
                mapaid = em.merge(mapaid);
            }
            for (Joga jogaListJoga : objetivo.getJogaList()) {
                Objetivo oldObjetivoidOfJogaListJoga = jogaListJoga.getObjetivoid();
                jogaListJoga.setObjetivoid(objetivo);
                jogaListJoga = em.merge(jogaListJoga);
                if (oldObjetivoidOfJogaListJoga != null) {
                    oldObjetivoidOfJogaListJoga.getJogaList().remove(jogaListJoga);
                    oldObjetivoidOfJogaListJoga = em.merge(oldObjetivoidOfJogaListJoga);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Objetivo objetivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Objetivo persistentObjetivo = em.find(Objetivo.class, objetivo.getId());
            Mapa mapaidOld = persistentObjetivo.getMapaid();
            Mapa mapaidNew = objetivo.getMapaid();
            List<Joga> jogaListOld = persistentObjetivo.getJogaList();
            List<Joga> jogaListNew = objetivo.getJogaList();
            List<String> illegalOrphanMessages = null;
            for (Joga jogaListOldJoga : jogaListOld) {
                if (!jogaListNew.contains(jogaListOldJoga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Joga " + jogaListOldJoga + " since its objetivoid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (mapaidNew != null) {
                mapaidNew = em.getReference(mapaidNew.getClass(), mapaidNew.getId());
                objetivo.setMapaid(mapaidNew);
            }
            List<Joga> attachedJogaListNew = new ArrayList<Joga>();
            for (Joga jogaListNewJogaToAttach : jogaListNew) {
                jogaListNewJogaToAttach = em.getReference(jogaListNewJogaToAttach.getClass(), jogaListNewJogaToAttach.getJogaPK());
                attachedJogaListNew.add(jogaListNewJogaToAttach);
            }
            jogaListNew = attachedJogaListNew;
            objetivo.setJogaList(jogaListNew);
            objetivo = em.merge(objetivo);
            if (mapaidOld != null && !mapaidOld.equals(mapaidNew)) {
                mapaidOld.getObjetivoList().remove(objetivo);
                mapaidOld = em.merge(mapaidOld);
            }
            if (mapaidNew != null && !mapaidNew.equals(mapaidOld)) {
                mapaidNew.getObjetivoList().add(objetivo);
                mapaidNew = em.merge(mapaidNew);
            }
            for (Joga jogaListNewJoga : jogaListNew) {
                if (!jogaListOld.contains(jogaListNewJoga)) {
                    Objetivo oldObjetivoidOfJogaListNewJoga = jogaListNewJoga.getObjetivoid();
                    jogaListNewJoga.setObjetivoid(objetivo);
                    jogaListNewJoga = em.merge(jogaListNewJoga);
                    if (oldObjetivoidOfJogaListNewJoga != null && !oldObjetivoidOfJogaListNewJoga.equals(objetivo)) {
                        oldObjetivoidOfJogaListNewJoga.getJogaList().remove(jogaListNewJoga);
                        oldObjetivoidOfJogaListNewJoga = em.merge(oldObjetivoidOfJogaListNewJoga);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = objetivo.getId();
                if (findObjetivo(id) == null) {
                    throw new NonexistentEntityException("The objetivo with id " + id + " no longer exists.");
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
            Objetivo objetivo;
            try {
                objetivo = em.getReference(Objetivo.class, id);
                objetivo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objetivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Joga> jogaListOrphanCheck = objetivo.getJogaList();
            for (Joga jogaListOrphanCheckJoga : jogaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Objetivo (" + objetivo + ") cannot be destroyed since the Joga " + jogaListOrphanCheckJoga + " in its jogaList field has a non-nullable objetivoid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mapa mapaid = objetivo.getMapaid();
            if (mapaid != null) {
                mapaid.getObjetivoList().remove(objetivo);
                mapaid = em.merge(mapaid);
            }
            em.remove(objetivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Objetivo> findObjetivoEntities() {
        return findObjetivoEntities(true, -1, -1);
    }

    public List<Objetivo> findObjetivoEntities(int maxResults, int firstResult) {
        return findObjetivoEntities(false, maxResults, firstResult);
    }

    private List<Objetivo> findObjetivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Objetivo.class));
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

    public Objetivo findObjetivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Objetivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Objetivo> rt = cq.from(Objetivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
