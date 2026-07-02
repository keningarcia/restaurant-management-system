## Proyecto

Restaurant Management System desarrollado con Java 21 y Spring Boot.

## Objetivo

Construir una API REST escalable siguiendo buenas prácticas.

## Arquitectura

Package by Feature.

Cada módulo debe contener:

- controller
- service
- repository
- entity
- dto
- mapper
- enums
- specification

## Tecnologías

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- JWT
- Flyway
- Docker
- Swagger
- Lombok
- MapStruct

## Convenciones

- Controladores delgados.
- Toda la lógica va en Service.
- Nunca acceder al Repository desde Controller.
- DTO para entrada y salida.
- No devolver entidades.
- Validar con Bean Validation.
- Manejo global de excepciones.
- Soft Delete cuando aplique.
- Auditoría automática.

## Estilo

- SOLID
- Clean Code
- Clean Architecture
- Principios REST
- Código documentado

## Desarrollo

Construir un módulo completamente antes de pasar al siguiente.