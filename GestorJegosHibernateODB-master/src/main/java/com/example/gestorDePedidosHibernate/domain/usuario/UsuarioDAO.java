package com.example.gestorDePedidosHibernate.domain.usuario;

import com.example.gestorDePedidosHibernate.domain.DAO;
import com.example.gestorDePedidosHibernate.domain.ODB;
import lombok.extern.java.Log;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Log
public class UsuarioDAO implements DAO<Usuario> {

    @Override
    public List<Usuario> getAll() {
        List<Usuario> result;
        EntityManager em = ODB.getEntityManagerFactory().createEntityManager(  );
        em.getTransaction().begin();
        TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
        return query.getResultList();
    }

    @Override
    public Usuario get(Long id) {
        return null;
    }

    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    @Override
    public void update(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {

    }

    public boolean isCorrectUser(String user, String pass) {
        return loadLogin(user,pass) != null;
    }

    public Usuario loadLogin(String user, String pass) {
        EntityManager em = null;
        Usuario result = null;

        try {
            em = ODB.getEntityManagerFactory().createEntityManager(  );
            Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.nombreusuario = :user AND u.pass = :pass", Usuario.class);
            q.setParameter("user", user);
            q.setParameter("pass", pass);

            List<Usuario> resultList = q.getResultList();
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

