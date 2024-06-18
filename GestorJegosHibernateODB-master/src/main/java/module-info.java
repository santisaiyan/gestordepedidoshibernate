module com.example.gestorjegoshibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    requires java.naming;
    requires java.sql;
    requires jasperreports;
    requires java.desktop;
    requires javax.persistence; //para fechas

    opens com.example.gestorDePedidosHibernate.domain.usuario;
    opens com.example.gestorDePedidosHibernate.domain.pedido;
    opens com.example.gestorDePedidosHibernate.domain.item;
    opens com.example.gestorDePedidosHibernate.domain.producto;
    opens com.example.gestorDePedidosHibernate to javafx.fxml;
    exports com.example.gestorDePedidosHibernate;
    exports com.example.gestorDePedidosHibernate.controllers;
    opens com.example.gestorDePedidosHibernate.controllers to javafx.fxml;
}