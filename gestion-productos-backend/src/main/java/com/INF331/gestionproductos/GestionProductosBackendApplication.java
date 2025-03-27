package com.INF331.gestionproductos;

import com.INF331.gestionproductos.entities.Producto;
import com.INF331.gestionproductos.services.ProductoServices;
import com.INF331.gestionproductos.entities.Usuario;
import com.INF331.gestionproductos.services.UsuarioServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SpringBootApplication
public class GestionProductosBackendApplication implements CommandLineRunner {

    @Autowired
    private UsuarioServices usuarioServices;

    @Autowired
    private ProductoServices productoServices;

	private static final Logger logger = LogManager.getLogger(GestionProductosBackendApplication.class);

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(GestionProductosBackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        mostrarMenuInicio(scanner);
    }

    private void mostrarMenuInicio(Scanner scanner) {
        while (true) {
			System.out.println("\n===== BIENVENIDO =====\n");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Iniciar sesión");
            System.out.println("3. Salir");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

			System.out.print("\n");

            switch (opcion) {
                case 2:
                    if (iniciarSesion(scanner)) {
                        mostrarMenuProductos(scanner);
                    }
                    break;
                case 1:
                    registrarse(scanner);
                    break;
                case 3:
                    System.out.println("Saliendo del sistema...");
					System.exit(0);  // Finaliza la aplicación y cierra la consola
					return; 
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    private boolean iniciarSesion(Scanner scanner) {
		System.out.println("\n===== LOGIN =====\n");

        System.out.print("Nombre de usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contraseña = scanner.nextLine();

        Optional<Usuario> usuarioOpt = usuarioServices.buscarPorNombre(nombre);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(contraseña, usuarioOpt.get().getContraseña())) {
            System.out.println("\nInicio de sesión exitoso. \nBienvenido " + nombre + "!\n");
            return true;
        } else {
            System.out.println("Usuario o contraseña incorrectos. Intente de nuevo.");
            return false;
        }
    }

    private void registrarse(Scanner scanner) {

		System.out.println("\n===== REGISTRO =====\n");

        System.out.print("Ingrese su nombre de usuario: ");
        String nombre = scanner.nextLine();
		if (usuarioServices.buscarPorNombre(nombre).isPresent()) {
			System.out.println("Error: El nombre de usuario ya está en uso. Intente con otro.");
			return;
		}

        System.out.print("Ingrese su contraseña: ");
        String contraseña = scanner.nextLine();
        String contraseñaCifrada = passwordEncoder.encode(contraseña);

        Usuario nuevoUsuario = new Usuario(null, nombre, contraseñaCifrada);
        usuarioServices.createUsuario(nuevoUsuario);
        System.out.println("Usuario registrado con éxito.");
    }

    private void mostrarMenuProductos(Scanner scanner) {
        while (true) {
			 System.out.println("\n===== MENÚ PRINCIPAL =====\n");
            System.out.println("1. Ver todos los productos");
            System.out.println("2. Agregar un nuevo producto");
            System.out.println("3. Actualizar un producto");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Buscar productos por nombre o categoría");
            System.out.println("6. Ver reportes");
            System.out.println("7. Salir");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

			System.out.print("\n");

            switch (opcion) {
                case 1:
                    verTodosLosProductos();
                    break;
                case 2:
                    agregarNuevoProducto(scanner);
                    break;
                case 3:
                    actualizarProducto(scanner);
                    break;
                case 4:
                    eliminarProducto(scanner);
                    break;
                case 5:
                    buscarProductos(scanner);
                    break;
                case 6:
                    generarReportes();
                    break;
                case 7:
                    System.out.println("Regresando al menú...");
                    mostrarMenuInicio(scanner);
                    break;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
            }
        }
    }

    private void verTodosLosProductos() {
        List<Producto> productos = productoServices.getAllProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
        } else {
            System.out.println("\nLista de productos en el inventario:");
            productos.forEach(this::imprimirProducto);
        }
    }

    private void agregarNuevoProducto(Scanner scanner) {
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
        imprimirProducto(productoGuardado);
    }

    private void actualizarProducto(Scanner scanner) {
        System.out.println("Ingrese el ID del producto a actualizar:");
        Long idActualizar = scanner.nextLong();
        scanner.nextLine();

        Optional<Producto> productoOpt = productoServices.getProductoById(idActualizar);
        if (productoOpt.isEmpty()) {
            System.out.println("Producto no encontrado.");
            return;
        }

        Producto productoExistente = productoOpt.get();

        System.out.println("Ingrese el nuevo nombre del producto (. para dejar igual):");
        String nuevoNombre = scanner.nextLine();
        if (nuevoNombre.equals(".")) nuevoNombre = productoExistente.getNombre();

        System.out.println("Ingrese la nueva descripción del producto (. para dejar igual):");
        String nuevaDescripcion = scanner.nextLine();
        if (nuevaDescripcion.equals(".")) nuevaDescripcion = productoExistente.getDescripcion();

		System.out.println("Ingrese la nueva cantidad (. para dejar igual):");
		String cantidadInput = scanner.nextLine();
		Long nuevaCantidad = cantidadInput.equals(".") || cantidadInput.isEmpty() ? productoExistente.getCantidad()
				: Long.parseLong(cantidadInput);

		System.out.println("Ingrese el nuevo precio (. para dejar igual):");
		String precioInput = scanner.nextLine();
		Long nuevoPrecio = precioInput.equals(".") || precioInput.isEmpty() ? productoExistente.getPrecio()
				: Long.parseLong(precioInput);


        System.out.println("Ingrese la nueva categoría (. para dejar igual):");
        String nuevaCategoria = scanner.nextLine();
        if (nuevaCategoria.equals(".")) nuevaCategoria = productoExistente.getCategoria();

        Producto productoActualizar = new Producto(
			idActualizar, 
			nuevoNombre, 
			nuevaDescripcion, 
			nuevaCantidad, 
			nuevoPrecio, 
			nuevaCategoria);
        Producto productoActualizado = productoServices.updateProducto(idActualizar, productoActualizar);
        System.out.println("Producto actualizado:");
        imprimirProducto(productoActualizado);
    }

    private void eliminarProducto(Scanner scanner) {
        System.out.println("Ingrese el ID del producto a eliminar:");
        Long idEliminar = scanner.nextLong();
        scanner.nextLine();

        Optional<Producto> productoOptEliminar = productoServices.getProductoById(idEliminar);
        if (productoOptEliminar.isEmpty()) {
            System.out.println("Producto no encontrado. No se puede eliminar.");
            return;
        }

        Producto productoAEliminar = productoOptEliminar.get();
        System.out.println("Producto encontrado:");
        imprimirProducto(productoAEliminar);

        System.out.println("¿Estás seguro que deseas eliminar este producto? (S/N)");
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            productoServices.deleteProducto(idEliminar);
            System.out.println("Producto eliminado con ID: " + idEliminar);
        } else {
            System.out.println("Operación cancelada. El producto no fue eliminado.");
        }
    }

	private void buscarProductos(Scanner scanner) {
		int tipoBusqueda = -1;

		while (tipoBusqueda < 1 || tipoBusqueda > 3) {
			System.out.println("\nBuscar por:\n");
			System.out.println("1. Nombre");
			System.out.println("2. Categoría");
			System.out.println("3. Volver");

			try {
				tipoBusqueda = scanner.nextInt(); 
				scanner.nextLine(); 
				if (tipoBusqueda < 1 || tipoBusqueda > 3) {
					System.out.println("Opción inválida, por favor ingrese 1, 2 o 3.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida. Por favor ingrese un número.");
				scanner.next(); 
			}
		}

		if (tipoBusqueda == 3) {
			return;
		}

		if (tipoBusqueda == 1) {
			System.out.print("Ingrese el nombre del producto: ");
			String nombreBusqueda = scanner.nextLine();
			List<Producto> productosPorNombre = productoServices.buscarPorNombre(nombreBusqueda);
			if (productosPorNombre.isEmpty()) {
				System.out.println("No se encontraron productos con ese nombre.");
			} else {
				productosPorNombre.forEach(this::imprimirProducto);
			}
		} else if (tipoBusqueda == 2) {
			System.out.print("Ingrese la categoría: ");
			String categoriaBusqueda = scanner.nextLine();
			List<Producto> productosPorCategoria = productoServices.buscarPorCategoria(categoriaBusqueda);
			if (productosPorCategoria.isEmpty()) {
				System.out.println("No se encontraron productos con esa categoría.");
			} else {
				productosPorCategoria.forEach(this::imprimirProducto);
			}
		}
	}


    private void generarReportes() {
        List<Producto> todos = productoServices.getAllProductos();

        // productos totales
        System.out.println("\nTotal de productos en inventario: " + todos.size());

        // valor total del inventario
        Long valorTotal = todos.stream()
                .mapToLong(p -> p.getCantidad() * p.getPrecio())
                .sum();
        System.out.println("Valor total del inventario: $" + valorTotal);

        // productos que están agotados
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
    }

    private void imprimirProducto(Producto producto) {
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










