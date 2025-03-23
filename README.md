# Tarea_1-INF331


## Descripción

Este proyecto es una aplicación de software desarrollada por un equipo de 2 personas. El objetivo es crear una aplicación para gestionar el inventario de productos en un negocio. El código fuente está disponible bajo la **MIT License**, lo que permite su uso, modificación y distribución de manera abierta.

## Wiki

Puede acceder a la Wiki del proyecto mediante el siguiente [enlace](https://github.com/lmellan/Tarea_1-INF331/wiki).  

## Instalación

Para instalar y ejecutar esta aplicación en tu entorno local, sigue estos pasos:

1. **Clona el repositorio**:

   Abre tu terminal o línea de comandos y clona el repositorio:

   ```bash
   git clone https://github.com/lmellan/Tarea_1-INF331.git
   ```

2. **Instala las dependencias**:

   Asegúrate de tener **Maven** instalado en tu máquina. Puedes verificar si tienes Maven ejecutando:

   ```bash
   mvn -v
   ```

   Si no lo tienes, puedes descargarlo desde [aquí](https://maven.apache.org/download.cgi) e instalarlo siguiendo las instrucciones correspondientes.

   Luego, instala las dependencias del proyecto con el siguiente comando:

   ```bash
   cd Tarea_1-INF331\gestion-productos-backend
   mvn clean install
   ```
   Este comando descargará todas las dependencias necesarias y preparará el proyecto para ser ejecutado.

3. **Instalación de la Base de Datos**:

   Este proyecto utiliza **MySQL** como base de datos. Sigue estos pasos para configurar tu base de datos localmente:

   - **Instala MySQL**: Si no tienes MySQL instalado, puedes descargarlo e instalarlo desde el sitio oficial [MySQL Community Server](https://dev.mysql.com/downloads/).

   - **Crea una base de datos**: Una vez que MySQL esté instalado, accede a MySQL desde la terminal o la herramienta de administración que prefieras (como MySQL Workbench). Luego, ejecuta el siguiente comando para crear una base de datos para la aplicación:

     ```sql
     CREATE DATABASE gestion_productos;
     ```

   - **Usa la base de datos**: Asegúrate de estar trabajando dentro de la base de datos que acabas de crear ejecutando el siguiente comando:

     ```sql
     USE gestion_productos;
     ```

   - **Configura la conexión a la base de datos**: En el archivo `src/main/resources/application.properties`, actualiza los datos de la base de datos:

     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/gestion_productos
     spring.datasource.username=tu_usuario
     spring.datasource.password=tu_contraseña
     ```

     Reemplaza `tu_usuario` y `tu_contraseña` con las credenciales que usas para conectarte a MySQL.
     
   - **Inicia la base de datos**: Asegúrate de que tu servidor MySQL esté corriendo. Puedes iniciarlo usando el siguiente comando:

     ```bash
     sudo service mysql start
     ```

     o, si usas un sistema operativo diferente, verifica el método correspondiente.



## Levantar el Proyecto

Para levantar el proyecto, ejecuta el siguiente comando:

```bash
mvn spring-boot:run
```

Este comando ejecutará la aplicación en la consola. A partir de ahí, podrás interactuar con el sistema a través de las opciones que aparecerán en la terminal.

   
## Licencia

Este proyecto está bajo la **MIT License** - ver el archivo [LICENSE](https://github.com/lmellan/Tarea_1-INF331/blob/main/LICENSE) para más detalles.
