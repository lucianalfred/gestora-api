# Dockerfile SIMPLIFICADO E FUNCIONAL
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# 1. Copiar APENAS pom.xml primeiro
COPY pom.xml .

# 2. Baixar dependências (sem filtering)
RUN mvn dependency:go-offline -B -Dmaven.resources.skip=true

# 3. Copiar código fonte
COPY src ./src

# 4. Build SEM filtering
RUN mvn clean package -DskipTests -Dmaven.resources.skip=true -Dfile.encoding=UTF-8

# Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]