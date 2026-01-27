

# run-wih-postgres.ps1
Write-Host "=== Executando com PostgreSQL ===" -ForegroundColor Cyan

# 1. Parar tudo
Write-Host "`n1. Parando containers existentes..." -ForegroundColor Yellow
docker-compose down -v

# 2. Build
Write-Host "`n2. Build da aplicação..." -ForegroundColor Yellow
docker-compose build

# 3. Executar
Write-Host "`n3. Iniciando PostgreSQL e API..." -ForegroundColor Yellow
docker-compose up -d

# 4. Aguardar
Write-Host "`n4. Aguardando 30 segundos para inicialização..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# 5. Verificar
Write-Host "`n5. Verificando serviços..." -ForegroundColor Yellow
docker-compose ps

# 6. Testar
Write-Host "`n6. Testando API..." -ForegroundColor Yellow
try {
    $health = Invoke-RestMethod -Uri "http://localhost:8080/actuator/health" -TimeoutSec 10
    Write-Host "✓ API saudável: $($health.status)" -ForegroundColor Green
    
    Write-Host "`n🌐 URLs:" -ForegroundColor Cyan
    Write-Host "   API: http://localhost:8080" -ForegroundColor White -BackgroundColor DarkBlue
    Write-Host "   Swagger: http://localhost:8080/swagger-ui.html" -ForegroundColor White -BackgroundColor DarkBlue
    Write-Host "   PostgreSQL: localhost:5432" -ForegroundColor White -BackgroundColor DarkBlue
} catch {
    Write-Host "✗ Erro ao testar API" -ForegroundColor Red
    Write-Host "Logs:" -ForegroundColor Yellow
    docker-compose logs app
}

Write-Host "`nComandos úteis:" -ForegroundColor Cyan
Write-Host "  Ver logs: docker-compose logs -f" -ForegroundColor White
Write-Host "  Parar: docker-compose down" -ForegroundColor White
Write-Host "  Acessar PostgreSQL: docker exec -it gestora-postgres psql -U postgres -d gestora_db" -ForegroundColor White