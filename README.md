# Gestión de Inventario

## Descripción

Este proyecto corresponde a la Tarea 1 del curso INF331. Se trata de una  aplicación de software desarrollada por un equipo de 2 personas. El objetivo es crear una aplicación para gestionar el inventario de productos en un negocio.

## Wiki

Puede acceder a la Wiki del proyecto mediante el siguiente [enlace](https://github.com/lmellan/Tarea_1-INF331/wiki).  

## Instalación

Para instalar y ejecutar esta aplicación en tu entorno local, sigue estos pasos:

### 1. Instalación de la Base de Datos

Este proyecto utiliza **MySQL** como base de datos. Así se puede configurar localmente:

- **Instala MySQL**: Si no lo tienes instalado, descárgalo desde [MySQL Community Server](https://dev.mysql.com/downloads/) e instálalo siguiendo las instrucciones correspondientes.

- **Inicia MySQL**: Asegúrate de que el servicio de MySQL esté corriendo. Puedes iniciarlo con:

linux:
  ```bash
  sudo service mysql start
  ```
windows:
  ```bash
  mysql -u root -p
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

Si no lo tienes, descárgalo desde [aquí](https://maven.apache.org/download.cgi) e instálalo las respectivas instrucciones.

Luego, instala las dependencias del proyecto con:

```bash
cd Tarea_1-INF331/gestion-productos-backend
mvn clean install -DskipTests -DinteractiveMode=false
```

Este comando descargará todas las dependencias necesarias y preparará el proyecto para su ejecución.

## Levantar el Proyecto

Para ejecutar la aplicación, usa el siguiente comando:

```bash
mvn spring-boot:run
```
Este comando iniciará la aplicación en la consola. A partir de ahí, podrás interactuar con el sistema según las opciones disponibles en la terminal.

## Cómo usar

Una vez que hayas configurado correctamente la base de datos y las dependencias, y hayas iniciado el proyecto, podrás realizar las siguientes acciones:

1. **Menú de inicio**: Cuando se inicie la aplicación, aparecerá el siguiente menú de inicio en la consola:

    ```
    ===== BIENVENIDO =====
    1. Crear cuenta
    2. Iniciar sesión
    3. Salir
    Seleccione una opción: 
    ```

    - **Opción 1: Crear cuenta**: Si aún no tienes una cuenta, selecciona la opción 1 para crear una. El sistema te pedirá un nombre de usuario y una contraseña. Si el nombre de usuario ya está en uso, se te informará y deberás elegir otro.
    
    - **Opción 2: Iniciar sesión**: Si ya tienes una cuenta, selecciona la opción 2 e ingresa tu nombre de usuario y contraseña para iniciar sesión en el sistema.
    
    - **Opción 3: Salir**: Si deseas salir de la aplicación, selecciona esta opción.

2. **Menú de productos**: Una vez que inicies sesión exitosamente, accederás al siguiente menú de productos:

    ```
    ===== MENÚ PRINCIPAL =====
    1. Ver todos los productos
    2. Agregar un nuevo producto
    3. Actualizar un producto
    4. Eliminar un producto
    5. Buscar/filtrar productos
    6. Ver reportes
    7. Volver
    ```

    Desde aquí, podrás realizar diferentes acciones relacionadas con el inventario de productos. Las opciones disponibles son:

    - **Opción 1: Ver todos los productos**: Visualiza todos los productos registrados en el sistema.
    
    - **Opción 2: Agregar un nuevo producto**: Permite agregar un producto al inventario proporcionando su nombre, cantidad, precio y otros detalles.
    
    - **Opción 3: Actualizar un producto**: Permite editar los detalles de un producto existente en el inventario.
    
    - **Opción 4: Eliminar un producto**: Permite eliminar un producto del inventario.
    
    - **Opción 5: Buscar/filtrar productos**: Permite buscar productos en el inventario según ciertos criterios.
    
    - **Opción 6: Ver reportes**: Visualiza un resumen con el total de productos en inventario, el valor total del inventario y los productos agotados.
    
    - **Opción 7: Volver**: Regresa al menú principal.


### Recomendaciones

- **Iniciar sesión**: Asegúrate de tener tu cuenta creada antes de intentar iniciar sesión. Si la contraseña es incorrecta o el nombre de usuario no existe, el sistema te notificará que el inicio de sesión ha fallado.
- **Flujo de trabajo**: Puedes interactuar con el inventario de productos según las opciones del menú. En todo momento puedes regresar al inicio o salir del sistema.


### 5. Ejecución de Pruebas

Este proyecto incluye pruebas unitarias.

**Ejecutar los tests:**
```bash
mvn test
```
**Herramientas utilizadas:**
- *JUnit5* para pruebas unitarias.
- *Mockito* para pruebas con mocks.

## Cómo contribuir

Si deseas contribuir al proyecto, sigue estos pasos utilizando **GitFlow** de para mantener una estructura organizada en el desarrollo:

### Flujo de trabajo con GitFlow 

1. **Haz un Fork del repositorio**: Ve a GitHub y haz un fork del repositorio para tener tu propia copia en tu cuenta.

2. **Clona el repositorio**: Clona tu fork localmente usando el siguiente comando:
   ```bash
   git clone https://github.com/tu_usuario/Tarea_1-INF331.git
   ```
   Luego, entra al directorio del proyecto:
   ```bash
   cd Tarea_1-INF331
   ```

3. **Añade el repositorio original como remoto**: Para poder sincronizar cambios con el repositorio principal, agrégalo como un nuevo remoto:
   ```bash
   git remote add upstream https://github.com/repo-original/Tarea_1-INF331.git
   ```
   Verifica los remotos configurados con:
   ```bash
   git remote -v
   ```

4. **Crea una nueva rama de desarrollo (develop)**:  
   Si el repositorio no tiene una rama `develop`, créala y súbela:
   ```bash
   git checkout -b develop
   git push origin develop
   ```

5. **Mantén tu repositorio actualizado**: Antes de trabajar en una nueva funcionalidad, asegúrate de que tienes la última versión del código:
   ```bash
   git checkout develop
   git pull upstream develop
   ```

6. **Crea una rama para tu funcionalidad**:  
   Si vas a desarrollar una nueva funcionalidad, crea una nueva rama `feature` a partir de `develop`:
   ```bash
   git checkout -b feature/nombre-de-la-funcionalidad develop
   ```

7. **Realiza tus cambios y haz commits**:  
   Edita el código y haz commits siguiendo buenas prácticas:
   ```bash
   git add .
   git commit -m "Descripción clara de la funcionalidad agregada"
   ```

8. **Sube tu rama al repositorio remoto**:
   ```bash
   git push origin feature/nombre-de-la-funcionalidad
   ```

9. **Crea un Pull Request (PR)**:  
   Ve a GitHub y crea un **Pull Request** desde tu rama `feature/nombre-de-la-funcionalidad` hacia la rama `develop` del repositorio principal.

10. **Revisión y fusión**:  
    - Un miembro del equipo revisará tu código.  
    - Si es aprobado, la funcionalidad será fusionada en `develop`.  
    - Si hay cambios solicitados, ajústalos y vuelve a hacer commits en la misma rama.

11. **Eliminar la rama de funcionalidad (opcional)**:  
    Una vez fusionado el PR, elimina la rama localmente y en GitHub:
    ```bash
    git branch -d feature/nombre-de-la-funcionalidad
    git push origin --delete feature/nombre-de-la-funcionalidad
    ```
    
### Reglas para los Pull Requests:
- Cada PR debe estar relacionado con una única funcionalidad o corrección.
- Los tests deben ejecutarse antes de enviar el PR.
- Usa mensajes de commit claros y concisos.
- Todo PR debe ser revisado antes de fusionarse.


## Licencia

Este proyecto está bajo la **MIT License** - ver el archivo [LICENSE](https://github.com/lmellan/Tarea_1-INF331/blob/main/LICENSE) para más detalles.

