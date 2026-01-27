@echo off
REM Build da imagem
docker build -t gestora-api:latest .

REM Executar
docker-compose up -d

echo.
echo Aplicacao rodando em: http://localhost:8080
echo Swagger UI: http://localhost:8080/swagger-ui.html