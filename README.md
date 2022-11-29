# Hero Challenge

Idea principal: Leer, modificar y borrar heroes.
El proyecto cuenta con:
- Configuración de caché
- Seguridad con Spring Security
- H2 como base de datos embebida en memoria
- Documentación (Javadoc en ```IModelMapperService``` como ejemplo, y Open Api 3)
- Test unitarios y de integración
- Manejo de excepciones controladas
- Aplicación de Facade como un ejemplo de patrón de diseño
- Principios SOLID
- Buenas prácticas en REST y en Java
- Uso de programación funcional
- Uso de ```Optional```
- El proyecto esta dockerizado sin usar una imagen de un SO completo
- Metología GitFlow

## Requisitos

Java JDK 11.

## Entorno

1. Correr ```mvn install``` para generar el empaquetado.
2. Para crear la imagen docker ```docker build -t "hero-app"```.
3. Si quieres ver las imágenes ```docker images```.
4. Para levantar la imagen ```docker run --name java-app -p 8081:8080 hero-app:latest```.
5. Para verificar que esté corrientdo ```docker ps -a```,

## Documentación

El proyecto cuenta con algunos métodos descriptos por JavaDoc, pero también posee Swagger. Para consultarlo, vaya a ```http://localhost:2001/swagger-ui/index.html```.