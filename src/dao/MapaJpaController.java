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
import BancoDeDadosHibernate.Objetivo;
import java.util.ArrayList;
import java.util.List;
import BancoDeDadosHibernate.Continente;
import BancoDeDadosHibernate.Mapa;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class MapaJpaController implements Serializable {

    public MapaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mapa mapa) {
        if (mapa.getObjetivoList() == null) {
            mapa.setObjetivoList(new ArrayList<Objetivo>());
        }
        if (mapa.getContinenteList() == null) {
            mapa.setContinenteList(new ArrayList<Continente>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida partidaid = mapa.getPartidaid();
            if (partidaid != null) {
                partidaid = em.getReference(partidaid.getClass(), partidaid.getId());
                mapa.setPartidaid(partidaid);
            }
            List<Objetivo> attachedObjetivoList = new ArrayList<Objetivo>();
            for (Objetivo objetivoListObjetivoToAttach : mapa.getObjetivoList()) {
                objetivoListObjetivoToAttach = em.getReference(objetivoListObjetivoToAttach.getClass(), objetivoListObjetivoToAttach.getId());
                attachedObjetivoList.add(objetivoListObjetivoToAttach);
            }
            mapa.setObjetivoList(attachedObjetivoList);
            List<Continente> attachedContinenteList = new ArrayList<Continente>();
            for (Continente continenteListContinenteToAttach : mapa.getContinenteList()) {
                continenteListContinenteToAttach = em.getReference(continenteListContinenteToAttach.getClass(), continenteListContinenteToAttach.getId());
                attachedContinenteList.add(continenteListContinenteToAttach);
            }
            mapa.setContinenteList(attachedContinenteList);
            em.persist(mapa);
            if (partidaid != null) {
                partidaid.getMapaList().add(mapa);
                partidaid = em.merge(partidaid);
            }
            for (Objetivo objetivoListObjetivo : mapa.getObjetivoList()) {
                Mapa oldMapaidOfObjetivoListObjetivo = objetivoListObjetivo.getMapaid();
                objetivoListObjetivo.setMapaid(mapa);
                objetivoListObjetivo = em.merge(objetivoListObjetivo);
                if (oldMapaidOfObjetivoListObjetivo != null) {
                    oldMapaidOfObjetivoListObjetivo.getObjetivoList().remove(objetivoListObjetivo);
                    oldMapaidOfObjetivoListObjetivo = em.merge(oldMapaidOfObjetivoListObjetivo);
                }
            }
            for (Continente continenteListContinente : mapa.getContinenteList()) {
                Mapa oldMapaidOfContinenteListContinente = continenteListContinente.getMapaid();
                continenteListContinente.setMapaid(mapa);
                continenteListContinente = em.merge(continenteListContinente);
                if (oldMapaidOfContinenteListContinente != null) {
                    oldMapaidOfContinenteListContinente.getContinenteList().remove(continenteListContinente);
                    oldMapaidOfContinenteListContinente = em.merge(oldMapaidOfContinenteListContinente);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mapa mapa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mapa persistentMapa = em.find(Mapa.class, mapa.getId());
            Partida partidaidOld = persistentMapa.getPartidaid();
            Partida partidaidNew = mapa.getPartidaid();
            List<Objetivo> objetivoListOld = persistentMapa.getObjetivoList();
            List<Objetivo> objetivoListNew = mapa.getObjetivoList();
            List<Continente> continenteListOld = persistentMapa.getContinenteList();
            List<Continente> continenteListNew = mapa.getContinenteList();
            List<String> illegalOrphanMessages = null;
            for (Objetivo objetivoListOldObjetivo : objetivoListOld) {
                if (!objetivoListNew.contains(objetivoListOldObjetivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Objetivo " + objetivoListOldObjetivo + " since its mapaid field is not nullable.");
                }
            }
            for (Continente continenteListOldContinente : continenteListOld) {
                if (!continenteListNew.contains(continenteListOldContinente)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Continente " + continenteListOldContinente + " since its mapaid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (partidaidNew != null) {
                partidaidNew = em.getReference(partidaidNew.getClass(), partidaidNew.getId());
                mapa.setPartidaid(partidaidNew);
            }
            List<Objetivo> attachedObjetivoListNew = new ArrayList<Objetivo>();
            for (Objetivo objetivoListNewObjetivoToAttach : objetivoListNew) {
                objetivoListNewObjetivoToAttach = em.getReference(objetivoListNewObjetivoToAttach.getClass(), objetivoListNewObjetivoToAttach.getId());
                attachedObjetivoListNew.add(objetivoListNewObjetivoToAttach);
            }
            objetivoListNew = attachedObjetivoListNew;
            mapa.setObjetivoList(objetivoListNew);
            List<Continente> attachedContinenteListNew = new ArrayList<Continente>();
            for (Continente continenteListNewContinenteToAttach : continenteListNew) {
                continenteListNewContinenteToAttach = em.getReference(continenteListNewContinenteToAttach.getClass(), continenteListNewContinenteToAttach.getId());
                attachedContinenteListNew.add(continenteListNewContinenteToAttach);
            }
            continenteListNew = attachedContinenteListNew;
            mapa.setContinenteList(continenteListNew);
            mapa = em.merge(mapa);
            if (partidaidOld != null && !partidaidOld.equals(partidaidNew)) {
                partidaidOld.getMapaList().remove(mapa);
                partidaidOld = em.merge(partidaidOld);
            }
            if (partidaidNew != null && !partidaidNew.equals(partidaidOld)) {
                partidaidNew.getMapaList().add(mapa);
                partidaidNew = em.merge(partidaidNew);
            }
            for (Objetivo objetivoListNewObjetivo : objetivoListNew) {
                if (!objetivoListOld.contains(objetivoListNewObjetivo)) {
                    Mapa oldMapaidOfObjetivoListNewObjetivo = objetivoListNewObjetivo.getMapaid();
                    objetivoListNewObjetivo.setMapaid(mapa);
                    objetivoListNewObjetivo = em.merge(objetivoListNewObjetivo);
                    if (oldMapaidOfObjetivoListNewObjetivo != null && !oldMapaidOfObjetivoListNewObjetivo.equals(mapa)) {
                        oldMapaidOfObjetivoListNewObjetivo.getObjetivoList().remove(objetivoListNewObjetivo);
                        oldMapaidOfObjetivoListNewObjetivo = em.merge(oldMapaidOfObjetivoListNewObjetivo);
                    }
                }
            }
            for (Continente continenteListNewContinente : continenteListNew) {
                if (!continenteListOld.contains(continenteListNewContinente)) {
                    Mapa oldMapaidOfContinenteListNewContinente = continenteListNewContinente.getMapaid();
                    continenteListNewContinente.setMapaid(mapa);
                    continenteListNewContinente = em.merge(continenteListNewContinente);
                    if (oldMapaidOfContinenteListNewContinente != null && !oldMapaidOfContinenteListNewContinente.equals(mapa)) {
                        oldMapaidOfContinenteListNewContinente.getContinenteList().remove(continenteListNewContinente);
                        oldMapaidOfContinenteListNewContinente = em.merge(oldMapaidOfContinenteListNewContinente);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mapa.getId();
                if (findMapa(id) == null) {
                    throw new NonexistentEntityException("The mapa with id " + id + " no longer exists.");
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
            Mapa mapa;
            try {
                mapa = em.getReference(Mapa.class, id);
                mapa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mapa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Objetivo> objetivoListOrphanCheck = mapa.getObjetivoList();
            for (Objetivo objetivoListOrphanCheckObjetivo : objetivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mapa (" + mapa + ") cannot be destroyed since the Objetivo " + objetivoListOrphanCheckObjetivo + " in its objetivoList field has a non-nullable mapaid field.");
            }
            List<Continente> continenteListOrphanCheck = mapa.getContinenteList();
            for (Continente continenteListOrphanCheckContinente : continenteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Mapa (" + mapa + ") cannot be destroyed since the Continente " + continenteListOrphanCheckContinente + " in its continenteList field has a non-nullable mapaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Partida partidaid = mapa.getPartidaid();
            if (partidaid != null) {
                partidaid.getMapaList().remove(mapa);
                partidaid = em.merge(partidaid);
            }
            em.remove(mapa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mapa> findMapaEntities() {
        return findMapaEntities(true, -1, -1);
    }

    public List<Mapa> findMapaEntities(int maxResults, int firstResult) {
        return findMapaEntities(false, maxResults, firstResult);
    }

    private List<Mapa> findMapaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mapa.class));
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

    public Mapa findMapa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mapa.class, id);
        } finally {
            em.close();
        }
    }

    public int getMapaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mapa> rt = cq.from(Mapa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
