# Hero Challenge

## Requisitos

Java JDK 11.

## Entorno

1. Correr ```mvn install``` para generar el empaquetado.
2. Para crear la imagen docker ```docker build -t "hero-app"```.
3. Si quieres ver las imágenes ```docker images```.
4. Para levantar la imagen ```docker run --name java-app -p 8081:8080 hero-app:latest```.
5. Para verificar que esté corrientdo ```docker ps -a```,