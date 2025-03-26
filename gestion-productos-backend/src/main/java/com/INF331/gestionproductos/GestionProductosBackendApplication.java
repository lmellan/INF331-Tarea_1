package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.repositories.UsuarioRepositories;
import com.INF331.gestionproductos.services.ProductoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Optional;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class GestionProductosBackendApplication implements CommandLineRunner {

	@Autowired
	private ProductoServices productoServices;

	private static final Logger logger = LogManager.getLogger(GestionProductosBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GestionProductosBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1. Ver todos los productos");
			System.out.println("2. Agregar un nuevo producto");
			System.out.println("3. Actualizar un producto");
			System.out.println("4. Eliminar un producto");
			System.out.println("5. Buscar productos por nombre o categoría");
			System.out.println("6. Ver reportes");
			System.out.println("7. Salir");

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
					if (!autenticarUsuario(scanner)) {
						System.out.println("Acceso denegado.");
						break;
					}
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

					if (!autenticarUsuario(scanner)) {
						System.out.println("Acceso denegado.");
						break;
					}

					System.out.println("Ingrese el ID del producto a actualizar:");
					Long idActualizar = scanner.nextLong();
					scanner.nextLine();

					// busca si el producto es existente con optional

					Optional<Producto> productoOpt = productoServices.getProductoById(idActualizar);
					if (productoOpt.isEmpty()) {
						System.out.println("Producto no encontrado.");
						break;
					}

					Producto productoExistente = productoOpt.get();

					System.out.println("Ingrese el nuevo nombre del producto (0 para dejar igual):");
					String nuevoNombre = scanner.nextLine();
					if (nuevoNombre.equals("0"))
						nuevoNombre = productoExistente.getNombre();

					System.out.println("Ingrese la nueva descripción del producto (0 para dejar igual):");
					String nuevaDescripcion = scanner.nextLine();
					if (nuevaDescripcion.equals("0"))
						nuevaDescripcion = productoExistente.getDescripcion();

					System.out.println("Ingrese la nueva cantidad (0 para dejar igual):");
					String cantidadInput = scanner.nextLine();
					Long nuevaCantidad = cantidadInput.equals("0") ? productoExistente.getCantidad()
							: Long.parseLong(cantidadInput);

					System.out.println("Ingrese el nuevo precio (0 para dejar igual):");
					String precioInput = scanner.nextLine();
					Long nuevoPrecio = precioInput.equals("0") ? productoExistente.getPrecio()
							: Long.parseLong(precioInput);

					System.out.println("Ingrese la nueva categoría (0 para dejar igual):");
					String nuevaCategoria = scanner.nextLine();
					if (nuevaCategoria.equals("0"))
						nuevaCategoria = productoExistente.getCategoria();

					Producto productoActualizar = new Producto(
							idActualizar,
							nuevoNombre,
							nuevaDescripcion,
							nuevaCantidad,
							nuevoPrecio,
							nuevaCategoria);

					Producto productoActualizado = productoServices.updateProducto(idActualizar, productoActualizar);
					System.out.println("Producto actualizado: " + productoActualizado);
					break;

				// AHORA EL PRODUCTO SE VALIDA ANTES DE ACTUALIZARLO...
				// System.out.println("Ingrese el nuevo nombre del producto:");
				// String nuevoNombre = scanner.nextLine();
				// System.out.println("Ingrese la nueva descripción del producto:");
				// String nuevaDescripcion = scanner.nextLine();
				// System.out.println("Ingrese la nueva cantidad:");
				// Long nuevaCantidad = scanner.nextLong();
				// System.out.println("Ingrese el nuevo precio:");
				// Long nuevoPrecio = scanner.nextLong();
				// scanner.nextLine();
				// System.out.println("Ingrese la nueva categoría:");
				// String nuevaCategoria = scanner.nextLine();

				// Producto productoActualizar = new Producto(idActualizar, nuevoNombre,
				// nuevaDescripcion,
				// nuevaCantidad, nuevoPrecio, nuevaCategoria);
				// Producto productoActualizado = productoServices.updateProducto(idActualizar,
				// productoActualizar);
				// if (productoActualizado != null) {
				// System.out.println("Producto actualizado: " + productoActualizado);
				// } else {
				// System.out.println("Producto no encontrado para actualizar.");
				// }
				// break;

				case 4: // Eliminar Producto

					if (!autenticarUsuario(scanner)) {
						System.out.println("Acceso denegado.");
						break;
					}

					System.out.println("Ingrese el ID del producto a eliminar:");
					Long idEliminar = scanner.nextLong();
					scanner.nextLine();

					// verificar si el producto existe
					Optional<Producto> productoOptEliminar = productoServices.getProductoById(idEliminar);
					if (productoOptEliminar.isEmpty()) {
						System.out.println("Producto no encontrado. No se puede eliminar.");
						break;
					}

					Producto productoAEliminar = productoOptEliminar.get();
					System.out.println("Producto encontrado:");
					System.out.println("ID: " + productoAEliminar.getId());
					System.out.println("Nombre: " + productoAEliminar.getNombre());
					System.out.println("¿Estás seguro que deseas eliminar este producto? (S/N)");
					String confirmacion = scanner.nextLine();

					if (confirmacion.equalsIgnoreCase("S")) {
						productoServices.deleteProducto(idEliminar);
						System.out.println("Producto eliminado con ID: " + idEliminar);
					} else {
						System.out.println("Operación cancelada. El producto no fue eliminado.");
					}
					break;

				// case 4: // Eliminar Producto

				// System.out.println("Ingrese el ID del producto a eliminar:");
				// Long idEliminar = scanner.nextLong();
				// productoServices.deleteProducto(idEliminar);
				// System.out.println("Producto eliminado con ID: " + idEliminar);
				// break;

				// case 5:

				// System.out.println("Saliendo del sistema...");
				// scanner.close();
				// return;

				// default:
				// System.out.println("Opción no válida, intente de nuevo.");}

				// IMPLEMENTACION CORRECTA FALTA IMPLEMENTAR MÉTODOS
				case 5: // Buscar productos
					System.out.println("Buscar por:");
					System.out.println("1. Nombre");
					System.out.println("2. Categoría");
					int tipoBusqueda = scanner.nextInt();
					scanner.nextLine();

					if (tipoBusqueda == 1) {
						System.out.print("Ingrese el nombre del producto: ");
						String nombreBusqueda = scanner.nextLine();
						List<Producto> productosPorNombre = productoServices.buscarPorNombre(nombreBusqueda);
						if (productosPorNombre.isEmpty()) {
							System.out.println("No se encontraron productos con ese nombre.");
						} else {
							productosPorNombre.forEach(GestionProductosBackendApplication::imprimirProducto);
						}
					} else if (tipoBusqueda == 2) {
						System.out.print("Ingrese la categoría: ");
						String categoriaBusqueda = scanner.nextLine();
						List<Producto> productosPorCategoria = productoServices.buscarPorCategoria(categoriaBusqueda);
						if (productosPorCategoria.isEmpty()) {
							System.out.println("No se encontraron productos con esa categoría.");
						} else {
							productosPorCategoria.forEach(GestionProductosBackendApplication::imprimirProducto);
						}
					} else {
						System.out.println("Opción de búsqueda no válida.");
					}
					break;

				case 6: // Reportes
					List<Producto> todos = productoServices.getAllProductos();

					// productos totales
					System.out.println("Total de productos en inventario: " + todos.size());

					// valor total del inventario
					Long valorTotal = todos.stream()
							.mapToLong(p -> p.getCantidad() * p.getPrecio())
							.sum();
					System.out.println("Valor total del inventario: $" + valorTotal);

					// productos q estan agotados
					System.out.println("Productos agotados (cantidad = 0):");
					boolean algunoAgotado = false;
					for (Producto p : todos) {
						if (p.getCantidad() == 0) {
							algunoAgotado = true;
							System.out.println("- " + p.getNombre() + " (ID: " + p.getId() + ")");
						}
					}
					if (!algunoAgotado) {
						System.out.println("Ningún producto está agotado.");
					}
					break;

				case 7: // Salir

					System.out.println("Saliendo del sistema...");
					scanner.close();
					return;

				default:
					System.out.println("Opción no válida, intente de nuevo.");

			}
		}
	}

	@Autowired
	private UsuarioRepositories usuarioRepository;

	private boolean autenticarUsuario(Scanner scanner) {
		System.out.println("===== LOGIN =====");
		System.out.print("Nombre de usuario: ");
		String username = scanner.nextLine();
		System.out.print("Contraseña: ");
		String password = scanner.nextLine();

		Optional<Usuario> usuario = usuarioRepository.findByNombreAndContraseña(username, password);

		if (usuario.isPresent()) {
			logger.info("Inicio de sesión exitoso para el usuario: " + username);
			return true;
		} else {
			logger.warn("Intento de inicio de sesión fallido para el usuario: " + username);
			return false;
		}
	}

	public static void imprimirProducto(Producto producto) {
		System.out.println("---------------------------------------------------");
		System.out.println("ID: " + producto.getId());
		System.out.println("Nombre: " + producto.getNombre());
		System.out.println("Descripción: " + producto.getDescripcion());
		System.out.println("Cantidad: " + producto.getCantidad());
		System.out.println("Precio: " + producto.getPrecio());
		System.out.println("Categoría: " + producto.getCategoria());
		System.out.println("---------------------------------------------------");
	}

}