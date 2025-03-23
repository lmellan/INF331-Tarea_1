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
   cd Tarea_1-INF331
   ```

2. **Instala las dependencias**:

   Asegúrate de tener **Maven** instalado en tu máquina. Puedes verificar si tienes Maven ejecutando:

   ```bash
   mvn -v
   ```

   Si no lo tienes, puedes descargarlo desde [aquí](https://maven.apache.org/download.cgi) e instalarlo siguiendo las instrucciones correspondientes.

   Luego, instala las dependencias del proyecto con el siguiente comando:

   ```bash
   mvn clean install
   ```

   Este comando descargará todas las dependencias necesarias y preparará el proyecto para ser ejecutado.

## Levantar el Backend

Para levantar el backend de la aplicación, ejecuta el siguiente comando:

```bash
mvn spring-boot:run
```

Este comando ejecutará el servidor de Spring Boot, y podrás acceder a la aplicación en tu navegador en la URL `http://localhost:8080`.

   
## Licencia

Este proyecto está bajo la **MIT License** - ver el archivo [LICENSE](https://github.com/lmellan/Tarea_1-INF331/blob/main/LICENSE) para más detalles.
