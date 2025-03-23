package com.INF331.gestionproductos.controllers;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin("*")

public class ProductoController {

    @Autowired
    private ProductoServices productoService;

    @GetMapping
    public List<Producto> listarProductos(){
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> listarProdcutoPorId(@PathVariable Long id){
        Optional<Producto> producto = productoService.getProductoById(id);

        return producto.map(value -> ResponseEntity.ok().body(value)).orElse(null);
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto){
        System.out.println("Producto a guardar: " + producto);  // Agregar para depuración
        Producto productoGuardado = productoService.createProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarTarea(@PathVariable Long id, @RequestBody Producto producto){
        Producto productoUpdate = productoService.updateProducto(id, producto);
        if(productoUpdate != null){
            return ResponseEntity.ok().body(productoUpdate);
        }
        else {
            return ResponseEntity.notFound().build();
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();

    }

}
