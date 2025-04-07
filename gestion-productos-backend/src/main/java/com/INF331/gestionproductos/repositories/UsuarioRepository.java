package com.INF331.gestionproductos.repositories;

import com.INF331.gestionproductos.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByNombre(String nombre);
}
