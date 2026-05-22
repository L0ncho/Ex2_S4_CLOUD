# Enrollment Platform

Sistema de inscripción educativa virtual desarrollado como monolito modular hexagonal con Spring Boot.

## Caso de negocio

Una plataforma educativa necesita gestionar la inscripción de estudiantes a cursos virtuales. El sistema cubre tres requisitos funcionales:

1. **Consultar cursos disponibles** — `GET /courses` lista todos los cursos con nombre, instructor, duración y costo.
2. **Agregar cursos** — `POST /courses` incorpora nuevos cursos con persistencia en base de datos.
3. **Inscribir estudiantes** — `POST /enrollments` inscribe a un estudiante en uno o más cursos y devuelve un resumen con el costo de cada curso y el total a pagar.

## Requisitos mínimos


| Herramienta | Versión mínima                              |
| ----------- | ------------------------------------------- |
| Java        | 21                                          |
| Maven       | 3.9+                                        |
| Oracle DB   | Solo perfil `prod` (opcional en desarrollo) |


## Estructura del proyecto

```
src/main/java/com/duoc/enrollmentplatform/
├── shared/domain/          # DomainError, Id, Money, Email
├── courses/
│   ├── domain/             # Course entity, CourseRepository + InMemory
│   ├── application/        # ListCoursesUseCase, CreateCourseUseCase, DTOs
│   └── infrastructure/     # JpaCourseRepository, CourseController
├── enrollment/
│   ├── domain/             # Student, Enrollment, EnrollmentLine, repos + InMemory
│   ├── application/        # CreateEnrollmentUseCase, EnrollmentSummaryDTO
│   └── infrastructure/     # JPA adapters, EnrollmentController
└── factory/                # EnrollmentPlatformFactory, ApplicationConfiguration

src/main/resources/
├── application.properties          # Perfil activo por defecto: local
├── application-local.properties    # H2 in-memory + Flyway
├── application-prod.properties     # Oracle Autonomous (variables de entorno)
└── db/migration/
    ├── V1__schema.sql              # Creación de tablas
    └── V2__seed_data.sql           # Datos iniciales en español
```

## Ejecución local (H2)

El perfil `local` usa H2 in-memory. No requiere configuración de base de datos.

```bash
# Clonar y compilar
./mvnw clean package -DskipTests

# Ejecutar (perfil local por defecto)
./mvnw spring-boot:run
```

La aplicación arranca en `http://localhost:8080`. Endpoints disponibles:


| Método | URL                | Descripción                        |
| ------ | ------------------ | ---------------------------------- |
| GET    | `/courses`         | Lista los cursos disponibles       |
| POST   | `/courses`         | Agrega un nuevo curso              |
| POST   | `/enrollments`     | Inscribe a un estudiante en cursos |
| GET    | `/actuator/health` | Estado de la aplicación            |
| GET    | `/h2-console`      | Consola H2 (solo perfil local)     |


## Ejecución con Oracle Cloud (prod)

Copiar `.env.example` a `.env` y completar las variables de conexión:

```bash
cp .env.example .env
# Editar .env con las credenciales de Oracle Autonomous
```

Spring Boot no carga `.env` automáticamente. Exportar las variables antes de ejecutar:

```bash
export SPRING_PROFILES_ACTIVE=prod
export SPRING_DATASOURCE_URL=jdbc:oracle:thin:@...
export SPRING_DATASOURCE_USERNAME=...
export SPRING_DATASOURCE_PASSWORD=...

./mvnw spring-boot:run
```

Flyway ejecuta las mismas migraciones (`V1__schema.sql` y `V2__seed_data.sql`) al conectarse a Oracle por primera vez.

## Ejecutar tests

Todos los tests (unitarios, integración y E2E) usan H2 con el perfil `local`:

```bash
./mvnw test
```

La pirámide de tests incluye:

- **Unitarios** — Dominio y use cases con InMemory repos. Sin Spring.
- **Integración** — Adaptadores JPA contra H2 con esquema Flyway.
- **E2E** — Endpoints HTTP completos con Spring Boot y datos semilla.

## Datos de prueba (seed)

El archivo `V2__seed_data.sql` carga al arrancar en perfil `local`

Ejemplo de inscripción con curl:

```bash
curl -X POST http://localhost:8080/enrollments \
  -H "Content-Type: application/json" \
  -d '{"studentId": "s-001", "courseIds": ["c-001", "c-002"]}'
```

## Contratos HTTP

### GET /courses

Respuesta `200`:

```json
[
  { "id": "c-001", "name": "Introducción a Java", "instructor": "María González", "durationHours": 40, "price": 150000.0 }
]
```

### POST /courses

Body:

```json
{ "name": "Nuevo curso", "instructor": "Prof", "durationHours": 20, "price": 100000 }
```

Respuesta `201`:

```json
{ "id": "...", "name": "Nuevo curso", "instructor": "Prof", "durationHours": 20, "price": 100000.0 }
```

### POST /enrollments

Body:

```json
{ "studentId": "s-001", "courseIds": ["c-001", "c-002"] }
```

Respuesta `201`:

```json
{
  "enrollmentId": "...",
  "studentId": "s-001",
  "lines": [
    { "courseId": "c-001", "courseName": "Introducción a Java", "unitPrice": 150000.0 },
    { "courseId": "c-002", "courseName": "Bases de datos", "unitPrice": 120000.0 }
  ],
  "totalAmount": 270000.0
}
```

## Errores


| Código | Situación                                                             |
| ------ | --------------------------------------------------------------------- |
| 400    | Campo requerido faltante o de tipo incorrecto                         |
| 404    | Estudiante o curso no encontrado                                      |
| 422    | Regla de negocio violada (nombre vacío, lista vacía, precio negativo) |
| 500    | Error técnico inesperado                                              |


