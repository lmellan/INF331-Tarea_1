# Tarea_1-INF331

## Descripción

Este proyecto es una aplicación de software desarrollada por un equipo de 2 personas. El objetivo es crear una aplicación para gestionar el inventario de productos en un negocio. El código fuente está disponible bajo la **MIT License**, lo que permite su uso, modificación y distribución de manera abierta.

## Wiki

Puede acceder a la Wiki del proyecto mediante el siguiente [enlace](https://github.com/lmellan/Tarea_1-INF331/wiki).  

## Instalación

Para instalar y ejecutar esta aplicación en tu entorno local, sigue estos pasos:

### 1. Instalación de la Base de Datos

Este proyecto utiliza **MySQL** como base de datos. Sigue estos pasos para configurarla localmente:

- **Instala MySQL**: Si no lo tienes instalado, descárgalo desde [MySQL Community Server](https://dev.mysql.com/downloads/) e instálalo siguiendo las instrucciones correspondientes.

- **Inicia MySQL**: Asegúrate de que el servicio de MySQL esté corriendo. Puedes iniciarlo con:
  
  ```bash
  sudo service mysql start
  ```
  
- **Crea una base de datos**: Abre MySQL desde la terminal o una herramienta como MySQL Workbench y ejecuta:
  
  ```sql
  CREATE DATABASE gestion_productos;
  ```
  
- **Usa la base de datos**: Ejecuta el siguiente comando para trabajar dentro de la base de datos:
  
  ```sql
  USE gestion_productos;
  ```

### 2. Configurar la Conexión a la Base de Datos

Antes de ejecutar el proyecto, configura la conexión a la base de datos en el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_productos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

Reemplaza `tu_usuario` y `tu_contraseña` con tus credenciales de MySQL.

### 3. Clona el Repositorio

Abre tu terminal o línea de comandos y clona el repositorio:

```bash
git clone https://github.com/lmellan/Tarea_1-INF331.git
```

### 4. Instala las Dependencias

Asegúrate de tener **Maven** instalado. Verifica su instalación ejecutando:

```bash
mvn -v
```

Si no lo tienes, descárgalo desde [aquí](https://maven.apache.org/download.cgi) e instálalo siguiendo las instrucciones.

Luego, instala las dependencias del proyecto con:

```bash
cd Tarea_1-INF331/gestion-productos-backend
mvn clean install
```

Este comando descargará todas las dependencias necesarias y preparará el proyecto para su ejecución.

## Levantar el Proyecto

Para ejecutar la aplicación, usa el siguiente comando:

```bash
mvn spring-boot:run
```

Este comando iniciará la aplicación en la consola. A partir de ahí, podrás interactuar con el sistema según las opciones disponibles en la terminal.

## Licencia

Este proyecto está bajo la **MIT License** - ver el archivo [LICENSE](https://github.com/lmellan/Tarea_1-INF331/blob/main/LICENSE) para más detalles.


