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
import java.util.ArrayList;
import java.util.List;
import BancoDeDadosHibernate.Mapa;
import BancoDeDadosHibernate.Partida;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class PartidaJpaController implements Serializable {

    public PartidaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Partida partida) {
        if (partida.getJogaList() == null) {
            partida.setJogaList(new ArrayList<Joga>());
        }
        if (partida.getMapaList() == null) {
            partida.setMapaList(new ArrayList<Mapa>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Joga> attachedJogaList = new ArrayList<Joga>();
            for (Joga jogaListJogaToAttach : partida.getJogaList()) {
                jogaListJogaToAttach = em.getReference(jogaListJogaToAttach.getClass(), jogaListJogaToAttach.getJogaPK());
                attachedJogaList.add(jogaListJogaToAttach);
            }
            partida.setJogaList(attachedJogaList);
            List<Mapa> attachedMapaList = new ArrayList<Mapa>();
            for (Mapa mapaListMapaToAttach : partida.getMapaList()) {
                mapaListMapaToAttach = em.getReference(mapaListMapaToAttach.getClass(), mapaListMapaToAttach.getId());
                attachedMapaList.add(mapaListMapaToAttach);
            }
            partida.setMapaList(attachedMapaList);
            em.persist(partida);
            for (Joga jogaListJoga : partida.getJogaList()) {
                Partida oldPartidaOfJogaListJoga = jogaListJoga.getPartida();
                jogaListJoga.setPartida(partida);
                jogaListJoga = em.merge(jogaListJoga);
                if (oldPartidaOfJogaListJoga != null) {
                    oldPartidaOfJogaListJoga.getJogaList().remove(jogaListJoga);
                    oldPartidaOfJogaListJoga = em.merge(oldPartidaOfJogaListJoga);
                }
            }
            for (Mapa mapaListMapa : partida.getMapaList()) {
                Partida oldPartidaidOfMapaListMapa = mapaListMapa.getPartidaid();
                mapaListMapa.setPartidaid(partida);
                mapaListMapa = em.merge(mapaListMapa);
                if (oldPartidaidOfMapaListMapa != null) {
                    oldPartidaidOfMapaListMapa.getMapaList().remove(mapaListMapa);
                    oldPartidaidOfMapaListMapa = em.merge(oldPartidaidOfMapaListMapa);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Partida partida) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Partida persistentPartida = em.find(Partida.class, partida.getId());
            List<Joga> jogaListOld = persistentPartida.getJogaList();
            List<Joga> jogaListNew = partida.getJogaList();
            List<Mapa> mapaListOld = persistentPartida.getMapaList();
            List<Mapa> mapaListNew = partida.getMapaList();
            List<String> illegalOrphanMessages = null;
            for (Joga jogaListOldJoga : jogaListOld) {
                if (!jogaListNew.contains(jogaListOldJoga)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Joga " + jogaListOldJoga + " since its partida field is not nullable.");
                }
            }
            for (Mapa mapaListOldMapa : mapaListOld) {
                if (!mapaListNew.contains(mapaListOldMapa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Mapa " + mapaListOldMapa + " since its partidaid field is not nullable.");
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
            partida.setJogaList(jogaListNew);
            List<Mapa> attachedMapaListNew = new ArrayList<Mapa>();
            for (Mapa mapaListNewMapaToAttach : mapaListNew) {
                mapaListNewMapaToAttach = em.getReference(mapaListNewMapaToAttach.getClass(), mapaListNewMapaToAttach.getId());
                attachedMapaListNew.add(mapaListNewMapaToAttach);
            }
            mapaListNew = attachedMapaListNew;
            partida.setMapaList(mapaListNew);
            partida = em.merge(partida);
            for (Joga jogaListNewJoga : jogaListNew) {
                if (!jogaListOld.contains(jogaListNewJoga)) {
                    Partida oldPartidaOfJogaListNewJoga = jogaListNewJoga.getPartida();
                    jogaListNewJoga.setPartida(partida);
                    jogaListNewJoga = em.merge(jogaListNewJoga);
                    if (oldPartidaOfJogaListNewJoga != null && !oldPartidaOfJogaListNewJoga.equals(partida)) {
                        oldPartidaOfJogaListNewJoga.getJogaList().remove(jogaListNewJoga);
                        oldPartidaOfJogaListNewJoga = em.merge(oldPartidaOfJogaListNewJoga);
                    }
                }
            }
            for (Mapa mapaListNewMapa : mapaListNew) {
                if (!mapaListOld.contains(mapaListNewMapa)) {
                    Partida oldPartidaidOfMapaListNewMapa = mapaListNewMapa.getPartidaid();
                    mapaListNewMapa.setPartidaid(partida);
                    mapaListNewMapa = em.merge(mapaListNewMapa);
                    if (oldPartidaidOfMapaListNewMapa != null && !oldPartidaidOfMapaListNewMapa.equals(partida)) {
                        oldPartidaidOfMapaListNewMapa.getMapaList().remove(mapaListNewMapa);
                        oldPartidaidOfMapaListNewMapa = em.merge(oldPartidaidOfMapaListNewMapa);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = partida.getId();
                if (findPartida(id) == null) {
                    throw new NonexistentEntityException("The partida with id " + id + " no longer exists.");
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
            Partida partida;
            try {
                partida = em.getReference(Partida.class, id);
                partida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The partida with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Joga> jogaListOrphanCheck = partida.getJogaList();
            for (Joga jogaListOrphanCheckJoga : jogaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partida (" + partida + ") cannot be destroyed since the Joga " + jogaListOrphanCheckJoga + " in its jogaList field has a non-nullable partida field.");
            }
            List<Mapa> mapaListOrphanCheck = partida.getMapaList();
            for (Mapa mapaListOrphanCheckMapa : mapaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Partida (" + partida + ") cannot be destroyed since the Mapa " + mapaListOrphanCheckMapa + " in its mapaList field has a non-nullable partidaid field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(partida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Partida> findPartidaEntities() {
        return findPartidaEntities(true, -1, -1);
    }

    public List<Partida> findPartidaEntities(int maxResults, int firstResult) {
        return findPartidaEntities(false, maxResults, firstResult);
    }

    private List<Partida> findPartidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Partida.class));
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

    public Partida findPartida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Partida.class, id);
        } finally {
            em.close();
        }
    }

    public int getPartidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Partida> rt = cq.from(Partida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
