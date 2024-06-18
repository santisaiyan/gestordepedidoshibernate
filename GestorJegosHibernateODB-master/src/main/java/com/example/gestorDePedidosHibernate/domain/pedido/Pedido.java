package com.example.gestorDePedidosHibernate.domain.pedido;

import com.example.gestorDePedidosHibernate.domain.item.Item;
import com.example.gestorDePedidosHibernate.domain.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Representa un Pedido
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_pedido;
    private String fecha;
    private String total;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private List<Item> items;


    @Override
    public String toString() {
        return "Pedido{" +
                "id_pedido=" + id_pedido +
                ", fecha='" + fecha + '\'' +
                ", usuario=" + usuario.getNombreusuario() +
                ", total='" + total + '\'' +
                ", items=" + items +
                '}';
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Pedido pedido)) return false;
        return Objects.equals( id_pedido , pedido.id_pedido ) && Objects.equals( fecha , pedido.fecha ) && Objects.equals( total , pedido.total ) && Objects.equals( usuario , pedido.usuario );
    }

    @Override
    public int hashCode( ) {
        return Objects.hash( id_pedido );
    }
}
