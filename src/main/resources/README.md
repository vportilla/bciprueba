
# Desafio BCI

API Rest de resgistro de usuarios para el BCI


## Tech Stack

** Version Java:** 17
** Base de Datos:** H2
** Framework Utlizado: ** SpringBoot
** Formato para ID: ** UUID

 


## Installation

Clonar el proyecto desde el github vportilla/bciprueba
Al utilizar el motor de base de datos H2, no es necesario un script de instalacion ya que esta se realiza atraves del application.properties


    
## Demo

Una vez levantado el proyecto, ir al sitio jwt.io y en la seccion Payload remplazar los parametros por "alias:"  y agregar un nombre.
en la seccion Verify Signature remplazar your-256-bit-secret por estaesunaclavedeejemploparalapostulaciondeunproyectoparaunclienteenparticular y luego copiar el JWT codificado

Para realizar el registro de nuevo usuario 
ingresar en el postman 

http://localhost:8080/api/usuarios/registro 
  Agregar en los header del postman los campos Authorization y user, agregando el codificado y alias respetivamente
  Agregar los campos definidos en la prueba para registrar datos y presionar post
  Probar ingresar contraseña menor a 8 digitos y mayor a 12.
  Probar Ingresar contraseña con solo numeros o solo letras ya sea mayusculas o minusculas, sin signgos especiales.
  Probar ingresar un Mail sin formato.

Para realizar la actualizacion ingresar lo siguiente en el postman
http://localhost:8080/api/usuarios/actualizar/ junto a la id del usuario y tambien los Headers como en el registro, usando PUT

Para mostrar todos los usuarios registrados simplemente ingresar en el postman
http://localhost:8080/api/usuarios/ usando GET


Para mostrar un usuario por ID ingresar en el postman
http://localhost:8080/api/usuarios/ y el ID del usario usando GET

Para eliminar ingresar http://localhost:8080/api/usuarios/ con la ID y usar el DELETE 

