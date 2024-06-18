package com.example.gestorDePedidosHibernate.domain.producto;

import com.example.gestorDePedidosHibernate.domain.item.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * clase producto que representa un producto dentro de un item
 */
@NoArgsConstructor
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_producto;
    private String nombre;
    private Double precio;
    private Integer cantidad;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private List<Item> items;

    public Producto( Long id_producto , String nombre , Double precio , Integer cantidad ) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    @Override
    public String toString( ) {
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                ", items size=" + items.size( ) +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Producto producto)) return false;
        return Objects.equals( id_producto , producto.id_producto ) && Objects.equals( nombre , producto.nombre ) && Objects.equals( precio , producto.precio ) && Objects.equals( cantidad , producto.cantidad );
    }

    @Override
    public int hashCode( ) {
        return Objects.hash( id_producto , nombre , precio , cantidad );
    }
}
