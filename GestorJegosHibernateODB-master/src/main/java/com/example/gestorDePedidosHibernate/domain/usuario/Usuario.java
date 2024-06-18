package com.example.gestorDePedidosHibernate.domain.usuario;

import com.example.gestorDePedidosHibernate.domain.pedido.Pedido;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name="usuario")
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombreusuario;
    private String pass;
    private String email;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos;

    @Override
    public boolean equals( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Usuario usuario)) return false;
        return Objects.equals( id_usuario , usuario.id_usuario ) && Objects.equals( nombreusuario , usuario.nombreusuario ) && Objects.equals( pass , usuario.pass ) && Objects.equals( email , usuario.email );
    }

    @Override
    public int hashCode( ) {
        return Objects.hash( id_usuario , nombreusuario , pass , email );
    }
}
