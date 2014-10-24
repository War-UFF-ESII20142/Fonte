/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import BancoDeDadosHibernate.Continente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import BancoDeDadosHibernate.Mapa;
import BancoDeDadosHibernate.Pais;
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
public class ContinenteJpaController implements Serializable {

    public ContinenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Continente continente) {
        if (continente.getPaisList() == null) {
            continente.setPaisList(new ArrayList<Pais>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mapa mapaid = continente.getMapaid();
            if (mapaid != null) {
                mapaid = em.getReference(mapaid.getClass(), mapaid.getId());
                continente.setMapaid(mapaid);
            }
            List<Pais> attachedPaisList = new ArrayList<Pais>();
            for (Pais paisListPaisToAttach : continente.getPaisList()) {
                paisListPaisToAttach = em.getReference(paisListPaisToAttach.getClass(), paisListPaisToAttach.getId());
                attachedPaisList.add(paisListPaisToAttach);
            }
            continente.setPaisList(attachedPaisList);
            em.persist(continente);
            if (mapaid != null) {
                mapaid.getContinenteList().add(continente);
                mapaid = em.merge(mapaid);
            }
            for (Pais paisListPais : continente.getPaisList()) {
                Continente oldContinenteidOfPaisListPais = paisListPais.getContinenteid();
                paisListPais.setContinenteid(continente);
                paisListPais = em.merge(paisListPais);
                if (oldContinenteidOfPaisListPais != null) {
                    oldContinenteidOfPaisListPais.getPaisList().remove(paisListPais);
                    oldContinenteidOfPaisListPais = em.merge(oldContinenteidOfPaisListPais);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Continente continente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Continente persistentContinente = em.find(Continente.class, continente.getId());
            Mapa mapaidOld = persistentContinente.getMapaid();
            Mapa mapaidNew = continente.getMapaid();
            List<Pais> paisListOld = persistentContinente.getPaisList();
            List<Pais> paisListNew = continente.getPaisList();
            List<String> illegalOrphanMessages = null;
            for (Pais paisListOldPais : paisListOld) {
                if (!paisListNew.contains(paisListOldPais)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pais " + paisListOldPais + " since its continenteid field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (mapaidNew != null) {
                mapaidNew = em.getReference(mapaidNew.getClass(), mapaidNew.getId());
                continente.setMapaid(mapaidNew);
            }
            List<Pais> attachedPaisListNew = new ArrayList<Pais>();
            for (Pais paisListNewPaisToAttach : paisListNew) {
                paisListNewPaisToAttach = em.getReference(paisListNewPaisToAttach.getClass(), paisListNewPaisToAttach.getId());
                attachedPaisListNew.add(paisListNewPaisToAttach);
            }
            paisListNew = attachedPaisListNew;
            continente.setPaisList(paisListNew);
            continente = em.merge(continente);
            if (mapaidOld != null && !mapaidOld.equals(mapaidNew)) {
                mapaidOld.getContinenteList().remove(continente);
                mapaidOld = em.merge(mapaidOld);
            }
            if (mapaidNew != null && !mapaidNew.equals(mapaidOld)) {
                mapaidNew.getContinenteList().add(continente);
                mapaidNew = em.merge(mapaidNew);
            }
            for (Pais paisListNewPais : paisListNew) {
                if (!paisListOld.contains(paisListNewPais)) {
                    Continente oldContinenteidOfPaisListNewPais = paisListNewPais.getContinenteid();
                    paisListNewPais.setContinenteid(continente);
                    paisListNewPais = em.merge(paisListNewPais);
                    if (oldContinenteidOfPaisListNewPais != null && !oldContinenteidOfPaisListNewPais.equals(continente)) {
                        oldContinenteidOfPaisListNewPais.getPaisList().remove(paisListNewPais);
                        oldContinenteidOfPaisListNewPais = em.merge(oldContinenteidOfPaisListNewPais);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = continente.getId();
                if (findContinente(id) == null) {
                    throw new NonexistentEntityException("The continente with id " + id + " no longer exists.");
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
            Continente continente;
            try {
                continente = em.getReference(Continente.class, id);
                continente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The continente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Pais> paisListOrphanCheck = continente.getPaisList();
            for (Pais paisListOrphanCheckPais : paisListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Continente (" + continente + ") cannot be destroyed since the Pais " + paisListOrphanCheckPais + " in its paisList field has a non-nullable continenteid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Mapa mapaid = continente.getMapaid();
            if (mapaid != null) {
                mapaid.getContinenteList().remove(continente);
                mapaid = em.merge(mapaid);
            }
            em.remove(continente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Continente> findContinenteEntities() {
        return findContinenteEntities(true, -1, -1);
    }

    public List<Continente> findContinenteEntities(int maxResults, int firstResult) {
        return findContinenteEntities(false, maxResults, firstResult);
    }

    private List<Continente> findContinenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Continente.class));
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

    public Continente findContinente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Continente.class, id);
        } finally {
            em.close();
        }
    }

    public int getContinenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Continente> rt = cq.from(Continente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
