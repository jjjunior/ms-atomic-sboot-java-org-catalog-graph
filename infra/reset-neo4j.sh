#!/bin/bash

set -e

PROJECT_NAME=$(basename "$PWD")

# Lista de volumes definidos no docker-compose
VOLUMES=("neo4j-data" "neo4j-logs" "neo4j-import")

echo "🔻 Parando containers..."
docker compose down

for VOLUME in "${VOLUMES[@]}"; do
  FULL_VOLUME_NAME="${PROJECT_NAME}_${VOLUME}"
  echo "🧹 Removendo volume: $FULL_VOLUME_NAME"
  docker volume rm "$FULL_VOLUME_NAME" || {
    echo "⚠️  Volume $FULL_VOLUME_NAME não encontrado ou já removido. Continuando..."
  }
done

echo "⬆️ Subindo containers novamente com volumes limpos..."
docker compose up -d

echo "✅ Neo4j reiniciado e zerado com sucesso!"