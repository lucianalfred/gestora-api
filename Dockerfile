# Dockerfile
# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar pom.xml primeiro para cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Configurações
ENV LANG C.UTF-8
ENV TZ=America/Sao_Paulo

# Copiar JAR
COPY --from=builder /app/target/*.jar app.jar

# Expor porta (Render usa a variável PORT)
EXPOSE 8080

# Otimizações para Render
ENV JAVA_OPTS="-Xmx512m -Xms256m -Dfile.encoding=UTF-8 -Dserver.port=${PORT:-8080}"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:${PORT:-8080}/actuator/health || exit 1

# Comando
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]