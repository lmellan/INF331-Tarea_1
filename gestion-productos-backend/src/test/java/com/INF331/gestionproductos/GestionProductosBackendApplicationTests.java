package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.entities.Usuario;
import com.INF331.gestionproductos.services.ProductoServices;
import com.INF331.gestionproductos.services.UsuarioServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.*;
import java.util.Optional;
import java.util.Scanner;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GestionProductosBackendApplicationTests {

	//se utiliza asserts para verificar si la salida es correcta/True
	@Test
	void iniciarSesion_credencialesCorrectas_deberiaRetornarTrue() {
		//simular las entradas del usuario default
		String input = "usuarioTest\n1234\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		UsuarioServices usuarioMock = mock(UsuarioServices.class);
		ProductoServices productoMock = mock(ProductoServices.class);

		Usuario mockUsuario = new Usuario(1L, "usuarioTest", new BCryptPasswordEncoder().encode("1234"));
		when(usuarioMock.buscarPorNombre("usuarioTest")).thenReturn(Optional.of(mockUsuario));

		GestionProductosBackendApplication app = new GestionProductosBackendApplication();
		ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);
		ReflectionTestUtils.setField(app, "productoServices", productoMock);

		boolean resultado = ReflectionTestUtils.invokeMethod(app, "iniciarSesion", scanner);

		assertTrue(resultado);
	}

	//si la contraseña es incorrecta, el login debiese retornar assert False
	@Test
	public void iniciarSesion_contrasenaIncorrecta_deberiaRetornarFalse() {
		String input = "usuarioTest\nclaveMala\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		UsuarioServices usuarioMock = mock(UsuarioServices.class);
		ProductoServices productoMock = mock(ProductoServices.class);

		Usuario mockUsuario = new Usuario(1L, "usuarioTest", new BCryptPasswordEncoder().encode("1234"));
		when(usuarioMock.buscarPorNombre("usuarioTest")).thenReturn(Optional.of(mockUsuario));

		GestionProductosBackendApplication app = new GestionProductosBackendApplication();
		ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);
		ReflectionTestUtils.setField(app, "productoServices", productoMock);

		boolean resultado = ReflectionTestUtils.invokeMethod(app, "iniciarSesion", scanner);

		assertFalse(resultado);
	}

	//debiese mostrar un mensaje de que el usuario ya existe.
	@Test
	public void registrarse_usuarioYaExiste_deberiaMostrarMensajeYSalir() {
		String input = "admin\n1234\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		UsuarioServices usuarioMock = mock(UsuarioServices.class);
		ProductoServices productoMock = mock(ProductoServices.class);

		when(usuarioMock.buscarPorNombre("admin")).thenReturn(Optional.of(new Usuario()));

		GestionProductosBackendApplication app = new GestionProductosBackendApplication();
		ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);
		ReflectionTestUtils.setField(app, "productoServices", productoMock);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ReflectionTestUtils.invokeMethod(app, "registrarse", scanner);

		String salida = outContent.toString();
		assertTrue(salida.contains("ya está en uso"));
	}

		@Test
	public void registrarse_usuarioNuevo_deberiaCrearUsuario() {
		String input = "nuevoUsuario\nclaveSegura\n";
		Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

		UsuarioServices usuarioMock = mock(UsuarioServices.class);
		ProductoServices productoMock = mock(ProductoServices.class);

		when(usuarioMock.buscarPorNombre("nuevoUsuario")).thenReturn(Optional.empty());

		GestionProductosBackendApplication app = new GestionProductosBackendApplication();
		ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);
		ReflectionTestUtils.setField(app, "productoServices", productoMock);

		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));

		ReflectionTestUtils.invokeMethod(app, "registrarse", scanner);

		verify(usuarioMock, times(1)).createUsuario(any(Usuario.class));

		String salida = outContent.toString();
		assertTrue(salida.contains("Usuario registrado con éxito."));
	}


	@Test
void buscarProductos_porNombreExistente_deberiaMostrarProducto() {
    String input = "1\nPan\n"; // 1 = buscar por nombre
    Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));

    ProductoServices productoMock = mock(ProductoServices.class);
    UsuarioServices usuarioMock = mock(UsuarioServices.class);

    when(productoMock.buscarPorNombre("Pan")).thenReturn(List.of(
            new Producto(1L, "Pan", "desc", 5L, 100L, "comida")
    ));

    GestionProductosBackendApplication app = new GestionProductosBackendApplication();
    ReflectionTestUtils.setField(app, "productoServices", productoMock);
    ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    ReflectionTestUtils.invokeMethod(app, "buscarProductos", scanner);

    assertTrue(out.toString().contains("Pan"));
}

@Test
void verTodosLosProductos_sinProductos_deberiaMostrarMensaje() {
    ProductoServices productoMock = mock(ProductoServices.class);
    UsuarioServices usuarioMock = mock(UsuarioServices.class);

    when(productoMock.getAllProductos()).thenReturn(List.of());

    GestionProductosBackendApplication app = new GestionProductosBackendApplication();
    ReflectionTestUtils.setField(app, "productoServices", productoMock);
    ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    ReflectionTestUtils.invokeMethod(app, "verTodosLosProductos");

    assertTrue(out.toString().contains("No hay productos en el inventario."));
}

@Test
void generarReportes_deberiaMostrarTotalesYAgotados() {
    ProductoServices productoMock = mock(ProductoServices.class);
    UsuarioServices usuarioMock = mock(UsuarioServices.class);

    List<Producto> productos = List.of(
        new Producto(1L, "Arroz", "desc", 10L, 200L, "comida"),
        new Producto(2L, "Leche", "desc", 0L, 500L, "bebida")
    );

    when(productoMock.getAllProductos()).thenReturn(productos);

    GestionProductosBackendApplication app = new GestionProductosBackendApplication();
    ReflectionTestUtils.setField(app, "productoServices", productoMock);
    ReflectionTestUtils.setField(app, "usuarioServices", usuarioMock);

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    ReflectionTestUtils.invokeMethod(app, "generarReportes");

    String salida = out.toString();
    assertTrue(salida.contains("Total de productos en inventario: 2"));
    assertTrue(salida.contains("Valor total del inventario: $2000"));
    assertTrue(salida.contains("Leche"));
}


}
