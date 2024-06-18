package com.example.gestorDePedidosHibernate.domain.item;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.ODB;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

/**
 * DAO para consultas relacionadas con los items
 */
public class ItemDAO implements DAO<Item> {
    @Override
    public List<Item> getAll() {
        return null;
    }

    @Override
    public Item get(Long id) {
        return null;
    }

    @Override
    public Item save(Item data) {
        return null;
    }

    /**
     * Actualizar un Item
     * @param data item modificado
     */
    @Override
    public void update(Item data) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            tx = em.getTransaction();
            tx.begin();

            // Recupera el objeto Item y Producto
            Item nuevoItem = em.find(Item.class, data.getId_item());
            Producto nuevoProducto = em.find(Producto.class, data.getProducto().getId_producto());

            // Actualiza los campos del objeto Item
            nuevoItem.setCantidad(data.getCantidad());
            nuevoItem.setProducto(nuevoProducto);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    /**
     * Borra un item
     * @param data Item que borrar
     */
    @Override
    public void delete(Item data) {
        EntityManager em = null;
        EntityTransaction tx = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            tx = em.getTransaction();
            tx.begin();

            // Recupera el objeto Item y elimínalo
            Item i = em.find(Item.class, data.getId_item());
            em.remove(i);

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    /**
     * Devuelve el Item si existe un producto en un pedido
     * @param p Pedido en el cual buscar
     * @param nombreProducto Nombre del producto a buscr
     * @return Item del pedido con ese producto o null si no existe
     */
    public Item itemEnPedidoPorNombre(Pedido p, String nombreProducto){
        EntityManager em = null;
        Item result = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery( "SELECT i FROM Item i WHERE i.producto.nombre = :nombre AND i.pedido.id_pedido = :idPedido", Item.class);
            q.setParameter("nombre", nombreProducto);
            q.setParameter("idPedido", p.getId_pedido());

            List<Item> resultList = q.getResultList();
            if (!resultList.isEmpty()) {
                result = resultList.get(0);
            }
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
