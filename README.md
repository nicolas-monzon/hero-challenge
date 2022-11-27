# hero_challenge

mvn install
docker build -t "hero-app" .
docker images
docker run --name java-app -p 8081:8080 hero-app:latest
docker ps -a // para ver lo que tengo corriendo