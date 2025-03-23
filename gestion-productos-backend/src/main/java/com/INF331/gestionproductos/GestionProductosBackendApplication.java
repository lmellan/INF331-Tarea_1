package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GestionProductosBackendApplication implements CommandLineRunner {


	@Autowired
	private ProductoServices productoServices;

	public static void main(String[] args) {
		SpringApplication.run(GestionProductosBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("\nSeleccione una opción:");
			System.out.println("1. Ver todos los productos");
			System.out.println("2. Agregar un nuevo producto");
			System.out.println("3. Actualizar un producto");
			System.out.println("4. Eliminar un producto");
			System.out.println("5. Salir");

			int opcion = scanner.nextInt();
			scanner.nextLine();
			switch (opcion) {
				case 1: // Listar los productos
					List<Producto> productos = productoServices.getAllProductos();
					if (productos.isEmpty()) {
						System.out.println("No hay productos en el inventario.");
					} else {
						System.out.println("\nLista de productos en el inventario:");
						System.out.println("---------------------------------------------------");
						for (Producto producto : productos) {
							System.out.println("ID: " + producto.getId());
							System.out.println("Nombre: " + producto.getNombre());
							System.out.println("Descripción: " + producto.getDescripcion());
							System.out.println("Cantidad: " + producto.getCantidad());
							System.out.println("Precio: " + producto.getPrecio());
							System.out.println("Categoría: " + producto.getCategoria());
							System.out.println("---------------------------------------------------");
						}
					}
					break;

				case 2: // Crear un producto
						System.out.println("Ingrese el nombre del producto:");
						String nombre = scanner.nextLine();
						System.out.println("Ingrese la descripción del producto:");
						String descripcion = scanner.nextLine();
						System.out.println("Ingrese la cantidad:");
						Long cantidad = scanner.nextLong();
						System.out.println("Ingrese el precio:");
						Long precio = scanner.nextLong();
						scanner.nextLine();
						System.out.println("Ingrese la categoría del producto:");
						String categoria = scanner.nextLine();

						Producto nuevoProducto = new Producto(null, nombre, descripcion, cantidad, precio, categoria);
						Producto productoGuardado = productoServices.createProducto(nuevoProducto);
						
						System.out.println("\nProducto creado exitosamente:");
						System.out.println("---------------------------------------------------");
						System.out.println("ID: " + productoGuardado.getId());
						System.out.println("Nombre: " + productoGuardado.getNombre());
						System.out.println("Descripción: " + productoGuardado.getDescripcion());
						System.out.println("Cantidad: " + productoGuardado.getCantidad());
						System.out.println("Precio: " + productoGuardado.getPrecio());
						System.out.println("Categoría: " + productoGuardado.getCategoria());
						System.out.println("---------------------------------------------------");
    				break;

				case 3: // Actualizar un Producto
					System.out.println("Ingrese el ID del producto a actualizar:");
					Long idActualizar = scanner.nextLong();
					scanner.nextLine(); 
					System.out.println("Ingrese el nuevo nombre del producto:");
					String nuevoNombre = scanner.nextLine();
					System.out.println("Ingrese la nueva descripción del producto:");
					String nuevaDescripcion = scanner.nextLine();
					System.out.println("Ingrese la nueva cantidad:");
					Long nuevaCantidad = scanner.nextLong();
					System.out.println("Ingrese el nuevo precio:");
					Long nuevoPrecio = scanner.nextLong();
					scanner.nextLine();
					System.out.println("Ingrese la nueva categoría:");
					String nuevaCategoria = scanner.nextLine();

					Producto productoActualizar = new Producto(idActualizar, nuevoNombre, nuevaDescripcion, nuevaCantidad, nuevoPrecio, nuevaCategoria);
					Producto productoActualizado = productoServices.updateProducto(idActualizar, productoActualizar);
					if (productoActualizado != null) {
						System.out.println("Producto actualizado: " + productoActualizado);
					} else {
						System.out.println("Producto no encontrado para actualizar.");
					}
					break;

				case 4: // Eliminar Producto

					System.out.println("Ingrese el ID del producto a eliminar:");
					Long idEliminar = scanner.nextLong();
					productoServices.deleteProducto(idEliminar);
					System.out.println("Producto eliminado con ID: " + idEliminar);
					break;

				case 5:

					System.out.println("Saliendo del sistema...");
					scanner.close();
					return;

				default:
					System.out.println("Opción no válida, intente de nuevo.");
			}
		}
	}
}