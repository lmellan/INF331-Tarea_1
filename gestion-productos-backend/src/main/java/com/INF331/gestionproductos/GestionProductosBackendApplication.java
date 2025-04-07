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
import java.text.Normalizer;

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
        logger.info("Inicio de la aplicación");
        while (true) {
            System.out.println("\n===== BIENVENIDO =====\n");
            System.out.println("1. Crear cuenta");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Salir");
            System.out.print("\nSeleccione una opcion: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            logger.info("Usuario seleccionó la opción: {}", opcion);

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
                    System.out.println("Saliendo del sistema...\n");
                    logger.info("Usuario ha salido del sistema");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Opción no válida, intente de nuevo.");
                    logger.warn("Usuario ingresó una opción inválida: {}", opcion);
            }
        }
    }

    private boolean iniciarSesion(Scanner scanner) {

        logger.info("Intento de inicio de sesion");
        System.out.println("\n======= LOGIN ========\n");

        try {
            System.out.print("Nombre de usuario: ");
            //String nombre = scanner.nextLine().trim();
            String nombre = normalizarTexto(scanner.nextLine());

            if (nombre.isEmpty()) {
                System.out.println("\n");
                logger.warn("Intento de inicio de sesion fallido: Nombre de usuario vacio.");
                return false;
            }

            System.out.print("Contraseña: ");
            String contraseña = scanner.nextLine();

            if (contraseña.isEmpty()) {
                System.out.println("\n");
                logger.warn("Intento de inicio de sesion fallido: Contraseña vacia.");
                return false;
            }

            Usuario usuario = usuarioServices.buscarPorNombre(nombre).orElse(null);

            if (usuario != null && passwordEncoder.matches(contraseña, usuario.getContraseña())) {
                logger.info("Inicio de sesion exitoso para el usuario: {}", nombre);
                System.out.println("\nBienvenido, " + nombre + "!");
                return true;
            } else {

                System.out.println("\n");
                logger.warn("Intento de inicio de sesión fallido para el usuario: {}", nombre);
                System.out.println("\nUsuario o contraseña incorrectos. Intente de nuevo.\n");
                return false;
            }
        } catch (Exception e) {
            logger.error("Error durante el inicio de sesion: {}", e.getMessage(), e);
            System.out.println("\nPor favor, intente nuevamente.");

            return false;
        }
    }

    private void registrarse(Scanner scanner) {
        logger.info("Inicio del proceso de registro");

        System.out.println("\n===== REGISTRO =====\n");

        try {
            System.out.print("Ingrese su nombre de usuario: ");
            //String nombre = scanner.nextLine().trim();
            String nombre = normalizarTexto(scanner.nextLine());

            if (nombre.isEmpty()) {
                logger.warn("Intento de registro fallido: Nombre de usuario vacio.");
                return;
            }

            if (usuarioServices.buscarPorNombre(nombre).isPresent()) {
                System.out.println("\n");
                logger.warn("Intento de registro fallido: El nombre de usuario '{}' ya esta en uso.", nombre);
                return;
            }

            System.out.print("Ingrese su contraseña: ");
            String contraseña = scanner.nextLine();


            if (contraseña.isEmpty()) {
                System.out.println("La contraseña no puede estar vacía.");
                logger.warn("Intento de registro fallido: Contraseña vacía.");
                return;
            }

            String contraseñaCifrada = passwordEncoder.encode(contraseña);

            Usuario nuevoUsuario = new Usuario(null, nombre, contraseñaCifrada);
            usuarioServices.createUsuario(nuevoUsuario);

            System.out.println("\nCuenta creada exitosamente!");
            logger.info("Usuario '{}' registrado con éxito.", nombre);

        } catch (Exception e) {
            logger.error("Error durante el registro de usuario: {}", e.getMessage(), e);
            System.out.println("\nPor favor, intente nuevamente.");
        }
    }



    private void mostrarMenuProductos(Scanner scanner) {
        logger.info("Mostrando menú de productos");
        while (true) {
            System.out.println("\n===== MENÚ PRINCIPAL =====\n");
            System.out.println("1. Ver todos los productos");
            System.out.println("2. Agregar un nuevo producto");
            System.out.println("3. Actualizar un producto");
            System.out.println("4. Eliminar un producto");
            System.out.println("5. Buscar/filtrar productos");
            System.out.println("6. Ver reportes");
            System.out.println("7. Volver");
            System.out.print("\nSeleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            logger.info("Usuario seleccionó la opción: {}", opcion);
            
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
                    logger.info("Usuario regresando al inicio");
                    System.out.println("Regresando al inicio...");
                    mostrarMenuInicio(scanner);
                    return;
                default:
                    logger.warn("Opción ingresada inválida: {}", opcion);
                    System.out.println("Por favor, intente de nuevo.");
            }
        }
    }


    private void verTodosLosProductos() {
        logger.info("Solicitando lista de todos los productos.");

        try {
            List<Producto> productos = productoServices.getAllProductos();
            if (productos.isEmpty()) {
                logger.warn("No hay productos en el inventario.");
            } else {
                System.out.println("Lista de productos en el inventario:\n");
                productos.forEach(this::imprimirProducto);
                logger.info("Se listaron {} productos.", productos.size());
            }
        } catch (Exception e) {
            logger.error("Error al obtener la lista de productos", e);
        }
    }


    private void agregarNuevoProducto(Scanner scanner) {
        logger.info("Inicio del proceso para agregar un nuevo producto");

        try {
            System.out.println("Ingrese el nombre del producto:");
            //String nombre = scanner.nextLine();
            String nombre = normalizarTexto(scanner.nextLine());
            System.out.println("Ingrese la descripción del producto:");
            String descripcion = scanner.nextLine();
            System.out.println("Ingrese la cantidad:");
            Long cantidad = scanner.nextLong();
            System.out.println("Ingrese el precio:");
            Long precio = scanner.nextLong();
            scanner.nextLine();
            System.out.println("Ingrese la categoría del producto:");
            //String categoria = scanner.nextLine();
            String categoria = normalizarTexto(scanner.nextLine());

            Producto nuevoProducto = new Producto(null, nombre, descripcion, cantidad, precio, categoria);
            Producto productoGuardado = productoServices.createProducto(nuevoProducto);

            if (productoGuardado == null) {
                logger.error("No se pudo crear el producto: Nombre={}, Categoría={}", nombre, categoria);
                System.out.println("\nPor favor, intente nuevamente.\n");
                return;
            }

            logger.info("Producto agregado exitosamente: ID={}", productoGuardado.getId());
            System.out.println("\nProducto agregado exitosamente!\n");
            imprimirProducto(productoGuardado);

        } catch (Exception e) {
            logger.error("Error al agregar un nuevo producto", e);
            System.out.println("\nPor favor, intente nuevamente.\n");
        }

    }


    private void actualizarProducto(Scanner scanner) {
        logger.info("Inicio de actualizacion de producto.");

        try {
            System.out.println("Ingrese el ID del producto a actualizar:");
            Long idActualizar = scanner.nextLong();
            scanner.nextLine();
            Optional<Producto> productoOpt = productoServices.getProductoById(idActualizar);

            
            if (productoOpt.isEmpty()) {
                System.out.println("\n");
                logger.warn("Producto con ID {} no encontrado.", idActualizar);
                return;
            }

            Producto productoExistente = productoOpt.get();

            System.out.println("Ingrese el nuevo nombre del producto (. para mantener igual):");
            String nuevoNombre = scanner.nextLine();
            if (nuevoNombre.equals(".")) nuevoNombre = productoExistente.getNombre();
            else nuevoNombre = normalizarTexto(nuevoNombre);

            System.out.println("Ingrese la nueva descripción del producto (. para mantener igual):");
            String nuevaDescripcion = scanner.nextLine();
            if (nuevaDescripcion.equals(".")) nuevaDescripcion = productoExistente.getDescripcion();

            System.out.println("Ingrese la nueva cantidad (. para mantener igual):");
            String cantidadInput = scanner.nextLine();
            Long nuevaCantidad = cantidadInput.equals(".") || cantidadInput.isEmpty() ? productoExistente.getCantidad()
                    : Long.parseLong(cantidadInput);

            System.out.println("Ingrese el nuevo precio (. para mantener igual):");
            String precioInput = scanner.nextLine();
            Long nuevoPrecio = precioInput.equals(".") || precioInput.isEmpty() ? productoExistente.getPrecio()
                    : Long.parseLong(precioInput);

            System.out.println("Ingrese la nueva categoría (. para mantener igual):");
            String nuevaCategoria = scanner.nextLine();
            if (nuevaCategoria.equals(".")) nuevaCategoria = productoExistente.getCategoria();
            else nuevaCategoria = normalizarTexto(nuevaCategoria);

            Producto productoActualizar = new Producto(
                    idActualizar, 
                    nuevoNombre, 
                    nuevaDescripcion, 
                    nuevaCantidad, 
                    nuevoPrecio, 
                    nuevaCategoria);
            
            Producto productoActualizado = productoServices.updateProducto(idActualizar, productoActualizar);

            if (productoActualizado == null) {
                logger.error("No se pudo actualizar el producto con ID {}.", idActualizar);
                return;
            }

            logger.info("Producto actualizado exitosamente: ID={}", idActualizar);
            imprimirProducto(productoActualizado);
        } catch (Exception e) {
            logger.error("Error al actualizar el producto", e);
        }

    }


    private void eliminarProducto(Scanner scanner) {
        logger.info("Inicio del proceso de eliminacion de producto.");

        try {
            System.out.println("Ingrese el ID del producto a eliminar:");
            Long idEliminar = scanner.nextLong();
            scanner.nextLine();

            Optional<Producto> productoOptEliminar = productoServices.getProductoById(idEliminar);
            if (productoOptEliminar.isEmpty()) {
                logger.warn("Intento de eliminacion de producto no encontrado. ID={}", idEliminar);
                return;
            }

            Producto productoAEliminar = productoOptEliminar.get();
            System.out.println("\nProducto encontrado:");
            imprimirProducto(productoAEliminar);

            System.out.println("\n¿Estás seguro que deseas eliminar este producto? (S/N)");
            String confirmacion = scanner.nextLine();

            if (confirmacion.equalsIgnoreCase("S")) {
                productoServices.deleteProducto(idEliminar);
                logger.info("Producto eliminado con exito: ID={}", idEliminar);
                System.out.println("\nProducto eliminado con exito: ID="+ idEliminar);
            } else {
                logger.info("Operacion de eliminacion cancelada para el producto con ID={}", idEliminar);
                System.out.println("\nOperacion de eliminacion cancelada para el producto con ID="+ idEliminar);
            }
        } catch (Exception e) {
            logger.error("Error al eliminar el producto", e);
        }
    }
    

	private void buscarProductos(Scanner scanner) {
        logger.info("Inicio del proceso de búsqueda de productos.");

        int tipoBusqueda = -1;
        while (tipoBusqueda < 1 || tipoBusqueda > 3) {
            System.out.println("\nBuscar por:\n");
            System.out.println("1. Nombre");
            System.out.println("2. Categoría");
            System.out.println("3. Volver");
            System.out.print("\nSeleccione una opcion: ");

            try {
                tipoBusqueda = scanner.nextInt();
                scanner.nextLine();
                if (tipoBusqueda < 1 || tipoBusqueda > 3) {
                    logger.warn("Entrada inválida en búsqueda de productos: {}", tipoBusqueda);
                    System.out.println("\nPor favor ingrese 1, 2 o 3.\n");
                }
            } catch (InputMismatchException e) {
                logger.error("Error de entrada en la búsqueda de productos", e);
                System.out.println("\nPor favor ingrese un número.\n");
                scanner.next();
            }
        }

        if (tipoBusqueda == 3) {
            logger.info("Búsqueda cancelada por el usuario.");
            return;
        }

        try {
            if (tipoBusqueda == 1) {
                System.out.print("\nIngrese el nombre del producto: ");
                String nombreBusqueda = scanner.nextLine();
                List<Producto> productosPorNombre = productoServices.buscarPorNombre(nombreBusqueda);
                if (productosPorNombre.isEmpty()) {
                    logger.info("No se encontraron productos con el nombre: {}", nombreBusqueda);
                    System.out.print("\nNo se encontraron productos con el nombre: "+ nombreBusqueda+"\n");
                } else {
                    logger.info("{} productos encontrados con el nombre: {}", productosPorNombre.size(), nombreBusqueda);
                    System.out.print("\nProductos encontrados: \n");
                    productosPorNombre.forEach(this::imprimirProducto);
                }
            } else if (tipoBusqueda == 2) {
                System.out.print("\nIngrese la categoría: ");
                String categoriaBusqueda = scanner.nextLine();
                List<Producto> productosPorCategoria = productoServices.buscarPorCategoria(categoriaBusqueda);
                if (productosPorCategoria.isEmpty()) {
                    logger.info("No se encontraron productos en la categoría: {}", categoriaBusqueda);
                    System.out.print("\nNo se encontraron productos en la categoría: "+ categoriaBusqueda);
                } else {
                    logger.info("{} productos encontrados en la categoría: {}", productosPorCategoria.size(), categoriaBusqueda);
                    System.out.print("\nProductos encontrados: \n");
                    productosPorCategoria.forEach(this::imprimirProducto);
                }
            }
        } catch (Exception e) {
            logger.error("Error durante la búsqueda de productos", e);
        }
    }



    private void generarReportes() {
        logger.info("Generando reportes de inventario.");

        try {
            List<Producto> todos = productoServices.getAllProductos();
            // productos totales
            logger.info("Se recuperaron {} productos del inventario.", todos.size());
            System.out.println("Total de productos del inventario: "+ todos.size());

            // valor total del inventario
            Long valorTotal = todos.stream()
                    .mapToLong(p -> p.getCantidad() * p.getPrecio())
                    .sum();
            logger.info("Valor total del inventario calculado: ${}", valorTotal);
            System.out.println("Valor total del inventario calculado: $"+ valorTotal);

            // productos que están agotados
            boolean algunoAgotado = false;
            List<Producto> productosAgotados = todos.stream()
                .filter(p -> p.getCantidad() == 0)
                .toList();

            if (productosAgotados.isEmpty()) {
                logger.info("No hay productos agotados en el inventario.");
                System.out.println("No hay productos agotados en el inventario.\n");
            } else {
                logger.info("Se encontraron productos agotados en el inventario.");
                System.out.println("Productos agotados: \n");
                productosAgotados.forEach(p -> 
                    System.out.println("· Nombre: "+ p.getNombre() + ", ID: " + p.getId()+"\n")
                );
            }

            logger.info("Reporte de inventario generado con exito.");
        } catch (Exception e) {
            logger.error("Error al generar reportes de inventario", e);
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

    public static String normalizarTexto(String input) {
        String sinAcentos = Normalizer.normalize(input, Normalizer.Form.NFD)
                                      .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return sinAcentos.toLowerCase();
    }
}










