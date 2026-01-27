# Dockerfile corrigido
# Build stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Configurar encoding globalmente
ENV LANG C.UTF-8
ENV LC_ALL C.UTF-8
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8"

# Copiar pom.xml primeiro
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Build com encoding explícito e skip filtering
RUN mvn clean package -DskipTests -Dfile.encoding=UTF-8 -Dmaven.resources.skip=true

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Configurações
ENV LANG C.UTF-8
ENV TZ=America/Sao_Paulo

# Copiar JAR
COPY --from=builder /app/target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Otimizações
ENV JAVA_OPTS="-Xmx512m -Xms256m -Dfile.encoding=UTF-8 -Dserver.port=\${PORT:-8080}"

# Comando
ENTRYPOINT ["sh", "-c", "java \${JAVA_OPTS} -jar /app/app.jar"]