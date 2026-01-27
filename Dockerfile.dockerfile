# Estágio 1: Build
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Copiar apenas os arquivos necessários para build
COPY pom.xml .
COPY src ./src

# Baixar dependências (cache)
RUN mvn dependency:go-offline -B

# Build da aplicação
RUN mvn clean package -DskipTests

# Estágio 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Criar usuário não-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copiar o JAR do estágio de build
COPY --from=builder --chown=spring:spring /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Configurações de memória e health check
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="production"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Comando de execução
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app/app.jar"]