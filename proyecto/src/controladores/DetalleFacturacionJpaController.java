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
import tablas.DetalleFacturacion;
import tablas.Facturacion;

/**
 *
 * @author manue
 */
public class DetalleFacturacionJpaController implements Serializable {

    public DetalleFacturacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DetalleFacturacion detalleFacturacion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacion facturacioncodigo = detalleFacturacion.getFacturacioncodigo();
            if (facturacioncodigo != null) {
                facturacioncodigo = em.getReference(facturacioncodigo.getClass(), facturacioncodigo.getCodigo());
                detalleFacturacion.setFacturacioncodigo(facturacioncodigo);
            }
            em.persist(detalleFacturacion);
            if (facturacioncodigo != null) {
                facturacioncodigo.getDetalleFacturacionList().add(detalleFacturacion);
                facturacioncodigo = em.merge(facturacioncodigo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DetalleFacturacion detalleFacturacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            DetalleFacturacion persistentDetalleFacturacion = em.find(DetalleFacturacion.class, detalleFacturacion.getCodigo());
            Facturacion facturacioncodigoOld = persistentDetalleFacturacion.getFacturacioncodigo();
            Facturacion facturacioncodigoNew = detalleFacturacion.getFacturacioncodigo();
            if (facturacioncodigoNew != null) {
                facturacioncodigoNew = em.getReference(facturacioncodigoNew.getClass(), facturacioncodigoNew.getCodigo());
                detalleFacturacion.setFacturacioncodigo(facturacioncodigoNew);
            }
            detalleFacturacion = em.merge(detalleFacturacion);
            if (facturacioncodigoOld != null && !facturacioncodigoOld.equals(facturacioncodigoNew)) {
                facturacioncodigoOld.getDetalleFacturacionList().remove(detalleFacturacion);
                facturacioncodigoOld = em.merge(facturacioncodigoOld);
            }
            if (facturacioncodigoNew != null && !facturacioncodigoNew.equals(facturacioncodigoOld)) {
                facturacioncodigoNew.getDetalleFacturacionList().add(detalleFacturacion);
                facturacioncodigoNew = em.merge(facturacioncodigoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = detalleFacturacion.getCodigo();
                if (findDetalleFacturacion(id) == null) {
                    throw new NonexistentEntityException("The detalleFacturacion with id " + id + " no longer exists.");
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
            DetalleFacturacion detalleFacturacion;
            try {
                detalleFacturacion = em.getReference(DetalleFacturacion.class, id);
                detalleFacturacion.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The detalleFacturacion with id " + id + " no longer exists.", enfe);
            }
            Facturacion facturacioncodigo = detalleFacturacion.getFacturacioncodigo();
            if (facturacioncodigo != null) {
                facturacioncodigo.getDetalleFacturacionList().remove(detalleFacturacion);
                facturacioncodigo = em.merge(facturacioncodigo);
            }
            em.remove(detalleFacturacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DetalleFacturacion> findDetalleFacturacionEntities() {
        return findDetalleFacturacionEntities(true, -1, -1);
    }

    public List<DetalleFacturacion> findDetalleFacturacionEntities(int maxResults, int firstResult) {
        return findDetalleFacturacionEntities(false, maxResults, firstResult);
    }

    private List<DetalleFacturacion> findDetalleFacturacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DetalleFacturacion.class));
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

    public DetalleFacturacion findDetalleFacturacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DetalleFacturacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getDetalleFacturacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DetalleFacturacion> rt = cq.from(DetalleFacturacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
