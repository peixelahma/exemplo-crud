# Dockerfile para o projeto Exemplo CRUD (Spring Boot + H2 em memória)
# Baseado em uma imagem oficial do OpenJDK 21 (JRE) do Eclipse Temurin.

FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copia o artifact do Maven (empacotado em jar) para o contêiner.
# Se preferir construir com mvn package, use: mvn -q -DskipTests package
COPY target/exemplo-crud-1.0.0.jar app.jar

# Porta em que o Spring Boot escuta (conforme application.properties -> server.port=8080)
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]