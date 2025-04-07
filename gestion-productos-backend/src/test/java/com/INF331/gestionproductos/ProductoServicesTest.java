package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.repositories.ProductoRepository;
import com.INF331.gestionproductos.services.implementation.ProductoServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductoServicesTest {

    private ProductoRepository productoRepository;
    private ProductoServicesImpl productoServices;

    @BeforeEach
    void setup() {
        productoRepository = mock(ProductoRepository.class);
        productoServices = new ProductoServicesImpl();
        productoServices.setProductoRepository(productoRepository);
    }

    // éxito: crear un producto exitosamente
    @Test
    void crearProducto_validInput_deberiaGuardarYRetornarProducto() {
        Producto producto = new Producto(null, "Producto Test", "Descripción", 10L, 1000L, "Categoría");
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoServices.createProducto(producto);

        assertNotNull(resultado);
        assertEquals("Producto Test", resultado.getNombre());
    }

    // fracaso: no se guarda el producto correctamente (el repositorio retorna null)
    @Test
    void crearProducto_validInput_deberiaRetornarNullSiFallaGuardado() {
        Producto producto = new Producto(null, "Producto Test", "Descripción", 10L, 1000L, "Categoría");
        when(productoRepository.save(producto)).thenReturn(null);

        Producto resultado = productoServices.createProducto(producto);

        assertNull(resultado);
    }

    // éxito: obtener producto por ID existente
    @Test
    void getProductoById_validId_deberiaRetornarProductoSiExiste() {
        Producto producto = new Producto(1L, "Test", "desc", 10L, 500L, "comida");
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> resultado = productoServices.getProductoById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    // fracaso: no se encuentra el producto porque el ID no existe en la base de
    // datos
    @Test
    void getProductoById_wrongId_deberiaRetornarEmptySiNoExiste() {
        when(productoRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Producto> resultado = productoServices.getProductoById(99L);

        assertFalse(resultado.isPresent());
    }

    // éxito: obtener todos los productos
    @Test
    void getAllProductos_conProductos_deberiaRetornarListaDeProductos() {
        List<Producto> mockProductos = Arrays.asList(
                new Producto(1L, "Producto A", "desc", 5L, 100L, "comida"),
                new Producto(2L, "Producto B", "desc", 3L, 200L, "comida"));

        when(productoRepository.findAll()).thenReturn(mockProductos);

        List<Producto> productos = productoServices.getAllProductos();

        assertEquals(2, productos.size());
    }

    // esperado: no se retorna ningún producto porque la lista está vacía
    @Test
    void getAllProductos_sinProductos_deberiaRetornarListaVaciaSiNoHayProductos() {
        when(productoRepository.findAll()).thenReturn(Arrays.asList());

        List<Producto> productos = productoServices.getAllProductos();

        assertTrue(productos.isEmpty());
    }

    // éxito: eliminar un producto existente
    @Test
    void deleteProducto_validId_deberiaLlamarDeleteById() {
        Long id = 1L;

        productoServices.deleteProducto(id);

        verify(productoRepository, times(1)).deleteById(id);
    }

    // fracaso: intento de actualizar producto inexistente porque no existe el ID
    @Test
    void updateProducto_wrongId_deberiaRetornarNullSiProductoNoExiste() {
        Long id = 99L;
        Producto producto = new Producto(id, "Nuevo", "desc", 1L, 200L, "cat");

        when(productoRepository.existsById(id)).thenReturn(false);

        Producto resultado = productoServices.updateProducto(id, producto);

        assertNull(resultado);
    }

    // éxito: búsqueda por nombre con coincidencias
    @Test
    void buscarPorNombre_validName_deberiaRetornarProductosCoincidentes() {
        List<Producto> mockProductos = Arrays.asList(
                new Producto(1L, "Pan", "desc", 3L, 500L, "comida"));

        when(productoRepository.findByNombreContainingIgnoreCase("pan"))
                .thenReturn(mockProductos);

        List<Producto> resultado = productoServices.buscarPorNombre("pan");

        assertEquals(1, resultado.size());
    }

    // fracaso: no hay coincidencias con el nombre buscado
    @Test
    void buscarPorNombre_invalidName_deberiaRetornarListaVaciaSiNoHayCoincidencias() {
        when(productoRepository.findByNombreContainingIgnoreCase("xyz"))
                .thenReturn(Arrays.asList());

        List<Producto> resultado = productoServices.buscarPorNombre("xyz");

        assertTrue(resultado.isEmpty());
    }

    // éxito: búsqueda por categoría con coincidencias
    @Test
    void buscarPorCategoria_validCategory_deberiaRetornarProductosCoincidentes() {
        List<Producto> mockProductos = Arrays.asList(
                new Producto(1L, "Cereal", "desc", 5L, 800L, "desayuno"));

        when(productoRepository.findByCategoriaContainingIgnoreCase("desayuno"))
                .thenReturn(mockProductos);

        List<Producto> resultado = productoServices.buscarPorCategoria("desayuno");

        assertEquals(1, resultado.size());
    }

    // fracaso: no hay coincidencias porque ninguna categoría coincide
    @Test
    void buscarPorCategoria_invalidCategory_deberiaRetornarListaVaciaSiNoHayCoincidencias() {
        when(productoRepository.findByCategoriaContainingIgnoreCase("xyz"))
                .thenReturn(Arrays.asList());

        List<Producto> resultado = productoServices.buscarPorCategoria("xyz");

        assertTrue(resultado.isEmpty());
    }
}
