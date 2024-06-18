package com.example.gestorDePedidosHibernate.domain.producto;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.ODB;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * DAO para consultas relacionadas con los productos
 */
public class ProductoDAO implements DAO<Producto> {
    @Override
    public List<Producto> getAll() {
        return null;
    }

    @Override
    public Producto get(Long id) {
        return null;
    }

    @Override
    public Producto save(Producto data) {
        return null;
    }

    @Override
    public void update(Producto data) {

    }

    @Override
    public void delete(Producto data) {

    }

    /**
     * Devuelve una instancia de un producto por su nombre
     * @param nombreProducto Nombre a buscar
     * @return Devuelve un objeto del producto con ese nombre o null si no existe
     */
    public Producto productoPorNombre(String nombreProducto){
        EntityManager em = null;
        Producto result = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery("SELECT p FROM Producto p WHERE p.nombre = :nombre", Producto.class);
            q.setParameter("nombre", nombreProducto);
            result = ( Producto ) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return result;
    }
}
