package com.INF331.gestionproductos.services;

import com.INF331.gestionproductos.entities.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoServices {
    List<Producto> getAllProductos();

    Optional<Producto> getProductoById(Long id);

    Producto createProducto(Producto producto);

    Producto updateProducto(Long id, Producto producto);

    void deleteProducto(Long id);

    List<Producto> buscarPorNombre(String nombre);

    List<Producto> buscarPorCategoria(String categoria);
}
