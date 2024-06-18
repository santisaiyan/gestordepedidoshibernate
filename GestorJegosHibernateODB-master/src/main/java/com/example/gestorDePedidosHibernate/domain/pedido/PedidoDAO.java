package com.example.gestorDePedidosHibernate.domain.pedido;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.ODB;
//import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para consultas relacionadas con los pedidos
 */
@Log
public class PedidoDAO implements DAO<Pedido> {
    @Override
    public ArrayList<Pedido> getAll( ) {
        return null;
    }

    @Override
    public Pedido get( Long id ) {
        return null;
    }

    /**
     * Guardar un nuevo pedido en la base de datos
     * @param data Pedido nuevo
     * @return Devuelve el nuevo pedido
     */
    @Override
    public Pedido save( Pedido data ) {
        EntityManager em = null;
        Pedido salida = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Persiste el objeto Pedido
            em.persist(data);

            tx.commit();
            salida = data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return salida;
    }

    @Override
    public void update( Pedido data ) {

    }

    /**
     * Borrar un pedido
     * @param data Pedido a borrar
     */
    @Override
    public void delete( Pedido data ) {

        EntityManager em = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Busca los items asociados al pedido y elimínalos
            Query itemQuery = em.createQuery( "SELECT i FROM Item i WHERE i.pedido = :pedido", Item.class);
            itemQuery.setParameter("pedido", data);
            List<Item> items = itemQuery.getResultList();
            items.forEach(em::remove);

            // Elimina el pedido
            Pedido pedido = em.find(Pedido.class, data.getId_pedido());
            em.remove(pedido);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Pedidos que corresponde a un usuario
     * @param usuario Usuario por el cual filtrar los pedidos
     * @return Lista de los pedidos del usuario
     */
    public List<Pedido> pedidosDeUnUsuario( Usuario usuario ) {

        EntityManager em = null;
        List<Pedido> salida = new ArrayList<>();

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery( "SELECT u FROM Usuario u WHERE u.id_usuario = :id", Usuario.class);
            q.setParameter("id", usuario.getId_usuario());
            Usuario usuarioEncontrado = ( Usuario ) q.getSingleResult();

            if (usuarioEncontrado != null) {
                salida = usuarioEncontrado.getPedidos();

                for (Pedido pedido : salida) {
                    Double total = 0.0;

                    // Calcula el total del pedido
                    for (Item item : pedido.getItems()) {
                        total += item.getCantidad() * item.getProducto().getPrecio();
                    }

                    DecimalFormat formato = new DecimalFormat("#0.00");
                    pedido.setTotal(formato.format(total));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return salida;
    }

    /**
     * Items correspondiente a un pedido
     * @param pedidoPulsado Pedido por el que filtrar los items
     * @return Lista  de Items correspondiente a un pedido
     */
    public List<Item> detallesDeUnPedido( Pedido pedidoPulsado ) {
        EntityManager em = null;
        List<Item> result = new ArrayList<>();

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery( "SELECT p FROM Pedido p WHERE p.id_pedido = :id", Pedido.class);
            q.setParameter("id", pedidoPulsado.getId_pedido());
            Pedido pedidoEncontrado = ( Pedido ) q.getSingleResult();

            if (pedidoEncontrado != null) {
                result = new ArrayList<>(pedidoEncontrado.getItems());
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

    /**
     * Todos los productos de la base de datos
     * @return Lista de todos los productos que estan en la base de datos
     */

    public List<String> todosLosProductos( ) {
        EntityManager em = null;
        List<String> resultado = new ArrayList<>();

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery("SELECT DISTINCT p.nombre FROM Producto p", String.class);
            resultado = q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }

        return resultado;
    }

    /**
     * Añadir nuevo item a un pedido
     * @param ped Pedido al cual añadir el item
     * @param cant Cantidad del producto añadido
     * @param prod Producto correspondiente al nuevo item
     */
    public void insertarItemAPedido( Pedido ped , Integer cant , Producto prod ) {
        EntityManager em = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            EntityTransaction tx = em.getTransaction();
            tx.begin();

            // Crear un nuevo item
            Item item = new Item();
            item.setCantidad(cant);
            item.setPedido(ped);
            item.setProducto(prod);

            // Persistir el nuevo item
            em.persist(item);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Busca un producto en un pedido
     * @param nombreProducto Nombre del producto a buscar
     * @param pedido Pedido donde buscar el producto
     * @return Producto si esta en el pedido o null si no está
     */
    public Producto buscaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        EntityManager em = null;
        Producto producto = null;

        try {

            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery(
                    "SELECT i.producto FROM Item i WHERE i.producto.nombre = :nombre AND i.pedido.id_pedido = :idPedido", Producto.class);
            q.setParameter("nombre", nombreProducto);
            q.setParameter("idPedido", pedido.getId_pedido());
            producto = ( Producto ) q.getSingleResult();
        } catch (Exception e) {

        } finally {
            if (em != null) {
                em.close();
            }
        }

        return producto;
    }

    /**
     * Checkea si el producto esta en el pedido
     * @param nombreProducto Nombre del producto a buscar
     * @param pedido Pedido donde buscar el producto
     * @return True si existe o false si no existe el producto
     */
    public boolean estaProductoEnPedido( String nombreProducto , Pedido pedido ) {
        return buscaProductoEnPedido( nombreProducto , pedido ) != null;
    }

    /**
     * Actualiza la fecha del pedido pulsado
     */
    public void actualizarFecha(Pedido ped){
        EntityManager em = null;

        try {

            em = ODB.getEntityManagerFactory().createEntityManager(  );
            em.getTransaction().begin();

            Pedido p = em.find(Pedido.class, ped.getId_pedido());
            p.setFecha(LocalDate.now().toString());

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
