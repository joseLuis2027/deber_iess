# ejercicio-backend

Este proyecto utiliza **Quarkus**, el *Framework Java*.

Si deseas aprender más sobre Quarkus, visita su sitio web: <https://quarkus.io/>.

---

## Ejecutar la aplicación en modo desarrollo

Puedes ejecutar la aplicación en modo desarrollo (dev mode), lo que habilita el **live coding**, usando el siguiente comando:

```bash
./mvnw compile quarkus:dev
```

> **Nota:** Quarkus incluye una **interfaz Dev UI**, disponible únicamente en modo desarrollo, en:  
> <http://localhost:8080/q/dev/>

---

## Empaquetar y ejecutar la aplicación

La aplicación puede empaquetarse con el siguiente comando:

```bash
./mvnw package
```

Esto genera el archivo `quarkus-run.jar` en el directorio `target/quarkus-app/`.  
Ten en cuenta que **no es un über-jar**, ya que las dependencias se copian en `target/quarkus-app/lib/`.

La aplicación puede ejecutarse con:

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

Si deseas construir un **über-jar**, ejecuta:

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

La aplicación empaquetada como über-jar se ejecuta con:

```bash
java -jar target/*-runner.jar
```

---

## Crear un ejecutable nativo

Puedes crear un ejecutable nativo con:

```bash
./mvnw package -Dnative
```

O, si no tienes **GraalVM** instalado, puedes construirlo dentro de un contenedor usando:

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Luego, puedes ejecutar el binario nativo con:

```bash
./target/ejercicio-backend-1.0.0-SNAPSHOT-runner
```

Para más información sobre cómo construir ejecutables nativos, revisa:  
<https://quarkus.io/guides/maven-tooling>

---

## Guías relacionadas

- **Hibernate ORM con Panache** ([guía](https://quarkus.io/guides/hibernate-orm-panache)):  
  Simplifica tu código de persistencia para Hibernate ORM mediante los patrones *Active Record* o *Repository*.
  
- **Controlador JDBC - MySQL** ([guía](https://quarkus.io/guides/datasource)):  
  Conéctate a bases de datos MySQL mediante JDBC.

- **Hibernate Validator** ([guía](https://quarkus.io/guides/validation)):  
  Valida propiedades de objetos (campos, getters) y parámetros de métodos en tus beans (REST, CDI, Jakarta Persistence).

- **SmallRye OpenAPI** ([guía](https://quarkus.io/guides/openapi-swaggerui)):  
  Documenta tus APIs REST con OpenAPI y Swagger UI.

- **REST (Jakarta REST)** ([guía](https://quarkus.io/guides/rest)):  
  Implementación de servicios REST reactivos basada en Vert.x.  
  *(No es compatible con `quarkus-resteasy` ni con extensiones que dependan de él).*

- **Swagger UI** ([guía](https://quarkus.io/guides/openapi-swaggerui)):  
  Interfaz gráfica para explorar y probar tus endpoints REST.

---


