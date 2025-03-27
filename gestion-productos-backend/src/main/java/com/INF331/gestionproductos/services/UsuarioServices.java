package com.INF331.gestionproductos.services;

import com.INF331.gestionproductos.entities.Usuario;

import java.util.Optional;


public interface UsuarioServices {

    Usuario createUsuario(Usuario usuario);

    Usuario updateUsuario(Long id, Usuario usuario);

    void deleteUsuario(Long id);

    Optional<Usuario> buscarPorNombre(String nombre);

    

    
}
