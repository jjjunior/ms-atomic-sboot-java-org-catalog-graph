#!/usr/bin/env bash
set -euo pipefail

# ==========================
# Uso:
#   ./reset-aura-neo4j.sh                 # apaga TODOS os nós/relacionamentos (soft reset)
#   ./reset-aura-neo4j.sh --hard          # apaga dados + dropa TODOS os constraints e índices (hard reset)
#
# Requisitos:
#   - cypher-shell instalado localmente (vem junto do Neo4j Desktop/CLI) OU
#   - usar o bloco "via Docker" mais abaixo
#
# Variáveis (defina por ambiente, .env ou inline):
#   AURA_URI=neo4j+s://<id>.databases.neo4j.io
#   AURA_USER=neo4j
#   AURA_PASSWORD=********
#   AURA_DATABASE=neo4j            # opcional (default: neo4j)
# ==========================

AURA_URI="${AURA_URI:-}"
AURA_USER="${AURA_USER:-neo4j}"
AURA_PASSWORD="${AURA_PASSWORD:-}"
AURA_DATABASE="${AURA_DATABASE:-neo4j}"
HARD_RESET="${1:-}"

if [[ -z "$AURA_URI" || -z "$AURA_PASSWORD" ]]; then
  echo "❌ Defina AURA_URI e AURA_PASSWORD (e opcionalmente AURA_DATABASE). Ex:"
  echo "   export AURA_URI='neo4j+s://<id>.databases.neo4j.io'"
  echo "   export AURA_PASSWORD='...'"
  exit 1
fi

echo "⚠️  Você está prestes a APAGAR TODOS os dados do banco '$AURA_DATABASE' em: $AURA_URI"
read -r -p "Digite 'APAGAR' para confirmar: " CONFIRM
[[ "$CONFIRM" == "APAGAR" ]] || { echo "Operação cancelada."; exit 1; }

# Função utilitária para executar Cypher
run_cypher () {
  local query="$1"
  cypher-shell -a "$AURA_URI" -u "$AURA_USER" -p "$AURA_PASSWORD" -d "$AURA_DATABASE" "$query"
}

echo "🔻 Removendo TODOS os nós e relacionamentos..."
run_cypher "MATCH (n) DETACH DELETE n;"

if [[ "$HARD_RESET" == "--hard" ]]; then
  echo "🧨 HARD RESET habilitado — dropando constraints e índices..."

  # Coleta nomes de constraints e droppa um a um
  echo "  • Coletando constraints..."
  mapfile -t CONSTRAINTS < <(cypher-shell -a "$AURA_URI" -u "$AURA_USER" -p "$AURA_PASSWORD" -d "$AURA_DATABASE" \
    "SHOW CONSTRAINTS YIELD name RETURN name" --format plain | sed '/^name$/d' | sed '/^$/d')

  if (( ${#CONSTRAINTS[@]} )); then
    for c in "${CONSTRAINTS[@]}"; do
      echo "  • DROP CONSTRAINT $c"
      run_cypher "DROP CONSTRAINT $c IF EXISTS;"
    done
  else
    echo "  • Nenhum constraint encontrado."
  fi

  # Coleta nomes de índices e droppa um a um
  echo "  • Coletando índices..."
  mapfile -t INDEXES < <(cypher-shell -a "$AURA_URI" -u "$AURA_USER" -p "$AURA_PASSWORD" -d "$AURA_DATABASE" \
    "SHOW INDEXES YIELD name RETURN name" --format plain | sed '/^name$/d' | sed '/^$/d')

  if (( ${#INDEXES[@]} )); then
    for i in "${INDEXES[@]}"; do
      echo "  • DROP INDEX $i"
      run_cypher "DROP INDEX $i IF EXISTS;"
    done
  else
    echo "  • Nenhum índice encontrado."
  fi
fi

echo "✅ Reset concluído com sucesso em $AURA_URI (db=$AURA_DATABASE)."