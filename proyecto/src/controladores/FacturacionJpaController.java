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
import tablas.DetalleFacturacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import tablas.Facturacion;

/**
 *
 * @author manue
 */
public class FacturacionJpaController implements Serializable {

    public FacturacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Facturacion facturacion) {
        if (facturacion.getDetalleFacturacionList() == null) {
            facturacion.setDetalleFacturacionList(new ArrayList<DetalleFacturacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<DetalleFacturacion> attachedDetalleFacturacionList = new ArrayList<DetalleFacturacion>();
            for (DetalleFacturacion detalleFacturacionListDetalleFacturacionToAttach : facturacion.getDetalleFacturacionList()) {
                detalleFacturacionListDetalleFacturacionToAttach = em.getReference(detalleFacturacionListDetalleFacturacionToAttach.getClass(), detalleFacturacionListDetalleFacturacionToAttach.getCodigo());
                attachedDetalleFacturacionList.add(detalleFacturacionListDetalleFacturacionToAttach);
            }
            facturacion.setDetalleFacturacionList(attachedDetalleFacturacionList);
            em.persist(facturacion);
            for (DetalleFacturacion detalleFacturacionListDetalleFacturacion : facturacion.getDetalleFacturacionList()) {
                Facturacion oldFacturacioncodigoOfDetalleFacturacionListDetalleFacturacion = detalleFacturacionListDetalleFacturacion.getFacturacioncodigo();
                detalleFacturacionListDetalleFacturacion.setFacturacioncodigo(facturacion);
                detalleFacturacionListDetalleFacturacion = em.merge(detalleFacturacionListDetalleFacturacion);
                if (oldFacturacioncodigoOfDetalleFacturacionListDetalleFacturacion != null) {
                    oldFacturacioncodigoOfDetalleFacturacionListDetalleFacturacion.getDetalleFacturacionList().remove(detalleFacturacionListDetalleFacturacion);
                    oldFacturacioncodigoOfDetalleFacturacionListDetalleFacturacion = em.merge(oldFacturacioncodigoOfDetalleFacturacionListDetalleFacturacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Facturacion facturacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Facturacion persistentFacturacion = em.find(Facturacion.class, facturacion.getCodigo());
            List<DetalleFacturacion> detalleFacturacionListOld = persistentFacturacion.getDetalleFacturacionList();
            List<DetalleFacturacion> detalleFacturacionListNew = facturacion.getDetalleFacturacionList();
            List<String> illegalOrphanMessages = null;
            for (DetalleFacturacion detalleFacturacionListOldDetalleFacturacion : detalleFacturacionListOld) {
                if (!detalleFacturacionListNew.contains(detalleFacturacionListOldDetalleFacturacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain DetalleFacturacion " + detalleFacturacionListOldDetalleFacturacion + " since its facturacioncodigo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<DetalleFacturacion> attachedDetalleFacturacionListNew = new ArrayList<DetalleFacturacion>();
            for (DetalleFacturacion detalleFacturacionListNewDetalleFacturacionToAttach : detalleFacturacionListNew) {
                detalleFacturacionListNewDetalleFacturacionToAttach = em.getReference(detalleFacturacionListNewDetalleFacturacionToAttach.getClass(), detalleFacturacionListNewDetalleFacturacionToAttach.getCodigo());
                attachedDetalleFacturacionListNew.add(detalleFacturacionListNewDetalleFacturacionToAttach);
            }
            detalleFacturacionListNew = attachedDetalleFacturacionListNew;
            facturacion.setDetalleFacturacionList(detalleFacturacionListNew);
            facturacion = em.merge(facturacion);
            for (DetalleFacturacion detalleFacturacionListNewDetalleFacturacion : detalleFacturacionListNew) {
                if (!detalleFacturacionListOld.contains(detalleFacturacionListNewDetalleFacturacion)) {
                    Facturacion oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion = detalleFacturacionListNewDetalleFacturacion.getFacturacioncodigo();
                    detalleFacturacionListNewDetalleFacturacion.setFacturacioncodigo(facturacion);
                    detalleFacturacionListNewDetalleFacturacion = em.merge(detalleFacturacionListNewDetalleFacturacion);
                    if (oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion != null && !oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion.equals(facturacion)) {
                        oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion.getDetalleFacturacionList().remove(detalleFacturacionListNewDetalleFacturacion);
                        oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion = em.merge(oldFacturacioncodigoOfDetalleFacturacionListNewDetalleFacturacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturacion.getCodigo();
                if (findFacturacion(id) == null) {
                    throw new NonexistentEntityException("The facturacion with id " + id + " no longer exists.");
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
            Facturacion facturacion;
            try {
                facturacion = em.getReference(Facturacion.class, id);
                facturacion.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<DetalleFacturacion> detalleFacturacionListOrphanCheck = facturacion.getDetalleFacturacionList();
            for (DetalleFacturacion detalleFacturacionListOrphanCheckDetalleFacturacion : detalleFacturacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Facturacion (" + facturacion + ") cannot be destroyed since the DetalleFacturacion " + detalleFacturacionListOrphanCheckDetalleFacturacion + " in its detalleFacturacionList field has a non-nullable facturacioncodigo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(facturacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Facturacion> findFacturacionEntities() {
        return findFacturacionEntities(true, -1, -1);
    }

    public List<Facturacion> findFacturacionEntities(int maxResults, int firstResult) {
        return findFacturacionEntities(false, maxResults, firstResult);
    }

    private List<Facturacion> findFacturacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Facturacion.class));
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

    public Facturacion findFacturacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Facturacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Facturacion> rt = cq.from(Facturacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
