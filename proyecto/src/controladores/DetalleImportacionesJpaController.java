/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import tablas.DetalleImportaciones;
import tablas.Importaciones;

/**
 *
 * @author manue
 */
public class DetalleImportacionesJpaController implements Serializable {

    public DetalleImportacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleImportaciones detalleImportaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Importaciones importacionescodigo = detalleImportaciones.getImportacionescodigo();
            if (importacionescodigo != null) {
                importacionescodigo = em.getReference(importacionescodigo.getClass(), importacionescodigo.getCodigo());
                detalleImportaciones.setImportacionescodigo(importacionescodigo);
            }
            em.persist(detalleImportaciones);
            if (importacionescodigo != null) {
                importacionescodigo.getDetalleImportacionesList().add(detalleImportaciones);
                importacionescodigo = em.merge(importacionescodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleImportaciones detalleImportaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleImportaciones persistentDetalleImportaciones = em.find(DetalleImportaciones.class, detalleImportaciones.getCodigo());
            Importaciones importacionescodigoOld = persistentDetalleImportaciones.getImportacionescodigo();
            Importaciones importacionescodigoNew = detalleImportaciones.getImportacionescodigo();
            if (importacionescodigoNew != null) {
                importacionescodigoNew = em.getReference(importacionescodigoNew.getClass(), importacionescodigoNew.getCodigo());
                detalleImportaciones.setImportacionescodigo(importacionescodigoNew);
            }
            detalleImportaciones = em.merge(detalleImportaciones);
            if (importacionescodigoOld != null && !importacionescodigoOld.equals(importacionescodigoNew)) {
                importacionescodigoOld.getDetalleImportacionesList().remove(detalleImportaciones);
                importacionescodigoOld = em.merge(importacionescodigoOld);
            }
            if (importacionescodigoNew != null && !importacionescodigoNew.equals(importacionescodigoOld)) {
                importacionescodigoNew.getDetalleImportacionesList().add(detalleImportaciones);
                importacionescodigoNew = em.merge(importacionescodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleImportaciones.getCodigo();
                if (findDetalleImportaciones(id) == null) {
                    throw new NonexistentEntityException("The detalleImportaciones with id " + id + " no longer exists.");
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
            DetalleImportaciones detalleImportaciones;
            try {
                detalleImportaciones = em.getReference(DetalleImportaciones.class, id);
                detalleImportaciones.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleImportaciones with id " + id + " no longer exists.", enfe);
            }
            Importaciones importacionescodigo = detalleImportaciones.getImportacionescodigo();
            if (importacionescodigo != null) {
                importacionescodigo.getDetalleImportacionesList().remove(detalleImportaciones);
                importacionescodigo = em.merge(importacionescodigo);
            }
            em.remove(detalleImportaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleImportaciones> findDetalleImportacionesEntities() {
        return findDetalleImportacionesEntities(true, -1, -1);
    }

    public List<DetalleImportaciones> findDetalleImportacionesEntities(int maxResults, int firstResult) {
        return findDetalleImportacionesEntities(false, maxResults, firstResult);
    }

    private List<DetalleImportaciones> findDetalleImportacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleImportaciones.class));
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

    public DetalleImportaciones findDetalleImportaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleImportaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleImportacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleImportaciones> rt = cq.from(DetalleImportaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
