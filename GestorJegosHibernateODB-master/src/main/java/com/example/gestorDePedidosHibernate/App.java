package com.example.gestorDePedidosHibernate;

import com.example.gestorDePedidosHibernate.domain.item.ItemDAO;
import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
import com.example.gestorDePedidosHibernate.domain.pedido.PedidoDAO;
import com.example.gestorDePedidosHibernate.domain.producto.Producto;
import com.example.gestorDePedidosHibernate.domain.producto.ProductoDAO;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import com.example.gestorDePedidosHibernate.domain.usuario.UsuarioDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Aplicacion principal
 */
public class App extends Application {
    @Getter
    @Setter
    private static Stage myStage;
    @Override
    public void start(Stage stage) throws IOException {
        myStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controllers/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login!");
        stage.setMinHeight( 600 );
        stage.setMinWidth( 1000 );
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cargar escena y cambiar el titulo.
     * @param fxml nombre del archivo donde está la nueva escena
     * @param titulo Nombre del nuevo título
     */
    public static void loadFXML(String fxml, String titulo){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("controllers/"+fxml));
            Scene scene = new Scene(fxmlLoader.load());
            myStage.setTitle(titulo);
            myStage.setScene(scene);
            myStage.show();
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo "+fxml);
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo main
     * @param args No se usan argumentos
     */
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory( "data.odb");
        EntityManager em = emf.createEntityManager();
//
//        try {
//            em.getTransaction().begin();
//            Usuario u = new Usuario();
//            u.setNombreusuario( "laura" );
//            u.setId_usuario( 3L );
//            u.setPass("123");
//            u.setEmail( "laura@email.com" );
//            u.setPedidos( new ArrayList<>(  ) );
//            em.persist( u );
//            em.getTransaction().commit();
//            Producto p1 = crearProducto(1000.0, "Iphone 15", 100);
//            Producto p2 = crearProducto(1200.0, "Samsung Galaxy S22", 80);
//            Producto p3 = crearProducto(800.0, "Google Pixel 6", 120);
//            Producto p4 = crearProducto(1500.0, "OnePlus 9 Pro", 90);
//            Producto p5 = crearProducto(700.0, "Xiaomi Redmi Note 10", 150);
//            Producto p6 = crearProducto(1300.0, "Huawei P50", 70);
//            Producto p7 = crearProducto(900.0, "Sony Xperia 5 III", 110);
//            Producto p8 = crearProducto(1100.0, "LG G8 ThinQ", 95);
//            Producto p9 = crearProducto(1000.0, "Motorola Edge 20", 105);
//            Producto p10 = crearProducto(1400.0, "Nokia 9 PureView", 75);
//            em.persist( p1 );
//            em.persist( p2 );
//            em.persist( p3 );
//            em.persist( p4 );
//            em.persist( p5 );
//            em.persist( p6 );
//            em.persist( p7 );
//            em.persist( p8 );
//            em.persist( p9 );
//            em.persist( p10 );
//            em.getTransaction().commit();
//
//        }
//        finally {
//            em.close();
//        }

        launch();
    }

    private static Producto crearProducto(double precio, String nombre, int cantidad) {
        Producto producto = new Producto();
        producto.setPrecio(precio);
        producto.setNombre(nombre);
        producto.setCantidad(cantidad);
        producto.setItems( new ArrayList<>(  ) );
        return producto;
    }
}