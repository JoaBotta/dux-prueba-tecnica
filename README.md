# Joaquin Botta - Prueba Tecnica - Dux Software
Este proyecto es una API REST desarrollada con **Spring Boot**, que permite gestionar equipos de fútbol (crear, buscar, actualizar y eliminar). Se implementa autenticación con JWT, documentación interactiva con Swagger, pruebas unitarias con Mockito, base de datos embebida H2 y Construccion de imagen con Docker.

| Spring Boot | Swagger | JWT | Docker | H2 Database | Mockito |
|-------------|-----|---------|--------|-------------|---------|
| ![Spring Boot](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711340017/Iconos/uhunhonoxlwpkcxm5luh.webp) | ![Swagger](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711340168/Iconos/swagger-icon_iato1w.png) | ![JWT](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711340096/Iconos/jwt-icon_r3jf8x.png) | ![Docker](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711340248/Iconos/Docker-Logo_bmbgwl.png) | ![H2](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711340319/Iconos/h2-database-icon_kymrzm.webp) | ![Mockito](https://res.cloudinary.com/dttgwvnoe/image/upload/v1711395151/mockito-icon_xvj7pi.jpg) |

## Funcionalidades Principales

- 🔐 Autenticación y autorización con JWT
- 📋 Documentación de la API con Swagger UI (`/swagger-ui.html`)
- 📦 Construccion de la imagen con Docker
- 🧪 Pruebas unitarias usando Mockito
- 🗃️ Persistencia en base de datos H2 en memoria
- 🌐 Roles protegidos con Spring Security


## Instrucciones
### Levantar la aplicación con Docker
Antes de generar la imagen de Docker, es necesario compilar y empaquetar la aplicación utilizando Maven. Desde la raíz del proyecto, ejecutá:

```
mvn clean package
```
Con el archivo .jar ya generado, podés construir la imagen Docker ejecutando el siguiente comando:

```
docker build -t api-prueba-tecnica-duxsoftware .
```
### Ejecutar el contenedor
Una vez creada la imagen, lanzá el contenedor con:
```
docker run -p 8080:8080 --name api-rest-container api-prueba-tecnica-duxsoftware
```
Esto iniciará el servicio en tu máquina local y estará disponible en: `http://localhost:8080`.

### Swagger

La aplicación cuenta con una interfaz interactiva para explorar y testear los endpoints expuestos. Podés acceder a la documentación generada automáticamente por Swagger en el siguiente enlace: `http://localhost:8080/swagger-ui/index.html`

1. Dentro del apartado auth-controller, seleccioná el método `POST /auth/login`.
    ```
    {
      "username": "test",
      "password": "12345"
    }
    ```
    Presionar `Execute`, devolvera un token. Ejemplo de un token generado por JWT:
    ```
    {
      "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0IiwiaWF0IjoxNzQ3MDg2MzkwLCJleHAiOjE3NDcwODc4MzB9.X2XNMzR2pMUZZXV_-KIRUNra3egrsml38loMoNGEUSQ"
    }
    
    ```

### Autorizar con el token
2. Pegá el token generado en el campo correspondiente y confirmá.

   <p style="text-align: center;">
    <img src="https://support.terra.bio/hc/article_attachments/7946132615451" alt="authorize" />
   </p>
3. Después de esto puedes probar completamente la API .

### Acceso a la Base de Datos H2

La aplicación utiliza una base de datos en memoria H2 para pruebas y desarrollo. Podés acceder a la consola web de H2 y consultar las tablas siguiendo estos pasos:

1. Abre tu navegador web y ve a `http://localhost:8080/h2-console`.

2. En el campo "JDBC URL", ingresa `jdbc:h2:mem:equiposDeFutbol`.

3. En el campo "User Name", ingresa `sa`.

4. Deja el campo "Password" vacío.

5. Haz clic en "Connect".

Ahora deberías estar conectado a la consola de H2 y listo para realizar tus consultas.