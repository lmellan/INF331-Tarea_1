package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Usuario;
import com.INF331.gestionproductos.repositories.UsuarioRepository;
import com.INF331.gestionproductos.services.implementation.UsuarioServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServicesTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioServicesImpl usuarioServices;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioServices = new UsuarioServicesImpl();
        usuarioServices.setUsuarioRepository(usuarioRepository);
    }

    @Test
    void createUsuario_datosValidos_deberiaCrearUsuario() {
        Usuario usuario = new Usuario(null, "admin", "1234");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServices.createUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("admin", resultado.getNombre());
    }

    @Test
    void createUsuario_guardadoFalla_deberiaRetornarNull() {
        Usuario usuario = new Usuario(null, "admin", "1234");
        when(usuarioRepository.save(usuario)).thenReturn(null);

        Usuario resultado = usuarioServices.createUsuario(usuario);

        assertNull(resultado);
    }

    @Test
    void updateUsuario_existente_deberiaActualizarUsuario() {
        Usuario usuario = new Usuario(1L, "admin", "1234");
        when(usuarioRepository.existsById(1L)).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario resultado = usuarioServices.updateUsuario(1L, usuario);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void updateUsuario_idInvalido_deberiaRetornarNull() {
        Usuario usuario = new Usuario(99L, "noExiste", "1234");
        when(usuarioRepository.existsById(99L)).thenReturn(false);

        Usuario resultado = usuarioServices.updateUsuario(99L, usuario);

        assertNull(resultado);
    }

    // @Test
    // void deleteUsuario_valido_deberiaLlamarDeleteById() {
    // Long id = 1L;

    // usuarioServices.deleteUsuario(id);

    // verify(usuarioRepository, times(1)).deleteById(id);
    // }

    // @Test
    // void buscarPorNombre_existente_deberiaRetornarUsuario() {
    // Usuario usuario = new Usuario(1L, "admin", "1234");
    // when(usuarioRepository.findByNombre("admin")).thenReturn(Optional.of(usuario));

    // Optional<Usuario> resultado = usuarioServices.buscarPorNombre("admin");

    // assertTrue(resultado.isPresent());
    // assertEquals("admin", resultado.get().getNombre());
    // }

    @Test
    void buscarPorNombre_noExiste_deberiaRetornarEmpty() {
        when(usuarioRepository.findByNombre("noExiste")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioServices.buscarPorNombre("noExiste");

        assertFalse(resultado.isPresent());
    }
}
