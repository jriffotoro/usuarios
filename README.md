# API de Gestión de Usuarios

Esta aplicación expone una API RESTful que permite gestionar usuarios. Los datos de entrada y salida se encuentran en formato JSON. La documentación de la API está basada en Swagger.

## Requisitos previos
- JDK 11 o superior
- Maven 3.6.3 o superior

## Instalación y configuración

1. Clona el repositorio de la aplicación:

```
git clone https://github.com/jriffotoro/usuarios.git
```

2. Accede al directorio del proyecto:

```
cd tu_repositorio
```

3. Compila el proyecto utilizando Maven:

```
mvn clean package
```

4. Ejecuta la aplicación:

```
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`.

## Documentación de la API

La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html`, donde encontrarás una interfaz interactiva que describe los diferentes endpoints, parámetros de entrada y salidas esperadas.

## Endpoints

### Iniciar sesión

```
POST /api/login
```

Inicia sesión y obtiene un token de acceso.

```bash
curl --location 'http://localhost:8080/api/login' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--header 'Cookie: JSESSIONID=47ED9D2220A55A18EC619D776853C939' \
--data '{
  "name": "admin",
  "password": "Bgfdgfdg23dsfds"
}'
```

Respuesta exitosa:

Código de estado: 200 (OK)
```json
{
    "estado": 0,
    "mensaje": "OK",
    "adicional": {
        "name": "admin",
        "password": "Bgfdgfdg23dsfds",
        "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQxNTA2LCJleHAiOjE2ODU4NDIxMDZ9.m-_nNpirlc5ggmPWTbZ69v9oIREqA5lhCZdOk_tSkH9UOKAARizQO4SyFM0rYJSI5A2BiBfAAOxcs8biFEw3Ng"
    },
    "tipoRespuesta": "success"
}
```

### Crear un usuario

```
POST /api/user
```

Crea un nuevo usuario con los siguientes datos:

```bash
curl --location 'http://localhost:8080/api/user' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQxNTA2LCJleHAiOjE2ODU4NDIxMDZ9.m-_nNpirlc5ggmPWTbZ69v9oIREqA5lhCZdOk_tSkH9UOKAARizQO4SyFM0rYJSI5A2BiBfAAOxcs8biFEw3Ng' \
--header 'Cookie: JSESSIONID=B71350C5827146A9009B73CDFD50E852' \
--data-raw '{
  "email": "jriffo.toro@gmail.com",
  "name": "José Riffo Toro",
  "password": "Bgfdgfdg23dsfds",
  "phones": [
    {
      "citycode": "2",
      "contrycode": "56",
      "number": "5556127"
      
    },
    {
      "citycode": "2",
      "contrycode": "56",
      "number": "55561279"
    }
  ]
}'
```

Respuesta exitosa:

Código de estado: 201 (Created)
```json
{
    "estado": 0,
    "mensaje": "Usuario creado",
    "adicional": {
        "id": "af2991e5-3e83-4579-8154-41b99efc8c5f",
        "name": "José Riffo Toro",
        "email": "jriffo.toro@gmail.com",
        "password": "Bgfdgfdg23dsfds",
        "created": "2023-06-04T01:18:38.930+00:00",
        "modified": null,
        "last_login": "2023-06-04T01:18:38.930+00:00",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQxNTA2LCJleHAiOjE2ODU4NDIxMDZ9.m-_nNpirlc5ggmPWTbZ69v9oIREqA5lhCZdOk_tSkH9UOKAARizQO4SyFM0rYJSI5A2BiBfAAOxcs8biFEw3Ng",
        "isactive": true,
        "phones": null
    },
    "tipoRespuesta": "success"
}
```

### Obtener usuarios

```
GET /api/user
```

Obtiene todos los usuarios

Respuesta exitosa:

Código de estado: 200 (OK)
```json
{
    "estado": 0,
    "mensaje": "OK",
    "adicional": [
        {
            "id": "f9df4683-91f9-4a02-a2da-a231265da2a5",
            "name": "José Riffo Toro",
            "email": "jriffo.toro@gmail.com",
            "password": "Bgfdgfdg23dsfds",
            "created": "2023-06-04T02:24:08.229+00:00",
            "modified": null,
            "last_login": "2023-06-04T02:24:08.229+00:00",
            "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQ1NDMxLCJleHAiOjE2ODU4NDYwMzF9.lsjLYfXIcJq-8QEAjAGFBTXv5hlsxoWwXxXJVsEuC4dPFAF78KjMtpb6uT-wS4CVDZAP0YOGWes3zYZyy_j05Q",
            "isactive": true,
            "phones": [
                {
                    "id_phone": 1,
                    "number": "5556127",
                    "citycode": "2",
                    "contrycode": "56",
                    "user": null
                },
                {
                    "id_phone": 2,
                    "number": "55561279",
                    "citycode": "2",
                    "contrycode": "56",
                    "user": null
                }
            ]
        }
    ],
    "tipoRespuesta": "success"
}
```

### Actualizar un usuario

```
PUT /api/user/{id}
```

Actualiza los datos de un usuario existente con el ID especificado.

```bash
curl --location --request PUT 'http://localhost:8080/api/user/8dd9e9eb-b628-4035-82fa-9d7eaa990403' \
--header 'Content-Type: application/json' \
--header 'Accept: */*' \
--header 'Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQ1ODgzLCJleHAiOjE2ODU4NDY0ODN9.EpJvFCcA4Otg3T6t0SQr7HOPnd4K8AsIldFBsn7JABeST-mrUDKhrJt2F3Jwg9gdFl0RsWJ7kpod8P6Y-1JIlQ' \
--header 'Cookie: JSESSIONID=38ADE65974E0C26D3B153FBE0EDD0636' \
--data-raw '{
  "email": "jriffo.toro@gmail.com",
  "name": "José Riffo Toro",
  "password": "Bgfdgfdg23dsfds",
  "phones": [
    {
      "citycode": "2",
      "contrycode": "56",
      "number": "5556127"
      
    }
  ]
}'
```

Respuesta exitosa:

Código de estado: 202 (Accepted)
```json
{
    "estado": 0,
    "mensaje": "Usuario actualizado",
    "adicional": {
        "id": "8dd9e9eb-b628-4035-82fa-9d7eaa990403",
        "name": "José Riffo Toro",
        "email": "jriffo.toro@gmail.com",
        "password": "Bgfdgfdg23dsfds",
        "created": null,
        "modified": null,
        "last_login": null,
        "token": null,
        "isactive": null,
        "phones": [
            {
                "id_phone": null,
                "number": "5556127",
                "citycode": "2",
                "contrycode": "56",
                "user": null
            }
        ]
    },
    "tipoRespuesta": "success"
}
```

### Eliminar un usuario

```
DELETE /api/user/{id}
```

Elimina un usuario por su ID.

Respuesta exitosa:

Código de estado: 202 (Accepted)
```json
{
    "estado": 0,
    "mensaje": "Usuario eliminado",
    "adicional": {
        "id": "c969928c-c9f2-4a99-890d-2ab6ad91c521",
        "name": "José Riffo Toro",
        "email": "jriffo.toro@gmail.com",
        "password": "Bgfdgfdg23dsfds",
        "created": "2023-06-04T02:30:12.064+00:00",
        "modified": null,
        "last_login": "2023-06-04T02:30:12.064+00:00",
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoiYWRtaW4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjg1ODQ1ODAxLCJleHAiOjE2ODU4NDY0MDF9.l6Fk3wkDCA29vjmZ8s0oDSs_MQ-Spo39OoO3YDBUVhkngdVgsPM2_tgtgeu8A6OIg_vd6muKoBdPpOueBLIqVg",
        "isactive": true,
        "phones": null
    },
    "tipoRespuesta": "success"
}
```