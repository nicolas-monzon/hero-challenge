FROM openjdk:11.0-oracle
COPY "./target/hero-1.0-SNAPSHOT.jar" "hero.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "hero.jar"]