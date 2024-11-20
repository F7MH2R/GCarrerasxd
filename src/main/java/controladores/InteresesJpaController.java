/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import controladores.exceptions.NonexistentEntityException;
import controladores.exceptions.RollbackFailureException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.io.Serializable;
import jakarta.persistence.Query;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import java.util.List;
import modelo.Intereses;

/**
 *
 * @author black
 */
public class InteresesJpaController implements Serializable {

    public InteresesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Intereses intereses) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(intereses);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Intereses intereses) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            intereses = em.merge(intereses);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = intereses.getIdInteres();
                if (findIntereses(id) == null) {
                    throw new NonexistentEntityException("The intereses with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Intereses intereses;
            try {
                intereses = em.getReference(Intereses.class, id);
                intereses.getIdInteres();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The intereses with id " + id + " no longer exists.", enfe);
            }
            em.remove(intereses);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Intereses> findInteresesEntities() {
        return findInteresesEntities(true, -1, -1);
    }

    public List<Intereses> findInteresesEntities(int maxResults, int firstResult) {
        return findInteresesEntities(false, maxResults, firstResult);
    }

    private List<Intereses> findInteresesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Intereses.class));
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

    public Intereses findIntereses(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Intereses.class, id);
        } finally {
            em.close();
        }
    }

    public int getInteresesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Intereses> rt = cq.from(Intereses.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public int countInteresesByCarrera(int carreraId) {
        EntityManager em = getEntityManager();
        try {
            String jpql = "SELECT COUNT(i) FROM Intereses i WHERE i.idCarrera = :carreraId";
            return ((Long) em.createQuery(jpql)
                    .setParameter("carreraId", carreraId)
                    .getSingleResult())
                    .intValue();
        } finally {
            em.close();
        }
    }

    public List<Object[]> countInteresesByCarrera() {
        EntityManager em = getEntityManager();
        try {
            // Consulta para contar intereses agrupados por carrera
            String query = "SELECT c.nombreCarrera, COUNT(i.idInteres) "
                    + "FROM Intereses i JOIN Carreras c ON i.idCarrera = c.idCarrera "
                    + "GROUP BY c.nombreCarrera";
            Query q = em.createQuery(query);

            // Retornar el resultado como una lista de arreglos de objetos
            // Cada objeto en la lista será un arreglo con dos elementos:
            // [0]: nombreCarrera (String)
            // [1]: cantidad de intereses (Long)
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Integer> findInteresesByUsuario(Integer idUsuario) {
        EntityManager em = getEntityManager();
        try {
            // Consulta para obtener los IDs de carreras que están relacionadas con el usuario
            Query query = em.createQuery("SELECT i.idCarrera FROM Intereses i WHERE i.idUsuario = :idUsuario");
            query.setParameter("idUsuario", idUsuario);

            // Retornar la lista de IDs de carreras
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Intereses findInteresByUsuarioAndCarrera(Integer idUsuario, Integer idCarrera) {
        EntityManager em = getEntityManager();
        try {
            // Consulta para obtener un interés específico por usuario y carrera
            Query query = em.createQuery("SELECT i FROM Intereses i WHERE i.idUsuario = :idUsuario AND i.idCarrera = :idCarrera");
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idCarrera", idCarrera);

            // Retornar el resultado si se encuentra
            return (Intereses) query.getSingleResult();
        } catch (NoResultException e) {
            // Si no encuentra un resultado, retornar null
            return null;
        } finally {
            em.close();
        }
    }

    public List<Intereses> findInteresesByUsuario(int idUsuario) {
        EntityManager em = getEntityManager();
        try {
            // Consulta para buscar los intereses del usuario
            Query query = em.createQuery("SELECT i FROM Intereses i WHERE i.idUsuario = :idUsuario");
            query.setParameter("idUsuario", idUsuario);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejar error devolviendo null
        } finally {
            em.close(); // Cerrar EntityManager
        }
    }

}
