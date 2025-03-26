package com.INF331.gestionproductos.repositories;

import com.INF331.gestionproductos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepositories extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreAndContraseña(String nombre, String contraseña);
}
