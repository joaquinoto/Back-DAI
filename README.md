# 🍳 CocinaApp — Backend

API REST para una aplicación móvil de recetas y clases de cocina. Desarrollada con Spring Boot, implementa autenticación JWT y un sistema de roles para gestionar distintos tipos de usuarios.

## Descripción

CocinaApp es el backend de una plataforma donde los usuarios pueden explorar recetas, subir las propias, inscribirse en clases de cocina presenciales y gestionar su cuenta. El sistema contempla cuatro roles: visitante, usuario registrado, alumno y administrador, con permisos diferenciados para cada uno.

## Tecnologías

- **Java 17**
- **Spring Boot 3**
- **Spring Security** con autenticación JWT stateless
- **Spring Data JPA**
- **MySQL**
- **BCrypt** para encriptación de contraseñas
- **Maven**

## Funcionalidades principales

### Recetas
- Listado de recetas aprobadas, por usuario, por tipo e ingrediente
- Búsqueda por nombre, con/sin ingrediente específico
- Carga de recetas por usuarios registrados (quedan pendientes de aprobación)
- Aprobación y publicación por parte del administrador
- Escalado visual de porciones
- Últimas tres recetas publicadas (acceso público)

### Cursos y Alumnos
- Listado y detalle de cursos disponibles
- Inscripción y baja de cursos
- Registro de asistencias y aprobación
- Historial de asistencia por alumno
- Cuenta corriente y movimientos de pago

### Usuarios y Autenticación
- Registro de usuarios, alumnos y administradores
- Login con generación de token JWT
- Recuperación de contraseña
- Sugerencia de alias y liberación de mail

### Otros módulos
- Ingredientes, unidades y conversiones
- Tipos de receta
- Pasos e instrucciones
- Fotos y multimedia
- Recetas guardadas
- Sedes

## Sistema de roles

| Rol | Acceso |
|---|---|
| **Visitante** | Recetas aprobadas, cursos, ingredientes, sedes |
| **Usuario** | Todo lo anterior + subir recetas, calificar, guardar |
| **Alumno** | Todo lo anterior + inscripción a cursos, asistencias, pagos |
| **Admin** | Acceso completo + aprobar recetas y gestionar usuarios |

## Endpoints principales

```
# Públicos
GET  /api/recetas/aprobadas
GET  /api/recetas/buscar?nombre=...
GET  /api/recetas/{id}
GET  /api/recetas/{id}/escalar-visual?porciones=4
GET  /api/recetas/ultimas
GET  /api/cursos
GET  /api/cursos/{id}
GET  /api/ingredientes
GET  /api/sedes

# Autenticación
POST /api/usuarios/login
POST /api/usuarios/registro/usuario
POST /api/usuarios/registro/alumno
POST /api/usuarios/recuperar

# Requieren autenticación
POST /api/recetas
GET  /api/recetas/usuario/{idUsuario}
GET  /api/recetas/tipo/{idTipo}
GET  /api/recetas/ingrediente?nombreIngrediente=...
GET  /api/recetas/sin-ingrediente?nombreIngrediente=...

# Solo ALUMNO o ADMIN
GET  /api/alumnos/{id}
GET  /api/alumnos/{id}/cuenta-corriente
GET  /api/alumnos/{id}/movimientos-cuenta
POST /api/cursos/inscribir
POST /api/cursos/registrar-asistencia

# Solo ADMIN
PUT  /api/recetas/aprobar/{id}
POST /api/usuarios/registro/admin-dev
```

## Instalación y ejecución

### Requisitos
- Java 17+
- MySQL 8+
- Maven 3.6+

### Configuración

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cocinaapp
spring.datasource.username=root
spring.datasource.password=root
```

### Ejecutar

```bash
git clone https://github.com/joaquinoto/Back-DAI.git
cd Back-DAI
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Estructura del proyecto

```
src/main/java/com/cocinaapp/backend/
├── controller/       # Endpoints REST
├── model/            # Entidades JPA
├── repository/       # Acceso a base de datos
├── service/          # Lógica de negocio
├── security/         # JWT filter y configuración
└── config/           # Spring Security config
```

## Autores

Proyecto académico desarrollado para la materia **Desarrollo de Aplicaciones I — UADE**.
