/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controladores;

import controladores.exceptions.IllegalOrphanException;
import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tablas.DetalleImportaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tablas.Importaciones;

/**
 *
 * @author manue
 */
public class ImportacionesJpaController implements Serializable {

    public ImportacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Importaciones importaciones) {
        if (importaciones.getDetalleImportacionesList() == null) {
            importaciones.setDetalleImportacionesList(new ArrayList<DetalleImportaciones>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleImportaciones> attachedDetalleImportacionesList = new ArrayList<DetalleImportaciones>();
            for (DetalleImportaciones detalleImportacionesListDetalleImportacionesToAttach : importaciones.getDetalleImportacionesList()) {
                detalleImportacionesListDetalleImportacionesToAttach = em.getReference(detalleImportacionesListDetalleImportacionesToAttach.getClass(), detalleImportacionesListDetalleImportacionesToAttach.getCodigo());
                attachedDetalleImportacionesList.add(detalleImportacionesListDetalleImportacionesToAttach);
            }
            importaciones.setDetalleImportacionesList(attachedDetalleImportacionesList);
            em.persist(importaciones);
            for (DetalleImportaciones detalleImportacionesListDetalleImportaciones : importaciones.getDetalleImportacionesList()) {
                Importaciones oldImportacionescodigoOfDetalleImportacionesListDetalleImportaciones = detalleImportacionesListDetalleImportaciones.getImportacionescodigo();
                detalleImportacionesListDetalleImportaciones.setImportacionescodigo(importaciones);
                detalleImportacionesListDetalleImportaciones = em.merge(detalleImportacionesListDetalleImportaciones);
                if (oldImportacionescodigoOfDetalleImportacionesListDetalleImportaciones != null) {
                    oldImportacionescodigoOfDetalleImportacionesListDetalleImportaciones.getDetalleImportacionesList().remove(detalleImportacionesListDetalleImportaciones);
                    oldImportacionescodigoOfDetalleImportacionesListDetalleImportaciones = em.merge(oldImportacionescodigoOfDetalleImportacionesListDetalleImportaciones);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Importaciones importaciones) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Importaciones persistentImportaciones = em.find(Importaciones.class, importaciones.getCodigo());
            List<DetalleImportaciones> detalleImportacionesListOld = persistentImportaciones.getDetalleImportacionesList();
            List<DetalleImportaciones> detalleImportacionesListNew = importaciones.getDetalleImportacionesList();
            List<String> illegalOrphanMessages = null;
            for (DetalleImportaciones detalleImportacionesListOldDetalleImportaciones : detalleImportacionesListOld) {
                if (!detalleImportacionesListNew.contains(detalleImportacionesListOldDetalleImportaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleImportaciones " + detalleImportacionesListOldDetalleImportaciones + " since its importacionescodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleImportaciones> attachedDetalleImportacionesListNew = new ArrayList<DetalleImportaciones>();
            for (DetalleImportaciones detalleImportacionesListNewDetalleImportacionesToAttach : detalleImportacionesListNew) {
                detalleImportacionesListNewDetalleImportacionesToAttach = em.getReference(detalleImportacionesListNewDetalleImportacionesToAttach.getClass(), detalleImportacionesListNewDetalleImportacionesToAttach.getCodigo());
                attachedDetalleImportacionesListNew.add(detalleImportacionesListNewDetalleImportacionesToAttach);
            }
            detalleImportacionesListNew = attachedDetalleImportacionesListNew;
            importaciones.setDetalleImportacionesList(detalleImportacionesListNew);
            importaciones = em.merge(importaciones);
            for (DetalleImportaciones detalleImportacionesListNewDetalleImportaciones : detalleImportacionesListNew) {
                if (!detalleImportacionesListOld.contains(detalleImportacionesListNewDetalleImportaciones)) {
                    Importaciones oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones = detalleImportacionesListNewDetalleImportaciones.getImportacionescodigo();
                    detalleImportacionesListNewDetalleImportaciones.setImportacionescodigo(importaciones);
                    detalleImportacionesListNewDetalleImportaciones = em.merge(detalleImportacionesListNewDetalleImportaciones);
                    if (oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones != null && !oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones.equals(importaciones)) {
                        oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones.getDetalleImportacionesList().remove(detalleImportacionesListNewDetalleImportaciones);
                        oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones = em.merge(oldImportacionescodigoOfDetalleImportacionesListNewDetalleImportaciones);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = importaciones.getCodigo();
                if (findImportaciones(id) == null) {
                    throw new NonexistentEntityException("The importaciones with id " + id + " no longer exists.");
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
            Importaciones importaciones;
            try {
                importaciones = em.getReference(Importaciones.class, id);
                importaciones.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The importaciones with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleImportaciones> detalleImportacionesListOrphanCheck = importaciones.getDetalleImportacionesList();
            for (DetalleImportaciones detalleImportacionesListOrphanCheckDetalleImportaciones : detalleImportacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Importaciones (" + importaciones + ") cannot be destroyed since the DetalleImportaciones " + detalleImportacionesListOrphanCheckDetalleImportaciones + " in its detalleImportacionesList field has a non-nullable importacionescodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(importaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Importaciones> findImportacionesEntities() {
        return findImportacionesEntities(true, -1, -1);
    }

    public List<Importaciones> findImportacionesEntities(int maxResults, int firstResult) {
        return findImportacionesEntities(false, maxResults, firstResult);
    }

    private List<Importaciones> findImportacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Importaciones.class));
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

    public Importaciones findImportaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Importaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getImportacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Importaciones> rt = cq.from(Importaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
