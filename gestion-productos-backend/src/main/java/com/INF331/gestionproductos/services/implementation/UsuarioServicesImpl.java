package com.INF331.gestionproductos.services.implementation;

import com.INF331.gestionproductos.entities.Usuario;
import com.INF331.gestionproductos.repositories.UsuarioRepository;
import com.INF331.gestionproductos.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServicesImpl implements UsuarioServices {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsByNombre(usuario.getNombre())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Long id, Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            return usuarioRepository.save(usuario);
        }
        return null;
    }

    @Override
    public void deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Usuario> buscarPorNombre(String categoria) {
        return usuarioRepository.findByNombreContainingIgnoreCase(categoria);
    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

}
