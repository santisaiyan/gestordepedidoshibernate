package com.example.gestorDePedidosHibernate.domain;

import lombok.extern.java.Log;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utilidades de ObjectDB
 */
@Log
public class ODB {
    private static final EntityManagerFactory emf;

    static{
        emf = Persistence.createEntityManagerFactory("data.odb");
    }

    /**
     * Entity Manager Factory
     * @return devuelve una instancia de EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory(){

        return emf;
    }
}
