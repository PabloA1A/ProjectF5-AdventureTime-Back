# Adventure Time
Un colectivo de desarrollo de software quiere crear una aplicación web para gestionar sus eventos on-line como talleres, masterclass o webinars. Los usuarios podrán ver la descripción de un evento, apuntarse y desapuntarse. También podrán ver la lista de los eventos a los que se han apuntado. El administrador debe tener las herramientas para la gestión (CRUD) de los eventos.

## Server
- localhost:8080

- mvn spring-boot:run to run app

## Endpoints

- api-endpoint = /api/v1

Animals
- <p>GET localhost:8080/api/v1/event/all</p>
- <p>GET localhost:8080/api/v1/event/{id}</p>
- <p>GET localhost:8080/api/v1/event/families</p>
- <p>POST localhost:8080/api/v1/event/add</p>
- <p>PUT localhost:8080/api/v1/event/update/{id}</p>
- <p>DELETE localhost:8080/api/api/v1/event/delete/{id}</p>

Login
- <p>GET localhost:8080/api/v1/login</p>


## Database Normalization Diagram


## UML Diagram

![Imagen de WhatsApp 2024-09-05 a las 14 53 23_86e7f818](https://github.com/user-attachments/assets/292d81b9-6e16-4b4c-8fe7-1bfc89b435b3)


## Tools
- Spring Boot

- Postman

- Docker

- MySql Workbench
