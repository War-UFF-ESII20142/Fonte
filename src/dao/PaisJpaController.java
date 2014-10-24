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
import BancoDeDadosHibernate.Continente;
import BancoDeDadosHibernate.Joga;
import java.util.ArrayList;
import java.util.List;
import BancoDeDadosHibernate.Pais;
import BancoDeDadosHibernate.Carta;
import dao.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Filipe
 */
public class PaisJpaController implements Serializable {

    public PaisJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pais pais) {
        if (pais.getJogaList() == null) {
            pais.setJogaList(new ArrayList<Joga>());
        }
        if (pais.getPaisList() == null) {
            pais.setPaisList(new ArrayList<Pais>());
        }
        if (pais.getPaisList1() == null) {
            pais.setPaisList1(new ArrayList<Pais>());
        }
        if (pais.getCartaList() == null) {
            pais.setCartaList(new ArrayList<Carta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Continente continenteid = pais.getContinenteid();
            if (continenteid != null) {
                continenteid = em.getReference(continenteid.getClass(), continenteid.getId());
                pais.setContinenteid(continenteid);
            }
            List<Joga> attachedJogaList = new ArrayList<Joga>();
            for (Joga jogaListJogaToAttach : pais.getJogaList()) {
                jogaListJogaToAttach = em.getReference(jogaListJogaToAttach.getClass(), jogaListJogaToAttach.getJogaPK());
                attachedJogaList.add(jogaListJogaToAttach);
            }
            pais.setJogaList(attachedJogaList);
            List<Pais> attachedPaisList = new ArrayList<Pais>();
            for (Pais paisListPaisToAttach : pais.getPaisList()) {
                paisListPaisToAttach = em.getReference(paisListPaisToAttach.getClass(), paisListPaisToAttach.getId());
                attachedPaisList.add(paisListPaisToAttach);
            }
            pais.setPaisList(attachedPaisList);
            List<Pais> attachedPaisList1 = new ArrayList<Pais>();
            for (Pais paisList1PaisToAttach : pais.getPaisList1()) {
                paisList1PaisToAttach = em.getReference(paisList1PaisToAttach.getClass(), paisList1PaisToAttach.getId());
                attachedPaisList1.add(paisList1PaisToAttach);
            }
            pais.setPaisList1(attachedPaisList1);
            List<Carta> attachedCartaList = new ArrayList<Carta>();
            for (Carta cartaListCartaToAttach : pais.getCartaList()) {
                cartaListCartaToAttach = em.getReference(cartaListCartaToAttach.getClass(), cartaListCartaToAttach.getId());
                attachedCartaList.add(cartaListCartaToAttach);
            }
            pais.setCartaList(attachedCartaList);
            em.persist(pais);
            if (continenteid != null) {
                continenteid.getPaisList().add(pais);
                continenteid = em.merge(continenteid);
            }
            for (Joga jogaListJoga : pais.getJogaList()) {
                jogaListJoga.getPaisList().add(pais);
                jogaListJoga = em.merge(jogaListJoga);
            }
            for (Pais paisListPais : pais.getPaisList()) {
                paisListPais.getPaisList().add(pais);
                paisListPais = em.merge(paisListPais);
            }
            for (Pais paisList1Pais : pais.getPaisList1()) {
                paisList1Pais.getPaisList().add(pais);
                paisList1Pais = em.merge(paisList1Pais);
            }
            for (Carta cartaListCarta : pais.getCartaList()) {
                Pais oldPaisidOfCartaListCarta = cartaListCarta.getPaisid();
                cartaListCarta.setPaisid(pais);
                cartaListCarta = em.merge(cartaListCarta);
                if (oldPaisidOfCartaListCarta != null) {
                    oldPaisidOfCartaListCarta.getCartaList().remove(cartaListCarta);
                    oldPaisidOfCartaListCarta = em.merge(oldPaisidOfCartaListCarta);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pais pais) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pais persistentPais = em.find(Pais.class, pais.getId());
            Continente continenteidOld = persistentPais.getContinenteid();
            Continente continenteidNew = pais.getContinenteid();
            List<Joga> jogaListOld = persistentPais.getJogaList();
            List<Joga> jogaListNew = pais.getJogaList();
            List<Pais> paisListOld = persistentPais.getPaisList();
            List<Pais> paisListNew = pais.getPaisList();
            List<Pais> paisList1Old = persistentPais.getPaisList1();
            List<Pais> paisList1New = pais.getPaisList1();
            List<Carta> cartaListOld = persistentPais.getCartaList();
            List<Carta> cartaListNew = pais.getCartaList();
            if (continenteidNew != null) {
                continenteidNew = em.getReference(continenteidNew.getClass(), continenteidNew.getId());
                pais.setContinenteid(continenteidNew);
            }
            List<Joga> attachedJogaListNew = new ArrayList<Joga>();
            for (Joga jogaListNewJogaToAttach : jogaListNew) {
                jogaListNewJogaToAttach = em.getReference(jogaListNewJogaToAttach.getClass(), jogaListNewJogaToAttach.getJogaPK());
                attachedJogaListNew.add(jogaListNewJogaToAttach);
            }
            jogaListNew = attachedJogaListNew;
            pais.setJogaList(jogaListNew);
            List<Pais> attachedPaisListNew = new ArrayList<Pais>();
            for (Pais paisListNewPaisToAttach : paisListNew) {
                paisListNewPaisToAttach = em.getReference(paisListNewPaisToAttach.getClass(), paisListNewPaisToAttach.getId());
                attachedPaisListNew.add(paisListNewPaisToAttach);
            }
            paisListNew = attachedPaisListNew;
            pais.setPaisList(paisListNew);
            List<Pais> attachedPaisList1New = new ArrayList<Pais>();
            for (Pais paisList1NewPaisToAttach : paisList1New) {
                paisList1NewPaisToAttach = em.getReference(paisList1NewPaisToAttach.getClass(), paisList1NewPaisToAttach.getId());
                attachedPaisList1New.add(paisList1NewPaisToAttach);
            }
            paisList1New = attachedPaisList1New;
            pais.setPaisList1(paisList1New);
            List<Carta> attachedCartaListNew = new ArrayList<Carta>();
            for (Carta cartaListNewCartaToAttach : cartaListNew) {
                cartaListNewCartaToAttach = em.getReference(cartaListNewCartaToAttach.getClass(), cartaListNewCartaToAttach.getId());
                attachedCartaListNew.add(cartaListNewCartaToAttach);
            }
            cartaListNew = attachedCartaListNew;
            pais.setCartaList(cartaListNew);
            pais = em.merge(pais);
            if (continenteidOld != null && !continenteidOld.equals(continenteidNew)) {
                continenteidOld.getPaisList().remove(pais);
                continenteidOld = em.merge(continenteidOld);
            }
            if (continenteidNew != null && !continenteidNew.equals(continenteidOld)) {
                continenteidNew.getPaisList().add(pais);
                continenteidNew = em.merge(continenteidNew);
            }
            for (Joga jogaListOldJoga : jogaListOld) {
                if (!jogaListNew.contains(jogaListOldJoga)) {
                    jogaListOldJoga.getPaisList().remove(pais);
                    jogaListOldJoga = em.merge(jogaListOldJoga);
                }
            }
            for (Joga jogaListNewJoga : jogaListNew) {
                if (!jogaListOld.contains(jogaListNewJoga)) {
                    jogaListNewJoga.getPaisList().add(pais);
                    jogaListNewJoga = em.merge(jogaListNewJoga);
                }
            }
            for (Pais paisListOldPais : paisListOld) {
                if (!paisListNew.contains(paisListOldPais)) {
                    paisListOldPais.getPaisList().remove(pais);
                    paisListOldPais = em.merge(paisListOldPais);
                }
            }
            for (Pais paisListNewPais : paisListNew) {
                if (!paisListOld.contains(paisListNewPais)) {
                    paisListNewPais.getPaisList().add(pais);
                    paisListNewPais = em.merge(paisListNewPais);
                }
            }
            for (Pais paisList1OldPais : paisList1Old) {
                if (!paisList1New.contains(paisList1OldPais)) {
                    paisList1OldPais.getPaisList().remove(pais);
                    paisList1OldPais = em.merge(paisList1OldPais);
                }
            }
            for (Pais paisList1NewPais : paisList1New) {
                if (!paisList1Old.contains(paisList1NewPais)) {
                    paisList1NewPais.getPaisList().add(pais);
                    paisList1NewPais = em.merge(paisList1NewPais);
                }
            }
            for (Carta cartaListOldCarta : cartaListOld) {
                if (!cartaListNew.contains(cartaListOldCarta)) {
                    cartaListOldCarta.setPaisid(null);
                    cartaListOldCarta = em.merge(cartaListOldCarta);
                }
            }
            for (Carta cartaListNewCarta : cartaListNew) {
                if (!cartaListOld.contains(cartaListNewCarta)) {
                    Pais oldPaisidOfCartaListNewCarta = cartaListNewCarta.getPaisid();
                    cartaListNewCarta.setPaisid(pais);
                    cartaListNewCarta = em.merge(cartaListNewCarta);
                    if (oldPaisidOfCartaListNewCarta != null && !oldPaisidOfCartaListNewCarta.equals(pais)) {
                        oldPaisidOfCartaListNewCarta.getCartaList().remove(cartaListNewCarta);
                        oldPaisidOfCartaListNewCarta = em.merge(oldPaisidOfCartaListNewCarta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pais.getId();
                if (findPais(id) == null) {
                    throw new NonexistentEntityException("The pais with id " + id + " no longer exists.");
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
            Pais pais;
            try {
                pais = em.getReference(Pais.class, id);
                pais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pais with id " + id + " no longer exists.", enfe);
            }
            Continente continenteid = pais.getContinenteid();
            if (continenteid != null) {
                continenteid.getPaisList().remove(pais);
                continenteid = em.merge(continenteid);
            }
            List<Joga> jogaList = pais.getJogaList();
            for (Joga jogaListJoga : jogaList) {
                jogaListJoga.getPaisList().remove(pais);
                jogaListJoga = em.merge(jogaListJoga);
            }
            List<Pais> paisList = pais.getPaisList();
            for (Pais paisListPais : paisList) {
                paisListPais.getPaisList().remove(pais);
                paisListPais = em.merge(paisListPais);
            }
            List<Pais> paisList1 = pais.getPaisList1();
            for (Pais paisList1Pais : paisList1) {
                paisList1Pais.getPaisList().remove(pais);
                paisList1Pais = em.merge(paisList1Pais);
            }
            List<Carta> cartaList = pais.getCartaList();
            for (Carta cartaListCarta : cartaList) {
                cartaListCarta.setPaisid(null);
                cartaListCarta = em.merge(cartaListCarta);
            }
            em.remove(pais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pais> findPaisEntities() {
        return findPaisEntities(true, -1, -1);
    }

    public List<Pais> findPaisEntities(int maxResults, int firstResult) {
        return findPaisEntities(false, maxResults, firstResult);
    }

    private List<Pais> findPaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pais.class));
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

    public Pais findPais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pais.class, id);
        } finally {
            em.close();
        }
    }

    public int getPaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pais> rt = cq.from(Pais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
