#!/bin/bash
# Build da imagem Docker
docker build -t gestora-api:latest .

# Tag para Docker Hub (se quiser publicar)
# docker tag gestora-api:latest seuusuario/gestora-api:latest