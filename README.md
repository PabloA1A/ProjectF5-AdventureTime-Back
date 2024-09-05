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


## UML Diagram

![Captura](https://github.com/user-attachments/assets/c9ef2023-47bd-408e-a3f4-bcce8e22e2a5)


## Tools
- Spring Boot

- Postman

- Docker

- MySql Workbench
